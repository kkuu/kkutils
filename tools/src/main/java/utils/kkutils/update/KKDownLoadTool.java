package utils.kkutils.update;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;

/**
 * Created by kk on 2016/5/18.
 */
public class KKDownLoadTool {
    static HashMap<Long ,Callback.Cancelable> downLoadMap=new HashMap<>();
    static volatile  long downLoadId=0;
    public static synchronized long downLoad(final String url,String localPath ,final DownLoadProgressListener downLoadProgressListener) {
        RequestParams requestParams=new RequestParams(url);
        if(StringTool.notEmpty(localPath))requestParams.setSaveFilePath(localPath);
        requestParams.setCancelFast(true);
        requestParams.setConnectTimeout(1000 * 60);//超时
        requestParams.setReadTimeout(1000*99999999);//文件上传不限制，这个是文件上传的
        Callback.Cancelable cancelable=x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                downLoadProgressListener.onProgress(1,url,"",total,current,false);
                LogTool.s("下载中"+total+"   "+current);
            }

            @Override
            public void onSuccess(File result) {
                downLoadProgressListener.onProgress(1,url,result.getAbsolutePath(),0,0,true);
                LogTool.s("下载完成"+result.getAbsolutePath());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        long id=downLoadId++;
        downLoadMap.put(id,cancelable);
        return id;
    }

    /***
     * 删除一个下载
     *
     * @param id
     */
    public static void removeDownLoad(long id) {
        try {
            Callback.Cancelable cancelable=downLoadMap.get(id);
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
