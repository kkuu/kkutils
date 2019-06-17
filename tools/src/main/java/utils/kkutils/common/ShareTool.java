package utils.kkutils.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
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

    // 調用系統方法分享文件
    public static void shareFile(Activity activity,String title, File file) {
        if (null != file && file.exists()) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            share.setType(getMimeType(file.getAbsolutePath()));//此处可发送多种文件
            share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(Intent.createChooser(share, title));
        } else {
            CommonTool.showToast("File Not Exist");
        }
    }

    // 根据文件后缀名获得对应的MIME类型。
    private static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "*/*";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }

}
