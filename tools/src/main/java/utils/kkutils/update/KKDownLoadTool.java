package utils.kkutils.update;

import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.http.implement.HttpToolOkHttp;

/**
 * Created by kk on 2016/5/18.
 */
public class KKDownLoadTool {
    static HashMap<Long ,Call> downLoadMap=new HashMap<>();
    static volatile  long downLoadId=0;
    public static synchronized long downLoad(final String url,String localPath ,final DownLoadProgressListener listener) {
        OkHttpClient client = new HttpToolOkHttp().getDefaultBuilder().build();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        File file=new File(localPath);
        long id=downLoadId++;
        downLoadMap.put(id,call);

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogTool.ex(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    LogTool.ex(new Throwable("返回错误"+response.code()+"   "+response.message()));
                }else {
                    FileOutputStream fileOutputStream = null;
                    InputStream inputStream = null;
                    try {
                        long total = response.body().contentLength();
                        long sum = 0;
                        inputStream = response.body().byteStream();
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[1024 * 1024];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, len);
                            if (listener != null) {
                                sum += len;
                                int progress = (int) (sum * 1.0f / total * 100);
                                LogTool.s("下载"+total +"     "+sum);
                                // 下载中
                                listener.onProgress(id,url,localPath,total,sum,false);
                            }
                        }
                        fileOutputStream.flush();

                        listener.onProgress(id,url,localPath,total,sum,sum==total);


                    } catch (Exception e) {
                        LogTool.ex(e);
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    }
                }



            }
        });
        return id;
    }

    /***
     * 删除一个下载
     *
     * @param id
     */
    public static void removeDownLoad(long id) {
        try {
            Call cancelable=downLoadMap.get(id);
            if(cancelable!=null)cancelable.cancel();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    public static abstract class DownLoadProgressListener {
        public long id;
        public abstract void onProgress(long id, String url, String localPath, long allSize, long currDownLoadSize, boolean isComplete);
    }

}
