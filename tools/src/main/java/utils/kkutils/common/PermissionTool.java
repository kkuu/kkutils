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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.blankj.utilcode.*;
import com.blankj.utilcode.util.PermissionUtils;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import utils.kkutils.AppTool;
import utils.kkutils.ui.dialog.DialogSimple;
import utils.kkutils.ui.dialog.DialogTool;

/**
 * 工具
 */
public class PermissionTool {


    public static void test() {
        PermissionTool.checkPermissionMust(new PermissionTool.PermissionListener() {
            @Override
            public void onGranted(List<String> granted) {
                CommonTool.showToast("允许了");
            }

            @Override
            public boolean onDenied(List<String> denied) {
                CommonTool.showToast("拒绝了");
                return false;
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE);

    }

    public interface PermissionListener {
        public void onGranted(List<String> granted);

        public boolean onDenied(List<String> denied);
    }


    public static boolean checkPermissionMust(PermissionListener permissionListener, final String... permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Activity currActivity = AppTool.currActivity;
            PermissionUtils.permission(permission)
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> granted) {
                            LogTool.s("权限允许了：" + granted);
                            if (permissionListener != null) permissionListener.onGranted(granted);
                        }

                        @Override
                        public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                            if (permissionListener != null) {
                                if (permissionListener.onDenied(denied)) return;
                            }
                            if (denied != null && !denied.isEmpty()) {

                                AppTool.currActivity = currActivity;

                                Set<String> stringSet = new TreeSet<>();
                                for (String s : denied) {
                                    LogTool.s("权限被拒绝了"+s);
                                    stringSet.add(PermissionStr.getPermissionName(s) + "、");
                                }
                                StringBuffer sb = new StringBuffer();
                                for (String s : stringSet) {
                                    sb.append(s);
                                }
                                String permissionStr = sb.toString().substring(0, sb.length() - 1);

                                DialogTool.initNormalQueDingDialog("提示", "请允许" + permissionStr + "权限","去设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        PermissionUtils.launchAppDetailsSettings();
                                    }
                                }, "取消").show();
                            }
                        }
                    })
                    .request();
        }
        ;
        return PermissionUtils.isGranted(permission);
    }




    public static class PermissionStr {

        static  HashMap<String,String> permissionMap;
        public static String getPermissionName(String permission) {
            if(permissionMap==null){
                permissionMap=new HashMap<>();
                try {
                    String[] namesArr = names.split("\n");
                    String[] valuesArr =values.split("\n") ;
                    for (int i = 0; i < namesArr.length; i++) {
                        permissionMap.put(namesArr[i],valuesArr[i]);
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }
            }
            return ""+permissionMap.get(permission);
        }

        static String names = "android.permission.ACCESS_CHECKIN_PROPERTIES\n" +
                "android.permission.ACCESS_COARSE_LOCATION\n" +
                "android.permission.ACCESS_FINE_LOCATION\n" +
                "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS\n" +
                "android.permission.ACCESS_MOCK_LOCATION\n" +
                "android.permission.ACCESS_NETWORK_STATE\n" +
                "android.permission.ACCESS_SURFACE_FLINGER\n" +
                "android.permission.ACCESS_WIFI_STATE\n" +
                "android.permission.ACCOUNT_MANAGER\n" +
                "android.permission.AUTHENTICATE_ACCOUNTS\n" +
                "android.permission.BATTERY_STATS\n" +
                "android.permission.BIND_APPWIDGET\n" +
                "android.permission.BIND_DEVICE_ADMIN\n" +
                "android.permission.BIND_INPUT_METHOD\n" +
                "android.permission.BIND_REMOTEVIEWS\n" +
                "android.permission.BIND_WALLPAPER\n" +
                "android.permission.BLUETOOTH\n" +
                "android.permission.BLUETOOTH_ADMIN\n" +
                "android.permission.BRICK\n" +
                "android.permission.BROADCAST_PACKAGE_REMOVED\n" +
                "android.permission.BROADCAST_SMS\n" +
                "android.permission.BROADCAST_STICKY\n" +
                "android.permission.BROADCAST_WAP_PUSH\n" +
                "android.permission.CALL_PHONE\n" +
                "android.permission.CALL_PRIVILEGED\n" +
                "android.permission.CAMERA\n" +
                "android.permission.CHANGE_COMPONENT_ENABLED_STATE\n" +
                "android.permission.CHANGE_CONFIGURATION\n" +
                "android.permission.CHANGE_NETWORK_STATE\n" +
                "android.permission.CHANGE_WIFI_MULTICAST_STATE\n" +
                "android.permission.CHANGE_WIFI_STATE\n" +
                "android.permission.CLEAR_APP_CACHE\n" +
                "android.permission.CLEAR_APP_USER_DATA\n" +
                "android.permission.CWJ_GROUP\n" +
                "android.permission.CELL_PHONE_MASTER_EX\n" +
                "android.permission.CONTROL_LOCATION_UPDATES\n" +
                "android.permission.DELETE_CACHE_FILES\n" +
                "android.permission.DELETE_PACKAGES\n" +
                "android.permission.DEVICE_POWER\n" +
                "android.permission.DIAGNOSTIC\n" +
                "android.permission.DISABLE_KEYGUARD\n" +
                "android.permission.DUMP\n" +
                "android.permission.EXPAND_STATUS_BAR\n" +
                "android.permission.FACTORY_TEST\n" +
                "android.permission.FLASHLIGHT\n" +
                "android.permission.FORCE_BACK\n" +
                "android.permission.GET_ACCOUNTS\n" +
                "android.permission.GET_PACKAGE_SIZE\n" +
                "android.permission.GET_TASKS\n" +
                "android.permission.GLOBAL_SEARCH\n" +
                "android.permission.HARDWARE_TEST\n" +
                "android.permission.INJECT_EVENTS\n" +
                "android.permission.INSTALL_LOCATION_PROVIDER\n" +
                "android.permission.INSTALL_PACKAGES\n" +
                "android.permission.INTERNAL_SYSTEM_WINDOW\n" +
                "android.permission.INTERNET\n" +
                "android.permission.KILL_BACKGROUND_PROCESSES\n" +
                "android.permission.MANAGE_ACCOUNTS\n" +
                "android.permission.MANAGE_APP_TOKENS\n" +
                "android.permission.MTWEAK_USER\n" +
                "android.permission.MTWEAK_FORUM\n" +
                "android.permission.MASTER_CLEAR\n" +
                "android.permission.MODIFY_AUDIO_SETTINGS\n" +
                "android.permission.MODIFY_PHONE_STATE\n" +
                "android.permission.MOUNT_FORMAT_FILESYSTEMS\n" +
                "android.permission.MOUNT_UNMOUNT_FILESYSTEMS\n" +
                "android.permission.NFC\n" +
                "android.permission.PERSISTENT_ACTIVITY\n" +
                "android.permission.PROCESS_OUTGOING_CALLS\n" +
                "android.permission.READ_CALENDAR\n" +
                "android.permission.READ_CONTACTS\n" +
                "android.permission.READ_FRAME_BUFFER\n" +
                "com.android.browser.permission.READ_HISTORY_BOOKMARKS\n" +
                "android.permission.READ_INPUT_STATE\n" +
                "android.permission.READ_LOGS\n" +
                "android.permission.READ_PHONE_STATE\n" +
                "android.permission.READ_SMS\n" +
                "android.permission.READ_SYNC_SETTINGS\n" +
                "android.permission.READ_SYNC_STATS\n" +
                "android.permission.REBOOT\n" +
                "android.permission.RECEIVE_BOOT_COMPLETED\n" +
                "android.permission.RECEIVE_MMS\n" +
                "android.permission.RECEIVE_SMS\n" +
                "android.permission.RECEIVE_WAP_PUSH \n" +
                "android.permission.RECORD_AUDIO\n" +
                "android.permission.REORDER_TASKS\n" +
                "android.permission.RESTART_PACKAGES\n" +
                "android.permission.SEND_SMS\n" +
                "android.permission.SET_ACTIVITY_WATCHER\n" +
                "com.android.alarm.permission.SET_ALARM\n" +
                "android.permission.SET_ALWAYS_FINISH\n" +
                "android.permission.SET_ANIMATION_SCALE\n" +
                "android.permission.SET_DEBUG_APP\n" +
                "android.permission.SET_ORIENTATION\n" +
                "android.permission.SET_PREFERRED_APPLICATIONS\n" +
                "android.permission.SET_PROCESS_LIMIT\n" +
                "android.permission.SET_TIME\n" +
                "android.permission.SET_TIME_ZONE\n" +
                "android.permission.SET_WALLPAPER\n" +
                "android.permission.SET_WALLPAPER_HINTS\n" +
                "android.permission.SIGNAL_PERSISTENT_PROCESSES\n" +
                "android.permission.STATUS_BAR\n" +
                "android.permission.SUBSCRIBED_FEEDS_READ\n" +
                "android.permission.SUBSCRIBED_FEEDS_WRITE\n" +
                "android.permission.SYSTEM_ALERT_WINDOW\n" +
                "android.permission.UPDATE_DEVICE_STATS\n" +
                "android.permission.USE_CREDENTIALS\n" +
                "android.permission.USE_SIP\n" +
                "android.permission.VIBRATE\n" +
                "android.permission.WAKE_LOCK\n" +
                "android.permission.WRITE_APN_SETTINGS\n" +
                "android.permission.WRITE_CALENDAR\n" +
                "android.permission.WRITE_CONTACTS\n" +
                "android.permission.WRITE_EXTERNAL_STORAGE\n" +
                "android.permission.WRITE_GSERVICES\n" +
                "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS\n" +
                "android.permission.WRITE_SECURE_SETTINGS\n" +
                "android.permission.WRITE_SETTINGS\n" +
                "android.permission.WRITE_SMS\n" +
                "android.permission.WRITE_SYNC_SETTINGS";
        static String values="问登记属性\n"+
                "获取粗略位置\n"+
                "获取精确位置\n"+
                "访问定位额外命令\n"+
                "获取模拟定位信息\n"+
                "获取网络状态\n"+
                "访问SurfaceFlinger\n"+
                "获取WiFi状态\n"+
                "账户管理\n"+
                "验证账户\n"+
                "电量统计\n"+
                "绑定小插件\n"+
                "绑定设备管理\n"+
                "绑定输入法\n"+
                "绑定RemoteView\n"+
                "绑定壁纸\n"+
                "使用蓝牙\n"+
                "蓝牙管理\n"+
                "变成砖头\n"+
                "应用删除时广播\n"+
                "收到短信时广播\n"+
                "连续广播\n"+
                "WAPPUSH广播\n"+
                "拨打电话\n"+
                "通话\n"+
                "拍照\n"+
                "改变组件状态\n"+
                "改变配置\n"+
                "改变网络状态\n"+
                "改变WiFi多播状态\n"+
                "改变WiFi状态\n"+
                "清除应用缓存\n"+
                "清除用户数据\n"+
                "底层访问\n"+
                "手机优化大师扩展\n"+
                "控制定位更新\n"+
                "删除缓存文件\n"+
                "删除应用\n"+
                "电源管理\n"+
                "应用诊断\n"+
                "禁用键盘锁\n"+
                "转存系统信息\n"+
                "状态栏控制\n"+
                "工厂测试模式\n"+
                "使用闪光灯\n"+
                "强制后退\n"+
                "访问账户Gmail列表\n"+
                "获取应用大小\n"+
                "获取任务信息\n"+
                "允许全局搜索\n"+
                "硬件测试\n"+
                "注射事件\n"+
                "安装定位提供\n"+
                "安装应用程序\n"+
                "内部系统窗口\n"+
                "访问网络\n"+
                "结束后台进程\n"+
                "管理账户\n"+
                "管理程序引用\n"+
                "高级\n"+
                "社区\n"+
                "软格式化\n"+
                "修改声音设置\n"+
                "修改电话状态\n"+
                "格式化文件系统\n"+
                "挂载文件系统\n"+
                "允许NFC通讯\n"+
                "永久Activity\n"+
                "处理拨出电话\n"+
                "读取日程提醒\n"+
                "读取联系人\n"+
                "屏幕截图\n"+
                "读取收藏夹和历史记录\n"+
                "读取输入状态\n"+
                "读取系统日志\n"+
                "读取电话状态\n"+
                "读取短信内容\n"+
                "读取同步设置\n"+
                "读取同步状态\n"+
                "重启设备\n"+
                "开机自动允许\n"+
                "接收彩信\n"+
                "接收短信\n"+
                "接收WapPush\n"+
                "录音\n"+
                "排序系统任务\n"+
                "结束系统任务\n"+
                "发送短信\n"+
                "设置Activity观察\n"+
                "设置闹铃提醒\n"+
                "设置总是退出\n"+
                "设置动画缩放\n"+
                "设置调试程序\n"+
                "设置屏幕方向\n"+
                "设置应用参数\n"+
                "设置进程限制\n"+
                "设置系统时间\n"+
                "设置系统时区\n"+
                "设置桌面壁纸\n"+
                "设置壁纸建议\n"+
                "发送永久进程信号\n"+
                "状态栏控制\n"+
                "访问订阅内容\n"+
                "写入订阅内容\n"+
                "显示系统窗口\n"+
                "更新设备状态\n"+
                "使用证书\n"+
                "使用SIP视频\n"+
                "使用振动\n"+
                "唤醒锁定\n"+
                "写入GPRS接入点设置\n"+
                "写入日程提醒\n"+
                "写入联系人\n"+
                "写入外部存储\n"+
                "写入Google地图数据\n"+
                "写入收藏夹和历史记录\n"+
                "读写系统敏感设置\n"+
                "读写系统设置\n"+
                "编写短信\n"+
                "写入在线同步设置";
    }
}
