package utils.kkutils.ui.daojishi;

import android.widget.TextView;

import java.util.Calendar;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.TimeTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.common.ViewTool;

/***
 *  倒计时显示
 *
 *
 *         DaoJiShiTool.startDaoJiShiMillisecond(tv_span, 1000 * 60, 100, new DaoJiShiTool.OnSetTime() {
 *             @Override
 *             public void onSet(TextView tv, long second) {
 *                 tv.setText(""+ DaoJiShiTool.getDaoJiShi_Long_millis(second));
 *             }
 *         });
 *
 * //        DaoJiShiTool.startDaoJiShi(tv_span, 90, new DaoJiShiTool.OnSetTime() {
 * //            @Override
 * //            public void onSet(TextView tv, long second) {
 * //                tv.setText(""+second);
 * //            }
 * //        });
 *
 */
public class DaoJiShiTool {
    public interface OnSetTime{
        /***
         *
         * @param tv
         * @param secondOrMilliSceond  秒或者毫秒，看调用的哪一个方法
         */
        public void onSet(TextView tv, long secondOrMilliSceond);
    }
   static int keyRunable= ViewTool.initKey();

    /**
     * 秒钟倒计时
     * @param tv_daojishi
     * @param secondIn
     * @param onSetTime
     */
    public static void startDaoJiShi(TextView tv_daojishi,long secondIn,OnSetTime onSetTime ){
        try {
            startDaoJiShiMillisecond(tv_daojishi, secondIn * 1000, 1000, new OnSetTime() {
                @Override
                public void onSet(TextView tv, long second) {
                    if (onSetTime != null) {
                        onSetTime.onSet(tv_daojishi, second/1000);
                    } else {
                        UiTool.setTextView(tv_daojishi, getDaoJiShi_Long(second ));
                    }
                }

            });
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    /***
     * 毫秒倒计时
     * @param tv_daojishi
     * @param millisecond 起始时间
     * @param millisecondStep 每次间隔
     * @param onSetTime  回调
     */
    public static void startDaoJiShiMillisecond (TextView tv_daojishi,long millisecond,long millisecondStep,OnSetTime onSetTime ){
        try {
            tv_daojishi.setTag(millisecond);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    long second = (long) tv_daojishi.getTag();
                    if (onSetTime != null) {
                        onSetTime.onSet(tv_daojishi, second);
                    } else {
                        UiTool.setTextView(tv_daojishi, getDaoJiShi_Long(second ));
                    }
//                    LogTool.s("倒计时：" + second);
                    if (second > 0) {
                        second=second-millisecondStep;
                        tv_daojishi.setTag(second);
                        tv_daojishi.postDelayed(this, millisecondStep);
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
    /***
     * 342:16:24.8
     * @param timeMillis
     * @return
     */
    public static String getDaoJiShi_Long_millis(long timeMillis){
        try {
            int[] ints = TimeTool.splitTimes(timeMillis);
            ints[1]=ints[1]+ints[0]*24;
            return String.format("%02d:%02d:%02d.%1d", ints[1],ints[2],ints[3],ints[4]/100);
        }catch (Exception e){
            LogTool.ex(e);
        }
        return "";
    }
}
