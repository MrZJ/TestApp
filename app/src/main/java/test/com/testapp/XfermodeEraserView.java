package test.com.testapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

/**
 * Created by zhangjian on 2019/4/9 09:40
 */
public class XfermodeEraserView extends View {
    private Paint mPaint;
    private Bitmap mDstBmp, mSrcBmp, mTxtBmp;
    private Path mPath;

    public XfermodeEraserView(Context context) {
        this(context, null);
    }

    public XfermodeEraserView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeEraserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);
//初始化图片对象
        mTxtBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.result);
        mSrcBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.eraser);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mDstBmp = Bitmap.createBitmap(mSrcBmp.getWidth(), mSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制结果
        canvas.drawBitmap(mTxtBmp, 0, 0, mPaint);

        //开启离屏绘制
        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, ALL_SAVE_FLAG);
        //将path添加到目标bitmap上
        Canvas dstBitmap = new Canvas(mDstBmp);
        dstBitmap.drawPath(mPath, mPaint);
        //将源bitmap绘制
        canvas.drawBitmap(mDstBmp, 0, 0, mPaint);
        //设置图层混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        //绘制源bitmap
        canvas.drawBitmap(mSrcBmp, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(i);
    }

    private float mEventX, mEventY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("jianzhang", "onTouchEvent start");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEventX = event.getX();
                mEventY = event.getY();
                mPath.moveTo(mEventX, mEventY);
                Log.e("jianzhang", "onTouchEvent ACTION_DOWN" + mEventX + "," + mEventY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                mPath.quadTo(mEventX, mEventY, x, y);
                mEventX = x;
                mEventY = y;
                Log.e("jianzhang", "onTouchEvent ACTION_DOWN" + mEventX + "," + mEventY);
                break;
        }
        invalidate();
        return true;//不改为true ACTION_MOVE 不响应
    }
}
