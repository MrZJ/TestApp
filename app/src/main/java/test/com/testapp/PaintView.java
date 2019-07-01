package test.com.testapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposeShader;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

/**
 * Created by zhangjian on 2019/7/1 10:22
 */
public class PaintView extends View {


    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private Bitmap mBitmap;
    private Shader mShader;

    private static int W = 250;
    private static int H = 250;

    private static final int ROW_MAX = 4;   // number of samples per row

    private Bitmap mSrcB;
    private Bitmap mDstB;
    private Shader mBG;     // background checker-board pattern

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);

        mPaint = new Paint(); //初始化
//        mPaint.setColor(Color.RED);// 设置颜色
//        mPaint.setARGB(255, 255, 255, 0); // 设置 Paint对象颜色,范围为0~255
//        mPaint.setAlpha(200); // 设置alpha不透明度,范围为0~255
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setStyle(Paint.Style.FILL); //描边效果
        mPaint.setStrokeWidth(4);//描边宽度
//        mPaint.setStrokeCap(Paint.Cap.ROUND); //圆角效果
//        mPaint.setStrokeJoin(Paint.Join.MITER);//拐角风格
//        mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED)); //设置环形渲染器
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN)); //设置图层混合模式
//        mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000)); //设置颜色过滤器
//        mPaint.setFilterBitmap(true); //设置双线性过滤
//        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜 ,传入度数和样式
//        mPaint.setTextScaleX(2);// 设置文本缩放倍数
//        mPaint.setTextSize(38);// 设置字体大小
//        mPaint.setTextAlign(Paint.Align.LEFT);//对其方式
//        mPaint.setUnderlineText(true);// 设置下划线
//
//        String str = "Android高级工程师";
//        Rect rect = new Rect();
//        mPaint.getTextBounds(str, 0, str.length(), rect); //测量文本大小，将文本大小信息存放在rect中
//        mPaint.measureText(str); //获取文本的宽
//        mPaint.getFontMetrics(); //获取字体度量对象


        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
            W = H = (display.widthPixels - 64) / ROW_MAX; //得到矩形
        }

        //1，API 14之后，有些函数不支持硬件加速，需要禁用
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mSrcB = makeSrc(W, H);
        mDstB = makeDst(W, H);

        //根据width和height创建空位图，然后用指定的颜色数组colors来从左到右从上至下一次填充颜色
        //make a ckeckerboard pattern
        Bitmap bm = Bitmap.createBitmap(new int[]{0xFFFFFFFF, 0xFFCCCCCC, 0xFFCCCCCC, 0xFFFFFFFF}, 2, 2, Bitmap.Config.RGB_565);
        mBG = new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Matrix m = new Matrix();
        m.setScale(6, 6);
        mBG.setLocalMatrix(m);
    }

    // 黑白
    public static final float colormatrix_heibai[] = {
            0.8f, 1.6f, 0.2f, 0, -163.9f,
            0.8f, 1.6f, 0.2f, 0, -163.9f,
            0.8f, 1.6f, 0.2f, 0, -163.9f,
            0, 0, 0, 1.0f, 0};

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        /**
//         * 1.线性渲染,LinearGradient(float x0, float y0, float x1, float y1, @NonNull @ColorInt int colors[], @Nullable float positions[], @NonNull TileMode tile)
//         * (x0,y0)：渐变起始点坐标
//         * (x1,y1):渐变结束点坐标
//         * color0:渐变开始点颜色,16进制的颜色表示，必须要带有透明度
//         * color1:渐变结束颜色
//         * colors:渐变数组
//         * positions:位置数组，position的取值范围[0,1],作用是指定某个位置的颜色值，如果传null，渐变就线性变化。
//         * tile:用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
//         */
//        mShader = new LinearGradient(0, 500, 500, 500, new int[]{Color.YELLOW, Color.RED, Color.BLUE}, new float[]{0.1f, 0.2f, 0.7f}, Shader.TileMode.MIRROR);
//        mPaint.setShader(mShader);
//        canvas.drawCircle(500, 500, 500, mPaint);

        //        /**
