package kk.kktools;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

import utils.kkutils.AppTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;

public class ShopTool {
    public static void openUrl(String url){

        url="https://s.click.taobao.com/Fp3Qviv";
        //url="https://aihuiguniang.taobao.com/?spm=a1z10.1-c-s.0.0.3d1941c05tcYk8";

        url="https://u.jd.com/fqpmhR" ;
        if(!openPinDuoDuo(url)){
            if(!openTaoBao(url)){
                if(!openJindDong(url)){
                    UiTool.startUrlView(url);
                }
            }
        }


    }



    public static boolean openJindDong(String url){
        return false;
    }

    public static boolean openTaoBao(String url){
        if(CommonTool.checkApkExist("com.taobao.taobao")){
            if(url.contains("taobao.com")){
                UiTool.startUrlView(url,"com.taobao.taobao");
                return true;
            }
        }
        return false;
    }

    /**
     * 打开拼多多
     https://mobile.yangkeduo.com/app.html?use_reload=1&launch_url=duo_coupon_landing.html%3Fgoods_id%3D95338609834%26pid%3D10421334_140080857%26cpsSign%3DCC_200509_10421334_140080857_ec5843bacb7bd87c61f810a79da659a4%26duoduo_type%3D2&campaign=ddjb&cid=launch_dl_force_
     * @param url
     */
    public static boolean openPinDuoDuo(String url){
        if(CommonTool.checkApkExist("com.xunmeng.pinduoduo")){
            if(url.contains("https://mobile.yangkeduo.com/")||url.contains("pinduoduo://com.xunmeng.pinduoduo/")){
                url=url.replace("https://mobile.yangkeduo.com/","pinduoduo://com.xunmeng.pinduoduo/");
                UiTool.startUrlView(url);
                return true;
            }
        }
        return false;
    }



    public static List<ResolveInfo>  getResolveInfo(String url){
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        List<ResolveInfo> resolveInfos = AppTool.getApplication().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        for (ResolveInfo resolveInfo:resolveInfos){
            LogTool.s("打开url"+resolveInfo.activityInfo);
        }
        return resolveInfos;

    }
}
