package test.com.testapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by zhangjian on 2019/6/13 14:44
 * 分析三种状态：
 * 1.小球旋转状态
 * 2.小球扩散聚拢状态
 * 3.打球扩散状态
 */
public class SplashView extends View {

    private abstract class DrawState {
        public abstract void drawState(Canvas canvas);
    }


    private Paint mBallPaint;
    private Paint mBgPaint;
    private int mCenterW, mCenterH;//布局中心点位置
    private float mDistance;//布局中心点距离0.0的距离
    private int ballRadious = 18;//旋转的小圆半径
    private int rotateRadious = 90;//旋转的半径
    private float mCurrentRotateRadius = rotateRadious;//当前的旋转半径
    //当前大圆的旋转角度
    private float mCurrentRotateAngle = 0F;
    private int mRotateDuration = 1200;
    private ValueAnimator mAnimator;
    private int[] mCircleColors;
    private DrawState mState;
    private float mCurrentHoleRadius;//扩散动画圆半径
    private Paint mHolePaint;//扩散圆画笔


    public SplashView(Context context) {
        this(context, null);
    }

    public SplashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint.setStyle(Paint.Style.STROKE);
        mHolePaint.setColor(Color.WHITE);
        mCircleColors = getResources().getIntArray(R.array.splash_circle_colors);
        for (int i = 0; i < 361; i++) {
            Log.e("jianzhang--->", "cos = " + Math.cos(i) + ",sin = " + Math.sin(i));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == null) {
            mState = new RotateState();
        }
        mState.drawState(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterW = w / 2;
        mCenterH = h / 2;
        mDistance = (float) (Math.hypot(w, h) / 2);
    }

    private void drawBall(Canvas canvas) {
        float rotateAngle = (float) (Math.PI * 2 / mCircleColors.length);
        for (int i = 0; i < mCircleColors.length; i++) {
            // x = r * cos(a) + centX;
            // y = r * sin(a) + centY;
            float angle = i * rotateAngle + mCurrentRotateAngle;
            float cx = (float) (Math.cos(angle) * mCurrentRotateRadius + mCenterW);
            float cy = (float) (Math.sin(angle) * mCurrentRotateRadius + mCenterH);
            Log.e("jianzhang", "cos = " + Math.cos(angle) + ",sin = " + Math.sin(angle));
//            Log.e("jianzhang", "cos len = " + Math.cos(angle) * mCurrentRotateRadius + ",sin len= " + (Math.sin(angle) * mCurrentRotateRadius + mCenterH));
            mBallPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx, cy, ballRadious, mBallPaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        if (mCurrentHoleRadius > 0) {
            //绘制空心圆
            float strokeWidth = mDistance - mCurrentHoleRadius;
            mHolePaint.setStrokeWidth(strokeWidth);
            float radius = strokeWidth / 2 + mCurrentHoleRadius;
            canvas.drawCircle(mCenterW, mCenterH, radius, mHolePaint);
        } else {
            canvas.drawColor(Color.WHITE);
        }
    }

    //旋转
    private class RotateState extends DrawState {

        public RotateState() {
            mAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
            mAnimator.setRepeatCount(1);
            mAnimator.setDuration(mRotateDuration);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(animation -> {
                mCurrentRotateAngle = (float) animation.getAnimatedValue();
                invalidate();
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new MerginState();
                }
            });
            mAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawBall(canvas);
        }
    }

    //扩散聚合
    private class MerginState extends DrawState {
        public MerginState() {
            mAnimator = ValueAnimator.ofFloat(ballRadious, rotateRadious);
//            mAnimator.setRepeatCount(1);
            mAnimator.setDuration(mRotateDuration);
            mAnimator.setInterpolator(new OvershootInterpolator(10f));
            mAnimator.addUpdateListener(animation -> {
                Log.e("animation", "(float) animation.getAnimatedValue()" + (float) animation.getAnimatedValue());
                mCurrentRotateRadius = (float) animation.getAnimatedValue();
                invalidate();
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            mAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawBall(canvas);
        }
    }

    //圆形扩散
    private class ExpandState extends DrawState {

        public ExpandState() {
            mAnimator = ValueAnimator.ofFloat(0, mDistance);
//            mAnimator.setRepeatCount(1);
            mAnimator.setDuration(mRotateDuration);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(animation -> {
                Log.e("animation", "(float) animation.getAnimatedValue()" + (float) animation.getAnimatedValue());
                mCurrentHoleRadius = (float) animation.getAnimatedValue();
                invalidate();
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            mAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }

}
