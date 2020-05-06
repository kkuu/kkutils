package utils.kkutils.parent;

import android.view.View;

import utils.kkutils.common.LogTool;

/**
 * Created by kk on 2017/3/22.
 */

public abstract class KKViewOnclickListener implements View.OnClickListener {
    static  long timePreClick=0;
    boolean allowQuickClick;
    long quickClickTime=300;//间隔300毫秒才能点击
    @Override
    public void onClick(View v) {
        try {
            if(!allowQuickClick){
                long time=System.currentTimeMillis();
                if(time-timePreClick<quickClickTime)return;//300毫秒内只能点击一次
                timePreClick=time;
            }
            onClickKK(v);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public KKViewOnclickListener(boolean allowQuickClick){
        setAllowQuickClick(allowQuickClick);
    }
    public KKViewOnclickListener(long  quickClickTime){
        setAllowQuickClick(false);
        this.quickClickTime=quickClickTime;
    }
    public KKViewOnclickListener(){
    }
    /***
     * 是否允许快速点击
     * @param allowQuickClick
     */
    public void setAllowQuickClick(boolean allowQuickClick){
        this.allowQuickClick=allowQuickClick;
    }
    public abstract void onClickKK(View v);
}
