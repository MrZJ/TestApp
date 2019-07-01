package test.com.testapp.layout;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import test.com.testapp.R;

/**
 * Created by zhangjian on 2019/6/25 10:47
 */
public class PercentRelativeLayout extends RelativeLayout {
    public PercentRelativeLayout(Context context) {
        this(context, null);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int rootWidth = MeasureSpec.getSize(widthMeasureSpec);
        int rootHeight = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            if (checkLayoutParams(layoutParams)) {
                LayoutParams params = (LayoutParams) layoutParams;
                if (params.widthPercent > 0) {
                    params.width = (int) (rootWidth * params.widthPercent);
                }
                if (params.heightPercent > 0) {
                    params.height = (int) (rootHeight * params.widthPercent);
                }
                if (params.marginLeftPercent > 0) {
                    params.leftMargin = (int) (rootWidth * params.marginLeftPercent);
                }
                if (params.marginRightPercent > 0) {
                    params.rightMargin = (int) (rootWidth * params.marginRightPercent);
                }
                if (params.marginTopPercent > 0) {
                    params.topMargin = (int) (rootHeight * params.marginTopPercent);
                }
                if (params.marginBottomPercent > 0) {
                    params.bottomMargin = (int) (rootHeight * params.marginBottomPercent);
                }
                Log.e("jianzhang", "bottomMargin = " + params.bottomMargin + ",view height = " + params.height);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    //todo 还要重写一下这个方法，查看源码发现ViewGroup如果addview方法传入的不是LayoutParams 则会使用该方法创建一个LayoutParams,这里就不补充了
//
//    private void addViewInner(View child, int index, ViewGroup.LayoutParams params,
//                              boolean preventRequestLayout) {
//
//        if (!checkLayoutParams(params)) {
//            params = generateLayoutParams(params);
//        }
//
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return super.generateLayoutParams(lp);
    }

    class LayoutParams extends RelativeLayout.LayoutParams {
        float widthPercent;
        float heightPercent;
        float marginLeftPercent;
        float marginRightPercent;
        float marginTopPercent;
        float marginBottomPercent;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.PercentRelativeLayout);
            widthPercent = array.getFloat(R.styleable.PercentRelativeLayout_widthPercent, 0);
            heightPercent = array.getFloat(R.styleable.PercentRelativeLayout_heightPercent, 0);
            marginLeftPercent = array.getFloat(R.styleable.PercentRelativeLayout_marginLeftPercent, 0);
            marginRightPercent = array.getFloat(R.styleable.PercentRelativeLayout_marginRightPercent, 0);
            marginTopPercent = array.getFloat(R.styleable.PercentRelativeLayout_marginTopPercent, 0);
            marginBottomPercent = array.getFloat(R.styleable.PercentRelativeLayout_marginBottomPercent, 0);
            array.recycle();
        }

    }
}
