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

    public  static int language=1;//1 中文 2英文

    /***
     * 初始化 本地语言， 第一个启动页调用
     * @param activity
     */
    public  void initLanguage(Activity activity){
        language= MapDB.loadObjByDefault("language",Integer.class,1);
        setLanguage(activity,null,language==1);
    }
    /***
     * 获取当前语言
     *
     *
     * if("zh_cn".equals(getLanguage().toLowerCase())){
     *             rg_yuyan.check(rb_zhongwen.getId());
     *         }else {
     *             rg_yuyan.check(rb_yinwen.getId());
     *         }
     *
     *
     * @return
     */
    public  String getLanguage() {
        return AppTool.getApplication().getResources().getConfiguration().locale.toString();
    }

    /***
     *
     * @param activity
     * @param restartActivity  重新启动的 activity ， 启动页， null 不重启
     * @param isCn
     */
    public  void setLanguage(Activity activity,Class restartActivity, boolean isCn) {
        Resources resources = AppTool.getApplication().getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。

        //en_US-英文，zh_CN-简体中文，zh_TW-繁体中文
        if(isCn){
            config.locale= Locale.SIMPLIFIED_CHINESE;
        }else {
            config.locale=Locale.US;
        }
        resources.updateConfiguration(config, dm);
        if(activity!=null)activity.getResources().updateConfiguration(config, dm);

        language=isCn?1:2;
        MapDB.saveObj("language",language);

        if(restartActivity!=null){
            LayoutInflaterTool.clearAll();
            Intent intent=new Intent(AppTool.getApplication(), restartActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            AppTool.getApplication().startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
}
