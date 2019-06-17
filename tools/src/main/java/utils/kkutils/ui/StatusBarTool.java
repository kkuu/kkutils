package utils.kkutils.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import androidx.core.graphics.ColorUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.BarUtils;

import utils.kkutils.R;
import utils.kkutils.common.LogTool;

/***
 * 只用 这个  public static void setStatusBarColor(Activity activity,int bgColor, boolean isTvBlack,boolean isLayoutBelowToStatusBar)
 */
public class StatusBarTool {
    public static int getDarkColorPrimary(Activity activity){
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }


    /***
     * 设置状态栏颜色,和文字颜色
     * @param  isLayoutBelowToStatusBar   contentView 是否在 状态栏的下面一层
     */
    public static void setStatusBarColor(Activity activity,int bgColor, boolean isTvBlack,boolean isLayoutBelowToStatusBar){
        try {
            LogTool.s("setStatusBarColor "+activity);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if(!isLayoutBelowToStatusBar)option=0;
            {
                activity.getWindow().setStatusBarColor(bgColor);
                // 设置状态栏底色白色
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                if(isTvBlack){  // 设置状态栏字体黑色
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|option);
                }else {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE|option);
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }





















    /***
     * 设置状态栏颜色,不要设置透明， 透明用
     * @see  StatusBarTool#setStatusBarTouMing(Activity)
     * @param activity
     * @param color
     * @param  isLayoutFullScreen   contentView 是否在 状态栏的下面一层
     */
    public static void setStatusBarColor(Activity activity, int color,boolean isLayoutFullScreen){
        try {
            LogTool.s("setStatusBarColor "+activity);
            boolean isLight= ColorUtils.calculateLuminance(color)>=0.5;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){//android 6 上才可设置状态栏浅色， 对应文字深色， android 5 没法改文字颜色，所以不能用浅色
                if(isLight){
                    color=getDarkColorPrimary(activity);//使用系统深色背景
                }
            }

            {
                activity.getWindow().setStatusBarColor(color);
                // 设置状态栏底色白色
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if(isLight){
                    // 设置状态栏字体黑色
                    if(isLayoutFullScreen){
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|getSystemUiOptionFullscreen());
                    }else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                }else {
                    if(isLayoutFullScreen){
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE|getSystemUiOptionFullscreen());
                    }else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    public static void setStatusBarColor(Activity activity, int color){
        setStatusBarColor(activity,color,false);
    }



    /***
     * 设置状态栏透明 自带SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * @param activity
     */
    public static void setStatusBarTouMing(Activity activity){
        LogTool.s("setStatusBarTouMing "+activity);
        Window window = activity.getWindow();

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//设置状态栏颜色白色
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawable(null);
        BarUtils.setStatusBarColor(activity, Color.TRANSPARENT);
    }
    /***
     * 设置状态栏半透明 自带SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * @param activity
     */
    public static void setStatusBarBanTouMing(Activity activity){
        setStatusBarTouMing(activity);
        BarUtils.setStatusBarColor(activity, Color.parseColor("#55000000"));

    }







    static int getSystemUiOptionFullscreen(){
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        return option;
    }

}
