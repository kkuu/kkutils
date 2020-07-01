package utils.kkutils.ui.daojishi;

import android.widget.TextView;

import java.util.Calendar;

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
                        UiTool.setTextView(tv_daojishi, getDaoJiShi_Long(second * 1000));
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

    /***
     * 14天06小时16分24秒
     * @param timeMillis
     * @return
     */
    public static String getDaoJiShi_Tian(long timeMillis){
        try {
            int[] ints = TimeTool.splitTimes(timeMillis);
            return String.format("%02d天%02d小时%02d分%02d秒", ints[0],ints[1],ints[2],ints[3]);
        }catch (Exception e){
            LogTool.ex(e);
        }
        return "";
    }

    /***
     * 342:16:24
     * @param timeMillis
     * @return
     */
    public static String getDaoJiShi_Long(long timeMillis){
        try {
            int[] ints = TimeTool.splitTimes(timeMillis);
            ints[1]=ints[1]+ints[0]*24;
            return String.format("%02d:%02d:%02d", ints[1],ints[2],ints[3]);
        }catch (Exception e){
            LogTool.ex(e);
        }
        return "";
    }

}
