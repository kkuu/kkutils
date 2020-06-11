package utils.kkutils.ui.daojishi;

import android.widget.TextView;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.TimeTool;
import utils.kkutils.common.UiTool;

public class DaoJiShiTool {
    public interface OnSetTime{
        public void onSet(TextView tv, int second);
    }
    public static void showDaoJiShi(TextView tv_daojishi,int secondIn,OnSetTime onSetTime ){
        secondIn=1230;
        tv_daojishi.setTag(secondIn);
        tv_daojishi.post(new Runnable() {
            @Override
            public void run() {
                int  second = (int) tv_daojishi.getTag();
                if(onSetTime!=null){
                    onSetTime.onSet(tv_daojishi, second);
                }else {
                    UiTool.setTextView(tv_daojishi, TimeTool.getShortTimeStr(second*1000));
                }
                LogTool.s("倒计时："+second);
                if(second>0){
                    tv_daojishi.setTag(--second);
                    tv_daojishi.postDelayed(this, 1000);
                }
            }
        });
    }
}