//         * 环形渲染，RadialGradient(float centerX, float centerY, float radius, @ColorInt int colors[], @Nullable float stops[], TileMode tileMode)
//         * centerX ,centerY：shader的中心坐标，开始渐变的坐标
//         * radius:渐变的半径
//         * centerColor,edgeColor:中心点渐变颜色，边界的渐变颜色
//         * colors:渐变颜色数组
//         * stoops:渐变位置数组，类似扫描渐变的positions数组，取值[0,1],中心点为0，半径到达位置为1.0f
//         * tileMode:shader未覆盖以外的填充模式。
//         */
//        mShader = new RadialGradient(500, 500, 200, new int[]{Color.RED, Color.YELLOW}, new float[]{0.5f, 0.5f}, Shader.TileMode.REPEAT);
//        mPaint.setShader(mShader);
//        canvas.drawCircle(500, 500, 500, mPaint);

//        //        /**
////         * 扫描渲染，SweepGradient(float cx, float cy, @ColorInt int color0,int color1)
////         * cx,cy 渐变中心坐标
////         * color0,color1：渐变开始结束颜色
////         * colors，positions：类似LinearGradient,用于多颜色渐变,positions为null时，根据颜色线性渐变
////         */
//        mShader = new SweepGradient(500, 500, new int[]{Color.RED, Color.YELLOW}, new float[]{0.1f, 0.9f});
//        mPaint.setShader(mShader);
//        canvas.drawCircle(500, 500, 500, mPaint);
//        mShader = new SweepGradient(0, 1250, new int[]{Color.RED, Color.YELLOW}, new float[]{0.1f, 0.9f});
//        canvas.drawRect(0, 1000, 1000, 1500, mPaint);
////        //        /**
//////         * 位图渲染，BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
//////         * Bitmap:构造shader使用的bitmap
//////         * tileX：X轴方向的TileMode
//////         * tileY:Y轴方向的TileMode
////               REPEAT, 绘制区域超过渲染区域的部分，重复排版
////               CLAMP， 绘制区域超过渲染区域的部分，会以最后一个像素拉伸排版
////               MIRROR, 绘制区域超过渲染区域的部分，镜像翻转排版
//////         */
//        mShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP);
//        mPaint.setShader(mShader);
//        canvas.drawRect(0, 0, 1000, 1000, mPaint);
//        canvas.drawCircle(500, 500, 500, mPaint);

//        /**
//         * 组合渲染，
//         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, Xfermode mode)
//         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, PorterDuff.Mode mode)
//         * shaderA,shaderB:要混合的两种shader
//         * Xfermode mode： 组合两种shader颜色的模式
//         * PorterDuff.Mode mode: 组合两种shader颜色的模式
//         */
//
//        mShader = new ComposeShader(new LinearGradient(0, 500, 500, 500, new int[]{Color.YELLOW, Color.RED, Color.BLUE}, new float[]{0.1f, 0.2f, 0.7f}, Shader.TileMode.MIRROR)
//                , new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP), PorterDuff.Mode.DST);
//        mShader = new ComposeShader(new LinearGradient(0, 500, 500, 500, new int[]{Color.YELLOW, Color.RED, Color.BLUE}, new float[]{0.1f, 0.2f, 0.7f}, Shader.TileMode.MIRROR)
//                , new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP), new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
//        mPaint.setShader(mShader);
//        canvas.drawRect(0, 0, 1000, 1000, mPaint);


