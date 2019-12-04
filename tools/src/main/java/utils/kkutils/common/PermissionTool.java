package utils.kkutils.common;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.blankj.utilcode.*;
import com.blankj.utilcode.util.PermissionUtils;

import androidx.core.app.ActivityCompat;
import utils.kkutils.AppTool;
import utils.kkutils.ui.dialog.DialogSimple;
import utils.kkutils.ui.dialog.DialogTool;

/**
 *权限工具
 */
public class PermissionTool {
    /***
     * 需要先在 xml 里面申明了这里才有效果
     * @param title
     * @param permission
     * @return
     */
    public static boolean checkPermission( String title,final String... permission){
        String test=Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if(!PermissionUtils.isGranted(permission)){
            try {
                if(StringTool.notEmpty(title)){
                    DialogTool.initNormalQueDingDialog("提示", title, "去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(AppTool.currActivity, permission,1);
                        }
                    },"取消").show();
                }else {
                    ActivityCompat.requestPermissions(AppTool.currActivity,permission,1);
                }
            }catch (Exception e){
                LogTool.ex(e);
            }
            return false;
        }
       return true;
    }
}
