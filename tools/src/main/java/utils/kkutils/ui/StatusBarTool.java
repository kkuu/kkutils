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
import utils.kkutils.common.ResourcesTool;

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
            setStatusBarColor(activity.getWindow(),bgColor,isTvBlack,isLayoutBelowToStatusBar);
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    /***
     * 设置状态栏颜色,和文字颜色
     * @param  isLayoutBelowToStatusBar   contentView 是否在 状态栏的下面一层
     */
    public static void setStatusBarColor(Window window, int bgColor, boolean isTvBlack, boolean isLayoutBelowToStatusBar){
        try {
            bgColor= ResourcesTool.getColor(bgColor);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if(!isLayoutBelowToStatusBar)option=0;
            {
                window.setStatusBarColor(bgColor);
                // 设置状态栏底色白色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                if(isTvBlack){  // 设置状态栏字体黑色
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|option);
                }else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE|option);
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }



}
