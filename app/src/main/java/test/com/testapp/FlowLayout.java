package test.com.testapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjian on 2019/7/2 14:29
 * 依托RelativeLayout写的一个流式布局
 */
public class FlowLayout extends RelativeLayout {
    private List<String> tags = new ArrayList<>();
    private boolean isInitialed = false;
    private int rootWidth;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isInitialed) {
                    isInitialed = true;
                    drawTags();
                }
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rootWidth = w;
    }

    private void drawTags() {
        removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        int totalWidth = 0;
        int currentIndex = 1;
        int beforIndex = 0;
        for (String tag : tags) {
            View child = inflater.inflate(R.layout.layout_tag, null);
            child.setId(currentIndex);
            TextView textView = child.findViewById(R.id.tag_txt);
            textView.setText(tag);
            textView.setPadding(10, 5, 10, 5);
            float textWidth = textView.getPaint().measureText(tag) + 20;//20为marginLeft值
            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (totalWidth + textWidth < rootWidth) {
                params.addRule(RIGHT_OF, beforIndex);
                params.addRule(ALIGN_TOP, beforIndex);
                if (currentIndex > 1) {
                    params.leftMargin = 20;
                    totalWidth += 20;
                }
            } else {
                params.addRule(BELOW, beforIndex);
                params.topMargin = 20;
                totalWidth = 0;
            }
            totalWidth += textWidth;
            addView(child, params);
            beforIndex = currentIndex;
            currentIndex++;
        }
    }

    public void addTags(List<String> list) {
        if (list != null) {
            tags = list;
            drawTags();
        } else {
            removeAllViews();
        }
    }
}
