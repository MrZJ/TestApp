package test.com.testapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjian on 2019/3/13 13:55
 */
public class SplitView extends View {
    private Bitmap mBitmap;
    private List<Ball> balls;
    private ValueAnimator animator;
    private static final int DEFAULT_RADIOUS = 1;
    private Paint mPaint;

    public SplitView(Context context) {
        this(context, null);
    }

    public SplitView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //step1:将图片分解成小球
    //step2：记录每个小球的位置，颜色信息
    //step3:
    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        balls = new ArrayList<>();
        for (int i = 0; i < mBitmap.getWidth(); i++) {
            for (int j = 0; j < mBitmap.getHeight(); j++) {
                Ball ball = new Ball();
                ball.color = mBitmap.getPixel(i, j);
                ball.x = i * DEFAULT_RADIOUS + (float) DEFAULT_RADIOUS / 2;
                ball.y = j * DEFAULT_RADIOUS + (float) DEFAULT_RADIOUS / 2;
                ball.r = (float) DEFAULT_RADIOUS / 2;
                //速度(-20,20)
                ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                ball.vY = rangInt(-15, 35);
                //加速度
                ball.aX = 1;
                ball.aY = 2;

                balls.add(ball);
            }
        }
        animator = ValueAnimator.ofInt(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            updateBall();
            invalidate();
        });
    }

    private void updateBall() {
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            ball.x += ball.vX;
            ball.y += ball.vY;
            ball.vX += ball.aX;
            ball.vY += ball.aY;
        }
    }

    private float rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(200, 200);
        for (Ball ball : balls) {
            mPaint.setColor(ball.color);
            canvas.drawCircle(ball.x, ball.y, ball.r, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animator.start();
                break;
        }
        return super.onTouchEvent(event);
    }
}