//        /**
//         * LightingColorFilter
//         * mul:减去的色值
//         * add：添加的色值
//         */
//        mPaint.reset();
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//        mPaint.setColorFilter(new LightingColorFilter(Color.parseColor("#00ffff"), Color.parseColor("#000000")));//减少红色
//        canvas.drawBitmap(mBitmap, 0, 500, mPaint);
//        mPaint.setColorFilter(new LightingColorFilter(Color.parseColor("#ff00ff"), Color.parseColor("#000000")));//减少绿色
//        canvas.drawBitmap(mBitmap, 0, 1000, mPaint);
//        mPaint.setColorFilter(new LightingColorFilter(Color.parseColor("#ffff00"), Color.parseColor("#000000")));//减少蓝色
//        canvas.drawBitmap(mBitmap, 0, 1500, mPaint);
//        mPaint.setColorFilter(new LightingColorFilter(Color.parseColor("#ffffff"), Color.parseColor("#0000ff")));//添加蓝色
//        canvas.drawBitmap(mBitmap, 500, 500, mPaint);
//        mPaint.setColorFilter(new LightingColorFilter(Color.parseColor("#ffffff"), Color.parseColor("#00ff00")));//添加绿色
//        canvas.drawBitmap(mBitmap, 500, 1000, mPaint);
//        mPaint.setColorFilter(new LightingColorFilter(Color.parseColor("#ffffff"), Color.parseColor("#ff0000")));//添加红色
//        canvas.drawBitmap(mBitmap, 500, 1500, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colormatrix_heibai));
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

//        /**
//         * ColorMatrixColorFilter
//         */
//        mPaint.reset();
//        ColorMatrix colorMatrix = new ColorMatrix();
//        colorMatrix.setScale(1f, 0.4f, 0.4f, 0.5f);
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

//        /**
//         * PorterDuffColorFileter
//         */
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC));
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.DST));
//        canvas.drawBitmap(mBitmap, 0, 500, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_OVER));
//        canvas.drawBitmap(mBitmap, 0, 1000, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.DST_OVER));
//        canvas.drawBitmap(mBitmap, 0, 1500, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.OVERLAY));
//        canvas.drawBitmap(mBitmap, 500, 0, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.ADD));
//        canvas.drawBitmap(mBitmap, 500, 500, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY));
//        canvas.drawBitmap(mBitmap, 500, 1000, mPaint);
//        mPaint.reset();
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN));
//        canvas.drawBitmap(mBitmap, 500, 1500, mPaint);


        /**
         * setXfermode 设置图层混合
         */
//        canvas.drawColor(Color.WHITE);
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速，因为有些API在低版本硬件加速并不支持，例如:PorterDuff.Mode.LIGHTEN,PorterDuff.Mode.DARKEN,PorterDuff.Mode.OVERLAY
//        Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
//        labelP.setTextAlign(Paint.Align.CENTER);
//
//        Paint paint = new Paint();
//        paint.setFilterBitmap(false);
//
//        canvas.translate(15, 35);
//
//        int x = 0;
//        int y = 0;
//        for (int i = 0; i < sModes.length; i++) {
//            // draw the border
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setShader(null);
//            canvas.drawRect(x - 0.5f, y - 0.5f, x + W + 0.5f, y + H + 0.5f, paint);
//
//            // draw the checker-board pattern
//            paint.setStyle(Paint.Style.FILL);
//            paint.setShader(mBG);
//            canvas.drawRect(x, y, x + W, y + H, paint);
//
//            // 使用离屏绘制
//            int sc = canvas.saveLayer(x, y, x + W, y + H, null, ALL_SAVE_FLAG);
//            canvas.translate(x, y);
//            canvas.drawBitmap(makeDst(2 * W / 3, 2 * H / 3), 0, 0, paint);
//            paint.setXfermode(sModes[i]);
//            canvas.drawBitmap(makeSrc(2 * W / 3, 2 * H / 3), W / 3, H / 3, paint);
//            paint.setXfermode(null);
//            canvas.restoreToCount(sc);
//
//            // draw the label
//            labelP.setTextSize(20);
//            canvas.drawText(sLabels[i], x + W / 2, y - labelP.getTextSize() / 2, labelP);
//
//            x += W + 10;
//
//            // wrap around when we've drawn enough for one row
//            if ((i % ROW_MAX) == ROW_MAX - 1) {
//                x = 0;
//                y += H + 30;
//            }
//        }


