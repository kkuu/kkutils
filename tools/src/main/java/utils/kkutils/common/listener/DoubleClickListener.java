package utils.kkutils.common.listener;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import utils.kkutils.AppTool;

public abstract class DoubleClickListener implements View.OnTouchListener {
    //双击间四百毫秒延时
    public static int timeout = 400;
    //记录连续点击次数
    protected int clickCount = 0;
    protected Handler handler;
    public DoubleClickListener() {
        handler = new Handler();
    }
    public abstract void doubleClick(View v);
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            clickCount++;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                     if (clickCount >= 2) {
                        doubleClick(v);
                    }
                    //清空handler延时，并防内存泄漏
                    //计数清零
                    handler.removeCallbacksAndMessages(null);
                     clickCount = 0;
                }
            }, timeout);
        }
        return false;////当setOnTouchListener和setOnclickListener一起使用，onTouch为true，则不会执行onClick方法，为false的才会执行onClick方法。    但是false 的时候只能收到 ACTION_DOWN事件
    }
}
