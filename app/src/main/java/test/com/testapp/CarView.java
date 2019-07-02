package test.com.testapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangjian on 2019/7/2 13:57
 */
public class CarView extends View {
    private Paint linePaint, carPaint;
    private Matrix matrix = new Matrix();
    private Bitmap bitmap;
    private Path path;

    public CarView(Context context) {
        this(context, null);
    }

    public CarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(5);
        carPaint = new Paint();
        carPaint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_car);
        path = new Path();
    }

    float distance = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();

        path.reset();
        path.addCircle(0, 0, 400, Path.Direction.CCW);
        canvas.translate(width / 2, height / 2);
        canvas.drawPath(path, linePaint);
        distance += 0.01;
        if (distance >= 1) {
            distance = 0;
        }
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getMatrix(pathMeasure.getLength() * distance, matrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.preTranslate(-bitmap.getWidth(), -bitmap.getHeight());
        canvas.drawBitmap(bitmap, matrix, carPaint);
        invalidate();
    }
}
