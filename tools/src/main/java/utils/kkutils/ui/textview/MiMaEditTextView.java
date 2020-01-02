package utils.kkutils.ui.textview;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.maning.pswedittextlibrary.MNPasswordEditText;

import utils.kkutils.common.LogTool;

/**
 * abc on 2017/11/16.
 */

public class MiMaEditTextView extends MNPasswordEditText {

    public MiMaEditTextView(Context context) {
        super(context);
        init();
    }

    public MiMaEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MiMaEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
}
