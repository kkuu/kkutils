package utils.kkutils.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by ishare on 2016/6/28.
 * 自动换行的
 */
@Deprecated
public class KKAutoLayoutOld extends RelativeLayout {
    public KKAutoLayoutOld(Context context) {
        super(context);
    }

    public KKAutoLayoutOld(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KKAutoLayoutOld(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KKAutoLayoutOld(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = layout(false);
        setMeasuredDimension(getMeasuredWidth(), h);
    }

    public int layout(boolean layout) {
        int currLeft = 0;
        int currTop = 0;
        int temRight = 0;
        int temBottom = 0;

        int w = getMeasuredWidth();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childW = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();

            temRight = currLeft + childW;
            temBottom = currTop + childH;

            if (temRight > w) {//宽超过 了就换行
                currLeft = 0;
                currTop = temBottom;

                temRight = currLeft + childW;
                temBottom = currTop + childH;
            }
            if (layout) child.layout(currLeft, currTop, temRight, temBottom);
            currLeft = temRight;
        }
        return temBottom;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        layout(true);
    }
}
