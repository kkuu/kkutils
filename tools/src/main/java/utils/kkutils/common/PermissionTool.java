package utils.kkutils.common;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PermissionGroupInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blankj.utilcode.*;
import com.blankj.utilcode.util.PermissionUtils;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import utils.kkutils.AppTool;
import utils.kkutils.ui.dialog.DialogSimple;
import utils.kkutils.ui.dialog.DialogTool;

/**
 *权限工具
 */
public class PermissionTool {


    public static void test(){

//        if(!PermissionTool.checkPermissionMust( Manifest.permission.WRITE_EXTERNAL_STORAGE,null)){
//            return null;
//        }


        PermissionTool.checkPermissionMust(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionTool.PermissionListener() {
            @Override
            public void onGranted(List<String> granted) {
                CommonTool.showToast("允许了");
            }

            @Override
            public boolean onDenied(List<String> denied) {
                CommonTool.showToast("拒绝了");
                return false;
            }
        });

    }
    public interface PermissionListener{
        public void onGranted(List<String> granted);
        public boolean onDenied(List<String> denied);
    }


    public static boolean checkPermissionMust(final String  permission,PermissionListener permissionListener){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Activity currActivity = AppTool.currActivity;
            PermissionUtils.permission(permission)
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> granted) {
                            LogTool.s("权限允许了："+granted);
                            if(permissionListener!=null)permissionListener.onGranted(granted);
                        }
                        @Override
                        public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                            if(permissionListener!=null)
                            {
                                if(permissionListener.onDenied(denied))return;
                            }
                            if(!denied.isEmpty()){
                                LogTool.s("权限被拒绝了");
                                AppTool.currActivity=currActivity;
                                DialogTool.initNormalQueDingDialog("提示", "请允许"+getPermissionDes(permission)+"权限", "去设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        PermissionUtils.launchAppDetailsSettings();
                                    }
                                },"取消").show();
                            }
                        }
                    })
                    .request();
        };
        return PermissionUtils.isGranted(permission);
    }
    public static String getPermissionDes(String permission){

        String result="";
        if(permission.contains("STORAGE"))result="存储卡";
        if(permission.contains("CAMERA"))result="相机";
        if(permission.contains("LOCATION"))result="定位";

        if(permission.contains("CALENDAR"))result="日历";
        if(permission.contains("CONTACTS"))result="联系人";
        if(permission.contains("MICROPHONE"))result="麦克风";

        if(permission.contains("PHONE"))result="手机状态";
        if(permission.contains("SMS"))result="短信";
        if(permission.contains("SENSORS"))result="传感器";

        return result;
    }



//    CALENDAR	日历
//
//    CAMERA		相机
//
//    CONTACTS	联系人
//
//    LOCATION	定位
//
//    MICROPHONE	麦克相关，比如录音
//
//    PHONE		手机状态
//
//    SENSORS		传感器
//
//    SMS		短信
//
//    STORAGE		存储权限
















//
//      CALENDAR（日历） 
//
//
//    READ_CALENDAR
//
//            WRITE_CALENDAR
//
//    CAMERA（相机） 
//
//
//    CAMERA
//
//    CONTACTS（联系人） 
//
//
//    READ_CONTACTS
//
//            WRITE_CONTACTS
//
//    GET_ACCOUNTS
//
//    LOCATION（位置） 
//
//
//    ACCESS_FINE_LOCATION
//
//            ACCESS_COARSE_LOCATION
//
//    MICROPHONE（麦克风） 
//
//
//    RECORD_AUDIO
//
//    PHONE（手机） 
//
//
//    READ_PHONE_STATE
//
//            CALL_PHONE
//
//    READ_CALL_LOG
//
//            WRITE_CALL_LOG
//
//    ADD_VOICEMAIL
//
//            USE_SIP
//
//    PROCESS_OUTGOING_CALLS
//
//    SENSORS（传感器） 
//
//
//    BODY_SENSORS
//
//    SMS（短信） 
//
//
//    SEND_SMS
//
//            RECEIVE_SMS
//
//    READ_SMS
//
//            RECEIVE_WAP_PUSH
//
//    RECEIVE_MMS
//
//    STORAGE（存储卡） 
//
//
//    READ_EXTERNAL_STORAGE
//
//            WRITE_EXTERNAL_STORAGE
//
//    作者：hacxiu
//    链接：https://ld246.com/article/1478487147474
//    来源：链滴
//    协议：CC BY-SA 4.0 https://creativecommons.org/licenses/by-sa/4.0/
}
