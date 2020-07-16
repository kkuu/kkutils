package utils.kkutils.ui;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.core.widget.NestedScrollView;

import android.content.res.TypedArray;
import android.util.AttributeSet;

import utils.kkutils.R;

/**
 * Created by ishare on 2016/6/13.
 */
public class KKScrollView extends NestedScrollView {
    float downY;
    float downX;
    double yDistance;
    double xDistance;
    OnScrollChangeListener onScrollChangeListener;


    public KKScrollView(Context context) {
        this(context, null);
    }

    public KKScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KKScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs!=null){
            @SuppressLint("Recycle")
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KKScrollView);
            maxHeight=typedArray.getDimension(R.styleable.KKScrollView_maxHeight, 0);
        }

    }
    public void init(){

    }

    public float maxHeight=0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(maxHeight>0){
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) maxHeight, MeasureSpec.AT_MOST));
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        if (getChildCount() > 0) {//解决内部有listview  或者recycleview  时， 自动滚动到下面的bug
            getChildAt(0).setFocusable(true);
            getChildAt(0).setFocusableInTouchMode(true);
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public OnScrollChangeListener getOnScrollChangeListener() {
        return onScrollChangeListener;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