//        //1，平移操作
//        canvas.drawRect(0,0, 400, 400, mPaint);
//        canvas.translate(50, 50);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0, 400, 400, mPaint);
//        canvas.drawLine(0, 0, 600,600, mPaint);

//        //缩放操纵
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

//        //矩阵
//        canvas.drawRect(0,0,700,700, mPaint);
//        Matrix matrix = new Matrix();
////        matrix.setTranslate(50,50);
////        matrix.setRotate(45);
//        matrix.setScale(0.5f, 0.5f);
//        canvas.setMatrix(matrix);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0,700,700, mPaint);

//
//        /**
//         * 1.canvas内部对于状态的保存存放在栈中
//         * 2.可以多次调用save保存canvas的状态，并且可以通过getSaveCount方法获取保存的状态个数
//         * 3.可以通过restore方法返回最近一次save前的状态，也可以通过restoreToCount返回指定save状态。指定save状态之后的状态全部被清除
//         * 4.saveLayer可以创建新的图层，之后的绘制都会在这个图层之上绘制，直到调用restore方法
//         * 注意：绘制的坐标系不能超过图层的范围， saveLayerAlpha对图层增加了透明度信息
//         */
//
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

//        canvas.drawRect(200,200, 700,700, mPaint);
//
//        int layerId = canvas.saveLayer(0,0, 700, 700, mPaint);
//        mPaint.setColor(Color.GRAY);
//        Matrix matrix = new Matrix();
//        matrix.setTranslate(100,100);
//        canvas.setMatrix(matrix);
//        canvas.drawRect(0,0,700,700, mPaint); //由于平移操作，导致绘制的矩形超出了图层的大小，所以绘制不完全
//        canvas.restoreToCount(layerId);
//
//        mPaint.setColor(Color.RED);
//        canvas.drawRect(0,0,100,100, mPaint);
    }

    //把谷歌官方的例子拷贝过来
    //其中Sa全称为Source alpha表示源图的Alpha通道；Sc全称为Source color表示源图的颜色；Da全称为Destination alpha表示目标图的Alpha通道；Dc全称为Destination color表示目标图的颜色，[...,..]前半部分计算的是结果图像的Alpha通道值，“,”后半部分计算的是结果图像的颜色值。
    //效果作用于src源图像区域
    private static final Xfermode[] sModes = {
            //所绘制不会提交到画布上
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            //显示上层绘制的图像
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            //显示下层绘制图像
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            //正常绘制显示，上下层绘制叠盖
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),

            //上下层都显示，下层居上显示
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            //取两层绘制交集，显示上层
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            //取两层绘制交集，显示下层
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            //取上层绘制非交集部分，交集部分变成透明
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),

            //取下层绘制非交集部分，交集部分变成透明
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            //取上层交集部分与下层非交集部分
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            //取下层交集部分与上层非交集部分
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            //去除两图层交集部分
            new PorterDuffXfermode(PorterDuff.Mode.XOR),

            //取两图层全部区域，交集部分颜色加深
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            //取两图层全部区域，交集部分颜色点亮
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            //取两图层交集部分，颜色叠加
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            //取两图层全部区域，交集部分滤色
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN),

            //取两图层全部区域，交集部分饱和度相加
            new PorterDuffXfermode(PorterDuff.Mode.ADD),
            //取两图层全部区域，交集部分叠加
            new PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
    };

    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen", "Add", "Overlay"
    };

    // create a bitmap with a circle, used for the "dst" image
    // 画圆一个完成的圆
    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    // 矩形右下角留有透明间隙
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
        c.drawRect(0, 0, w * 19 / 20, h * 19 / 20, p);
        return bm;
    }
}
