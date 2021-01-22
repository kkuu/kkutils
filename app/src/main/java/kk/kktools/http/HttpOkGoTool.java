package kk.kktools.http;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import utils.kkutils.HttpTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.FileTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.update.Version;

public class HttpOkGoTool {
    public static<T> void url(String url){

        OkGoRequest.url("")
                .get()
                .tag("")
                .execute(new KKCallBack() {
                    @Override
                    public void onSuccess(Response response) {

                    }
                });

    }

    /**
     * get请求获取数据
     * @param url
     */
    public static<T> Request get(String url, KKCallBack<T> callback){
        return OkGo.<T>get(url)                            // 请求方式和请求url
                .tag(url)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST); // 缓存模式
    }
    /**
     * post请求获取数据
     * @param url
     */
    public static<T> void post(String url, KKCallBack<T> callback){
        OkGo.<T>post(url)                            // 请求方式和请求url
                .tag(url)                      // 请求的 tag, 主要用于取消对应的请求
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST) // 缓存模式
                .execute(callback);

    }

    /**
     * 下载文件
     * @param url 下载地址
     * @param destFileDir 保存文件路径
     * @param destFileName 保存文件名
     */
    public static void downLoad(String url, String destFileDir, String destFileName,KKCallBack<File> callback){
        OkGo.<File>get(url)//
                .tag(url)//
                .execute(new FileCallback(destFileDir, destFileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if(callback!=null)callback.onSuccess(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        LogTool.s(progress.currentSize+"  "+progress.totalSize);
                        if(callback!=null)callback.downloadProgress(progress);
                    }
                });
    }
    /**
     * 多文件上传
     * @param url
     * @param keyName
     * @param files 文件集合
     */
    public void uploadFiles(String url, String keyName, List<File> files){
        OkGo.<String>post(url)//
                .tag(this)//
                //.isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                //.params("param1", "paramValue1")        // 这里可以上传参数
                //.params("file1", new File("filepath1"))   // 可以添加文件上传
                //.params("file2", new File("filepath2"))     // 支持多文件同时添加上传
                //.addFileParams(keyName, files)    // 这里支持一个key传多个文件
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                    }
                });
    }

}
