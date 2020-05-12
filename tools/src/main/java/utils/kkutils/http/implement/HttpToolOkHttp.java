package utils.kkutils.http.implement;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dns;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import utils.kkutils.AppTool;
import utils.kkutils.JsonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.http.InterfaceHttpTool;
import utils.kkutils.http.SSLTool;

/**
 * Created by coder on 15/12/25.
 * 网络请求接口xutils的实现
 */
public class HttpToolOkHttp implements InterfaceHttpTool {

    String[] crts;
    OkHttpClient client = new OkHttpClient();

    /**
     * 初始化
     *
     * @param context
     */
    @Override
    public void init(Context context) {
        init(context, new String[0]);
    }

    @Override
    public void init(Context context, String... crts) {
        this.crts = crts;
        client = getDefaultBuilder().build();
    }
    public static class ReTryInterceptor implements Interceptor {
        public int maxRetry=10;//最大重试次数
        @NotNull
        @Override
        public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
            int retryNum = 1;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

            Request request = chain.request();
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                LogTool.s(response.code()+"  "+response.message()+"重试：请求："+retryNum+"   "+chain.request().url());
                try {
                    Thread.sleep(100*retryNum);
                    response.close();
                }catch (Exception e){
                    LogTool.ex(e);
                }
                response = chain.proceed(request);
            }
            return response;

        }
    }
    public OkHttpClient.Builder getDefaultBuilder(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(initCookieJar())
                .connectTimeout(AppTool.isDebug?5:60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new ReTryInterceptor())//重试
                .readTimeout(10, TimeUnit.DAYS)
                .writeTimeout(10, TimeUnit.DAYS)
                // .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.110.200", 8888)))
                .dns(new Dns() {//dns 优先ipv4,否则android 可能导致慢
                    @NotNull
                    @Override
                    public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {
                        if (hostname == null) {
                            throw new UnknownHostException("hostname == null");
                        } else {
                            try {
                                List<InetAddress> mInetAddressesList = new ArrayList<>();
                                InetAddress[] mInetAddresses = InetAddress.getAllByName(hostname);
                                for (InetAddress address : mInetAddresses) {
                                    if (address instanceof Inet4Address) {
                                        mInetAddressesList.add(0, address);
                                    } else {
                                        mInetAddressesList.add(address);
                                    }
                                }
                                return mInetAddressesList;
                            } catch (NullPointerException var4) {
                                UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour");
                                unknownHostException.initCause(var4);
                                throw unknownHostException;
                            }
                        }
                    }
                })
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                ;


//        builder.sslSocketFactory(SSLTool.initSSLFactoryByCrt(crts),null);
        return builder;
    }

    protected CookieJar initCookieJar() {
        CookieJar cookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                ArrayList<Cookie> cookieArrayList = new ArrayList<>();
                List<Cookie> oldCookie = cookieStore.get(url.host());
                if (oldCookie != null) {
                    cookieArrayList.addAll(oldCookie);
                }
                cookieArrayList.addAll(cookies);

                cookieStore.put(url.host(), cookieArrayList);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
        return cookieJar;
    }

    /***
     * 同步的, 直接返回请求数据
     *
     * @param request
     * @param clzz
     * @param <T>
     * @return
     */
    @Override
    public <T extends Serializable> T request(HttpRequest request, Class<T> clzz) {
        try {
            request.beginRequest();
            Request.Builder builder = convertHttpRequestToRequestParams(request);
            String temStr = client.newCall(builder.build()).execute().body().string();
            T result = JsonTool.toJava(temStr, clzz);
            request.setResponseDataStr(temStr, clzz);
            request.endRequest();
            return result;
        } catch (Throwable t) {
            LogTool.ex(t);
        }

        return null;
    }

    /***
     * 异步请求,返回的数据再callBack 中
     *
     * @param request
     * @param clzz
     * @param callBack
     * @param <T>
     */
    @Override
    public <T extends Serializable> void request(final HttpRequest request, final Class<T> clzz, final HttpUiCallBack callBack) {
            try {
                request.beginRequest();
                Request.Builder builder = convertHttpRequestToRequestParams(request);
                client.newCall(builder.build()).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        request.endRequest();
                        if (callBack != null) {
                            callBack.notifyState(HttpUiCallBack.State.stateOnNetLocalError, request, clzz);
                            LogTool.ex(e);
                        }
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        request.endRequest();
                        if (callBack != null) {
                            request.setResponseDataStr(response.body().string(), clzz);
                            callBack.notifyState(HttpUiCallBack.State.stateOnSuccess, request, clzz);
                        }
                        if(!response.isSuccessful()){
                            LogTool.ex(new Throwable("返回错误"+response.code()+"   "+response.message()));
                        }
                    }
                });

            } catch (Throwable t) {
                LogTool.ex(t);
            }


    }

    protected  RequestBody getBody(Map<String, Object> map) {
        if (map == null) return new FormBody.Builder().build();
        boolean hasFile = false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue() instanceof File) {//有文件
                hasFile = true;
                break;
            }
        }
        if (!hasFile) {
            FormBody.Builder builder1 = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder1.add(entry.getKey(), "" + entry.getValue());
            }
            return builder1.build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File value = (File) entry.getValue();
                    String TYPE = "application/octet-stream";
                    RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), value);
                    builder.addFormDataPart(entry.getKey(), value.getName(), fileBody);
                } else {
                    builder.addFormDataPart(entry.getKey(), "" + entry.getValue());
                }
            }
            return builder.build();
        }

    }

    protected synchronized Request.Builder convertHttpRequestToRequestParams(HttpRequest httpRequest) {
        //第二步构建Request对象
        Request.Builder builder = new Request.Builder()
                .url(httpRequest.getUrlStr());

        /***
         * 放入Header
         */
        Iterator<Map.Entry<String, String>> iteratorHeader = httpRequest.getHeaderMap().entrySet().iterator();
        while (iteratorHeader.hasNext()) {
            Map.Entry<String, String> entry = iteratorHeader.next();
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        //放入参数
        if (HttpRequest.RequestMethod.GET.equals(httpRequest.getRequestMethod())) {
            builder.url(httpRequest.getUrlRequestGet());
            builder.get();
        } else {
            builder.post(getBody(httpRequest.getQueryMap()));
        }

        return builder;
    }
}
