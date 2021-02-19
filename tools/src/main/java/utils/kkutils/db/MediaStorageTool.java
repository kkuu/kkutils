package utils.kkutils.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UriTool;


/***
 * android 11 提出了 分区存储， 存放外部文件不可以，所以可以用这个 来放到共享目录存放一些信息
 * MediaStore.Files: 共享的文件,包括多媒体和非多媒体信息
 *
 * MediaStore.Audio: 存放音频信息
 *
 * MediaStore.Image: 存放图片信息
 *
 * MediaStore.Vedio: 存放视频信息
 *
 *
 * 上面几个 可以用 file 方式直接读写
 */
public class MediaStorageTool {
    static final Uri urlExternal = MediaStore.Files.getContentUri("external");
    static final String RELATIVE_PATH="relative_path";//MediaStore.Files.FileColumns.RELATIVE_PATH
    static String fileParent="Documents";//sd 卡下的document


    public static Context context= CommonTool.getApp();


    public static void StringSave(String key,String content){
        Uri uri = MediaStorageTool.fileNew( "", key);
        MediaStorageTool.fileWrite(uri,content);
    }
    public static String StringLoad(String key){
        Uri uri = MediaStorageTool.fileNew( "", key);
        String result = MediaStorageTool.fileRead( uri);
        return result;
    }



    public static Uri fileNew(String path,String name){



        MediaFile mediaFile = fileFind( path, name);
        if(mediaFile.uri==null){//没有文件 新插入一个
            ContentValues contentValues=new ContentValues();
            contentValues.put(RELATIVE_PATH,mediaFile.path);
            contentValues.put(MediaStore.Files.FileColumns.DISPLAY_NAME, mediaFile.name);
            contentValues.put(MediaStore.Files.FileColumns.MIME_TYPE, "application/java-archive");
            context.getContentResolver().insert(urlExternal, contentValues);
            mediaFile = fileFind( path, name);
        }
        return mediaFile.uri;
    }
    public static MediaFile fileFind(String path,String name){
        if(TextUtils.isEmpty(path)) {
            path="files";
        }
        if(!path.endsWith("/")){
            path=path+"/";
        }
        String relativePath=fileParent+"/System/"+context.getPackageName()+"/"+path;
        String displayName=name;

        MediaFile mediaFile=new MediaFile();
        mediaFile.path=relativePath;
        mediaFile.name=displayName;


        try {//默认用原生的file， 兼容低版本android
            String pathTem= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+relativePath;

            File file=new File(pathTem,displayName+".jar");
            if(!file.exists()){
                File fileTem=new File(pathTem);
                fileTem.mkdirs();
                file.createNewFile();
                LogTool.s("原始方式创建文件："+file.getAbsolutePath());
            }

            if(file.exists()){
                Uri uri= UriTool.getUriWithFileProvider(file);
                mediaFile.uri=uri;
                return mediaFile;
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        List<MediaFile> list = MediaStorageTool.getFilesByPath(relativePath, displayName+".jar");
        if(list.size()==1){
            return list.get(0);
        }

        return mediaFile;
    }
    public static boolean fileWrite(Uri uri,String content){
        try {
            if(uri==null||content==null)return false;
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static String fileRead(Uri uri){
        String result="";
        BufferedReader br=null;
        try {
            if(uri==null)return result;

            InputStream inputStream = context.getContentResolver().openInputStream(uri);
             br=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer=new StringBuffer();
            String line="";
            while ((line=br.readLine())!=null){
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
            result=stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 获取所有文件
     **/
    public static List<MediaFile> getFilesAll(Context context) {

        return getFilesByCursor( context.getContentResolver().query(MediaStore.Files.getContentUri("external"), null, null, null, null));
    }
    public static List<MediaFile> getFilesByPath(String relativePath, String displayName) {
        try {
            String sql=(RELATIVE_PATH+"=? and "+MediaStore.Files.FileColumns.DISPLAY_NAME+"=?");
            ContentResolver mContentResolver = context.getContentResolver();
            Cursor external = mContentResolver.query(MediaStore.Files.getContentUri("external"), null, sql, new String[]{relativePath, displayName}, null);
           return getFilesByCursor(external);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    /**
     * 获取所有文件 使用 Cursor
     **/
    public static List<MediaFile> getFilesByCursor( Cursor c) {
        List<MediaFile> list = new ArrayList<>();
        // 扫描files文件库
        try {
            int columnIndexOrThrow_ID = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
            int columnIndexOrThrow_MIME_TYPE = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);
            int columnIndexOrThrow_DATA = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            int columnIndexOrThrow_SIZE = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
            // 更改时间
            int columnIndexOrThrow_DATE_MODIFIED = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED);

            int tempId = 0;
            while (c.moveToNext()) {
                String path = c.getString(columnIndexOrThrow_DATA);
                String minType = c.getString(columnIndexOrThrow_MIME_TYPE);
                Log.v("kk","path:" + path);

                int position_do = path.lastIndexOf(".");
                if (position_do == -1) {
                    continue;
                }
                int position_x = path.lastIndexOf(File.separator);
                if (position_x == -1) {
                    continue;
                }
                String displayName = path.substring(position_x + 1, path.length());
                long size = c.getLong(columnIndexOrThrow_SIZE);
                long modified_date = c.getLong(columnIndexOrThrow_DATE_MODIFIED);
                File file = new File(path);
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified()));

                MediaFile mediaFile = new MediaFile();
                mediaFile.name = displayName;
                mediaFile.path = path;
                mediaFile.size = size;
                mediaFile.id = c.getLong(columnIndexOrThrow_ID);//tempId++) + "";
                mediaFile.time = time;
                mediaFile.uri=MediaStore.Files.getContentUri("external",mediaFile.id);

                list.add(mediaFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }




    public static class MediaFile {
        public String name;
        public String path;
        public long size;
        public long id;
        public String time;
        public Uri uri;

        @Override
        public String toString() {
            return "MediaFile{" +
                    "name='" + name + '\'' +
                    ", path='" + path + '\'' +
                    ", size=" + size +
                    ", id='" + id + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

}
