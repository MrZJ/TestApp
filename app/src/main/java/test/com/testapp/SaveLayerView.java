package test.com.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

/**
 * Created by zhangjian on 2019/3/10 10:41
 */
public class SaveLayerView extends View {
    private Paint mPaint;

    public SaveLayerView(Context context) {
        this(context, null);
    }

    public SaveLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

//        mPaint.setShader(new LinearGradient(0, 0, 10, 0, Color.YELLOW, Color.GREEN, Shader.TileMode.REPEAT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(500,0);
//        canvas.rotate(45);
//        canvas.drawRect(200, 0, 600, 400, mPaint);
//        int i = canvas.saveLayer(200, 0, 600, 400, mPaint, ALL_SAVE_FLAG);
//        Matrix matrix = new Matrix();
////        matrix.setTranslate(100, 100);
//        canvas.setMatrix(matrix);
////        canvas.translate(200, 0);
//        mPaint.setColor(Color.YELLOW);
////        canvas.translate(100, 100);
//        canvas.drawRect(0, 0, 100, 100, mPaint);
//        mPaint.setColor(Color.GREEN);
//        canvas.translate(100, 100);
//        canvas.drawRect(0, 0, 100, 100, mPaint);
//        mPaint.setColor(Color.GRAY);
//        canvas.translate(100, 100);
//        canvas.drawRect(0, 0, 100, 100, mPaint);
//        mPaint.setColor(Color.RED);
//        canvas.translate(100, 100);
//        canvas.drawRect(0, 0, 100, 100, mPaint);
        /**
         * 1.canvas内部对于状态的保存存放在栈中
         * 2.可以多次调用save保存canvas的状态，并且可以通过getSaveCount方法获取保存的状态个数
         * 3.可以通过restore方法返回最近一次save前的状态，也可以通过restoreToCount返回指定save状态。指定save状态之后的状态全部被清除
         * 4.saveLayer可以创建新的图层，之后的绘制都会在这个图层之上绘制，直到调用restore方法
         * 注意：绘制的坐标系不能超过图层的范围， saveLayerAlpha对图层增加了透明度信息
         */

//        Log.e("", "onDraw: "+canvas.getSaveCount());
//        canvas.drawRect(200, 200, 700, 700, mPaint);
//        int state = canvas.save();
//        Log.e("", "onDraw: "+canvas.getSaveCount());
//        canvas.translate(50,50);
//
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0,500,500, mPaint);
//
//        canvas.save();
//        Log.e("", "onDraw: "+canvas.getSaveCount());
//        canvas.translate(50,50);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(0,0,500,500, mPaint);
//
//
////        canvas.restore();
//        Log.e("", "onDraw: "+canvas.getSaveCount());
////        canvas.restore();
//        canvas.restoreToCount(state);
//
////        canvas.restore();
//        Log.e("", "onDraw: "+canvas.getSaveCount());
//        canvas.drawLine(0,0, 400,500, mPaint);

//        canvas.drawRect(200, 200, 700, 700, mPaint);

//        int layerId = canvas.saveLayer(200, 200, 1000, 1000, mPaint, ALL_SAVE_FLAG);
//        mPaint.setColor(Color.GRAY);
//        Matrix matrix = new Matrix();
////        matrix.setTranslate(100, 100);
//        canvas.setMatrix(matrix);
//        canvas.drawRect(0, 0, 700, 700, mPaint); //由于平移操作，导致绘制的矩形超出了图层的大小，所以绘制不完全
//        mPaint.setColor(Color.GREEN);
//        canvas.drawRect(300, 300, 400, 400, mPaint);
//        canvas.restoreToCount(layerId);
//
//        mPaint.setColor(Color.RED);
//        canvas.drawRect(0, 0, 100, 100, mPaint);
//1，平移操作
//        canvas.drawRect(0,0, 400, 400, mPaint);
//        canvas.translate(50, 50);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0, 400, 400, mPaint);
//        canvas.drawLine(0, 0, 600,600, mPaint);

        //缩放操纵
//        canvas.drawRect(200,200, 700,700, mPaint);
////        canvas.scale(0.5f, 0.5f);
//        //先translate(px, py),再scale(sx, sy),再反响translate
//        //canvas.scale(0.5f, 0.5f, 200,200);
//
//        canvas.translate(200, 200);
//        canvas.scale(0.5f, 0.5f);
//        canvas.translate(-200, -200);
//
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(200,200, 700,700, mPaint);
//        canvas.drawLine(0,0, 400, 600, mPaint);

//        //旋转操作
//        canvas.translate(50,50);
//        canvas.drawRect(0,0, 700,700, mPaint);
//        canvas.rotate(45);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0, 700,700, mPaint);

//        canvas.drawRect(400, 400, 900, 900, mPaint);
//        canvas.rotate(45, 650, 650); //px, py表示旋转中心的坐标
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(400, 400, 900, 900, mPaint);

//        //倾斜操作
//        canvas.drawRect(0,0, 400, 400, mPaint);
////        canvas.skew(1, 0); //在X方向倾斜45度,Y轴逆时针旋转45
//        canvas.skew(0, 1); //在y方向倾斜45度， X轴顺时针旋转45
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0, 0, 400, 400, mPaint);
        //切割
//        canvas.drawRect(200, 200,700, 700, mPaint);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(200, 800,700, 1300, mPaint);
//        canvas.clipRect(200, 200,700, 700); //画布被裁剪
//        canvas.drawCircle(100,100, 100,mPaint); //坐标超出裁剪区域，无法绘制
//        canvas.drawCircle(300, 300, 100, mPaint); //坐标区域在裁剪范围内，绘制成功

//        canvas.drawRect(200, 200,700, 700, mPaint);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(200, 800,700, 1300, mPaint);
//        canvas.clipOutRect(200,200,700,700); //画布裁剪外的区域
//        canvas.drawCircle(100,100,100,mPaint); //坐标区域在裁剪范围内，绘制成功
//        canvas.drawCircle(300, 300, 100, mPaint);//坐标超出裁剪区域，无法绘制

        //矩阵
//        canvas.drawRect(0,0,500,500, mPaint);
//        Matrix matrix = new Matrix();
////        matrix.setTranslate(50,50);
////        matrix.setRotate(45);
//        matrix.setScale(0.5f, 0.5f);
//        matrix.setScale(1,1);
////        matrix.postScale();/
//        canvas.setMatrix(matrix);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0,500,500, mPaint);

    }
}
