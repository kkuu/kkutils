package utils.wzutils.ui;

import android.content.Context;
import android.util.AttributeSet;

import utils.wzutils.common.CommonTool;

/***
 * 可以设置最大高度 的ScrollView
 * 一般用于dialog中
 */
public class MaxHeightScrollView extends KKScrollView {
    public MaxHeightScrollView(Context context) {
        super(context);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public int maxHeight= CommonTool.getWindowSize().y/2;
    public void setMaxHeight(int maxHeightPixel){
        maxHeight=maxHeightPixel;
        invalidate();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(maxHeight>0){
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(maxHeight,MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

}
