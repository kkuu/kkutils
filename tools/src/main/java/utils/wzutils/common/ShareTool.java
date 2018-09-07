package utils.wzutils.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

public class ShareTool {
    public static void shareText(Activity activity, String title, String text){
        try {
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, text);
            activity.startActivity(Intent.createChooser(textIntent, title));
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
    public static void shareImageUri(Activity activity,String title,Uri uri){
        try {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            activity.startActivity(Intent.createChooser(shareIntent, title));
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    public static void shareImageBitmap(Activity activity,String title,String imgName,Bitmap bitmap){
        try {
            File file = FileTool.initCacheFile("" , imgName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            shareImageUri(activity,title, Uri.fromFile(file));
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    /***
     * 分享一个控件截图
     * @param activity
     * @param title
     * @param imgName
     * @param view
     */
    public static void shareView(Activity activity,String title,String imgName,View view){
        try {
            view.setDrawingCacheEnabled(true);
            shareImageBitmap(activity,title,imgName,view.getDrawingCache());
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
}
