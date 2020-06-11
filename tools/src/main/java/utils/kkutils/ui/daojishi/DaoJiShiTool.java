package utils.kkutils.ui.daojishi;

import android.widget.TextView;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.TimeTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.common.ViewTool;

public class DaoJiShiTool {
    public interface OnSetTime{
        public void onSet(TextView tv, int second);
    }
   static int keyRunable= ViewTool.initKey();

    public static void startDaoJiShi(TextView tv_daojishi,int secondIn,OnSetTime onSetTime ){
        try {
            tv_daojishi.setTag(secondIn);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    int second = (int) tv_daojishi.getTag();
                    if (onSetTime != null) {
                        onSetTime.onSet(tv_daojishi, second);
                    } else {
                        UiTool.setTextView(tv_daojishi, TimeTool.getShortTimeStr(second * 1000));
                    }
                    LogTool.s("倒计时：" + second);
                    if (second > 0) {
                        tv_daojishi.setTag(--second);
                        tv_daojishi.postDelayed(this, 1000);
                    }
                }
            };
            stopDaoJiShi(tv_daojishi);

            tv_daojishi.setTag(keyRunable, runnable);
            tv_daojishi.post(runnable);
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
    public static void stopDaoJiShi(TextView tv_daojishi ){
        try {
            Object tag = tv_daojishi.getTag(keyRunable);
            if(tag!=null && tag instanceof Runnable){
                tv_daojishi.removeCallbacks((Runnable) tag);
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
}
