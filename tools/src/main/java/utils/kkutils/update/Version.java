package utils.kkutils.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import java.io.File;

import utils.kkutils.AppTool;
import utils.kkutils.BuildConfig;
import utils.kkutils.R;
import utils.kkutils.common.FileTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;

/**
 * Created by kk on 2016/5/18.
 * 用于更新的
 */
public class Version {
    public int versionCode;//版本号
    public String versionName = "";//版本名称
    public String versionSize = "";//版本大小
    public String updateUrl = "";//下载地址
    public String updateDesc = "";//版本描述
    public String updateTime = "";//更新时间
    public String isForce = "";//是否强制更新 0-否 1-是


    /***
     * 检测更新
     *
     * @param newVersion
     */
    public static boolean checkUpDate(final Context context, final Version newVersion) {
        int currVersionCode = newVersion.getCurrVersionCode(context);
        LogTool.s("检查更新： 当前版本" + currVersionCode + "  服务端版本" + newVersion.versionCode);
        boolean hasNew = currVersionCode < newVersion.versionCode;
        newVersion.updateDesc = "" + newVersion.updateDesc.replace("\\n", "\n");
        if (hasNew) {//有新的版本
            String showText = context.getString(R.string.banben_mingcheng)
                    + newVersion.versionName
                    + "\n"+context.getString(R.string.gengxin_shijian)
                    + newVersion.updateTime
                    + "\n"+context.getString(R.string.gengxin_daxiao)
                    + newVersion.versionSize
                    + "\n"+context.getString(R.string.gengxin_neirong)
                    + newVersion.updateDesc;


            final AlertDialog dialogShowMsg = new AlertDialog.Builder(context)
                    .setTitle(R.string.ninyou_xinde_banben)
                    .setMessage(showText)
                    .create();

            final boolean isForce = "1".equals(newVersion.isForce);
            if (isForce) {//强制更新
                dialogShowMsg.setCancelable(false);
                dialogShowMsg.setButton(ProgressDialog.BUTTON_NEGATIVE, context.getString(R.string.tuichu_chengxu), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppTool.exitApp();
                    }
                });
            } else {
                dialogShowMsg.setCancelable(true);
                dialogShowMsg.setButton(ProgressDialog.BUTTON_NEGATIVE, context.getString(R.string.quxiao), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogShowMsg.dismiss();
                    }
                });
            }
            dialogShowMsg.setButton(ProgressDialog.BUTTON_POSITIVE, context.getString(R.string.liji_gengxin), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   newVersion. showDownLoadProgressDialog(context, newVersion, isForce);
                }
            });

            dialogShowMsg.show();
        }
        return hasNew;
    }

    /**
     * 显示下载进度框
     *
     * @param context
     * @param newVersion
     */
    public  void showDownLoadProgressDialog(final Context context, Version newVersion, final boolean isForce) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(context.getString(R.string.zhengzai_nuli_xiazai));
        String path= FileTool.getCacheDir("apk")+"tem.apk";

        final long id = KKDownLoadTool.downLoad( newVersion.updateUrl,path, new KKDownLoadTool.DownLoadProgressListener() {
            @Override
            public void onProgress(long id, String url, final String localPath, long allSize, long currDownLoadSize, boolean isComplete) {
                if (isComplete && allSize == currDownLoadSize&&localPath!=null) {//完成过后就是安装
                    LogTool.s("installApk pre " + localPath + "  " + new File(localPath).length());
                    if(StringTool.isEmpty(localPath))return;
                    AppTool.uiHandler.postDelayed(new Runnable() {//延迟一秒钟，  担心可能没下载完毕
                        @Override
                        public void run() {
                            installApk(context, localPath);
                            if (!isForce) progressDialog.dismiss();
                        }
                    }, 1000);
                } else {//下载进度
                    progressDialog.setProgress((int) ((currDownLoadSize * 100) / allSize));
                }
            }
        });
        if (!isForce) {
            progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, context.getString(R.string.quxiao), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    KKDownLoadTool.removeDownLoad(id);
                    progressDialog.dismiss();
                }
            });
            progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, context.getString(R.string.houtai_xiazai), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progressDialog.dismiss();
                }
            });
        }
        progressDialog.show();
    }


    /**
     * 获取当前版本号
     *
     * @param context
     * @return
     */
    public  int getCurrVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 安装Apk
     *
     * @param context
     * @param path
     */
    protected   void installApk(final Context context, final String path) {


        if(true)autoInstallApk(context,new File(path));
//        LogTool.s("installApk ing " + path + "  " + new File(path).length());
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
//        context.startActivity(intent);


    }

    protected  void autoInstallApk(Context context,File file) {
        LogTool.s("installApk ing " + file.getAbsolutePath() + "  " + file.length());

        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            // 声明需要的临时的权限
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 第二个参数，即第一步中配置的authorities
            Uri contentUri = FileProvider.getUriForFile(AppTool.getApplication(), BuildConfig.APPLICATION_ID + ".fileprovider", file);//注意这里，我故意写了com.http.www.smarthttpdemo.BuildConfig，因为这里出错了我郁闷了好久，因为这里容易导错包，如果你是多module开发，一定要使用主Module（即：app）的那个BuildConfig（这里说明一下，我上面的清单文件是主Module的，所以这里也要导包导入主module的，我觉得写到被依赖module也是可以的，但要保持一致，这个我没试过）
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}
