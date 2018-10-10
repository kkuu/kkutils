package utils.wzutils.common;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

import utils.wzutils.AppTool;
import utils.wzutils.db.MapDB;

public class LanguageTool {
    /***
     * 初始化 本地语言， 第一个启动页调用
     * @param activity
     */
    public void initLanguage(Activity activity) {

        Locale locale = MapDB.loadObjByDefault("locale", Locale.class, null);
        if(locale!=null){
            setLanguage(activity, null, locale);
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
     *  @param activity
     * @param restartActivity  重新启动的 activity ， 启动页， null 不重启
     */
    public void setLanguage(Activity activity, Class restartActivity, Locale locale) {
        Resources resources = AppTool.getApplication().getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale=locale;
        resources.updateConfiguration(config, dm);
        if (activity != null) activity.getResources().updateConfiguration(config, dm);


        MapDB.saveObj("locale", locale);

        if (restartActivity != null) {
            LayoutInflaterTool.clearAll();
            Intent intent = new Intent(AppTool.getApplication(), restartActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            AppTool.getApplication().startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
             System.exit(0);
        }
    }
}
