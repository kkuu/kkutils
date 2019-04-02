package utils.wzutils.common;

import android.app.Activity;
import android.os.Build;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.view.WindowManager;

/***
 * 系统UI 的工具
 */
public class OsUiTool {
    /***
     * 设置状态栏颜色
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color){
        try {
            activity.getWindow().setStatusBarColor(color);
            boolean isLight= ColorUtils.calculateLuminance(color)>=0.5;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 设置状态栏底色白色
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if(isLight){
                    // 设置状态栏字体黑色
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }else {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
}
