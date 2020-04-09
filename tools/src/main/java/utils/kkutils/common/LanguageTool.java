package utils.kkutils.common;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

import utils.kkutils.AppTool;
import utils.kkutils.db.MapDB;

/***
 * bug
 * 1， 使用webview 后会导致 重置为系统默认语言， 需要在webview 界面结束时再调用initLanguage
 * 2.  需要在每个activity oncreate 里面调用initLanguage
 *
 *
 */
public class LanguageTool {
    /***
     * 初始化 本地语言， 第一个启动页调用
     * @param activity
     */
    public void initLanguage(Activity activity) {

        try {
            Locale locale = MapDB.loadObjByDefault("locale", Locale.class, null);
            if(locale!=null){
                setLanguage(activity, null, locale);
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    /**
     * 是否简体
     * @return
     */
    public boolean isSimpleChinese(){
        return Locale.SIMPLIFIED_CHINESE.equals(getLanguage());
    }

    /***
     * 是否繁体
     * @return
     */
    public boolean isTw(){
        return Locale.TAIWAN.equals(getLanguage());
    }

    /***
     * 是否中文， 包含简体繁体
     * @return
     */
    public boolean isChinese(){
        return Locale.CHINESE.getLanguage().equals(getLanguage().getLanguage());
    }

    /***
     * 是否英文
     * @return
     */
    public boolean isEn(){
        return Locale.ENGLISH.getLanguage().equals(getLanguage().getLanguage());
    }

    public Locale getLanguage() {
        return AppTool.getApplication().getResources().getConfiguration().locale;
    }

    /***
     * 需要在每个 activity 初始化的时候调用， 可以放在父类里面
     *  @param activity
     * @param restartActivity  重新启动的 activity ， 启动页， null 不重启
     */
    public static  void setLanguage(Activity activity, Class restartActivity, Locale locale) {
        try {
            Resources resources = AppTool.getApplication().getResources();// 获得res资源对象
            Configuration config = resources.getConfiguration();// 获得设置对象
            DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
            config.locale=locale;
            resources.updateConfiguration(config, dm);
            if (activity != null) activity.getResources().updateConfiguration(config, dm);


            MapDB.saveObj(true,"locale", locale);

            if (restartActivity != null) {
                LayoutInflaterTool.clearAll();
                Intent intent = new Intent(AppTool.getApplication(), restartActivity);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                AppTool.getApplication().startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
}
