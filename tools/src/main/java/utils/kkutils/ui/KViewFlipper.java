package utils.kkutils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

import utils.kkutils.common.LogTool;

public class KViewFlipper extends ViewFlipper {
    public KViewFlipper(Context context) {
        super(context);
    }

    public KViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
}
