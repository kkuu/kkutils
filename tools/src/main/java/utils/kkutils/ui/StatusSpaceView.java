package utils.kkutils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;

import utils.kkutils.common.UiTool;

/***
 * 状态栏高度的view
 */
public class StatusSpaceView extends View {
    public StatusSpaceView(Context context) {
        super(context);
        init();
    }

    public StatusSpaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusSpaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public StatusSpaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public void init(){
        post(new Runnable() {
            @Override
            public void run() {
                UiTool.setWH(StatusSpaceView.this, 1, BarUtils.getStatusBarHeight());
            }
        });
    }
}
