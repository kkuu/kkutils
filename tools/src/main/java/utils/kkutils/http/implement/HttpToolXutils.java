//package utils.kkutils.http.implement;
//
//import android.app.Application;
//import android.content.Context;
//
//import org.xutils.common.Callback;
//import org.xutils.http.HttpMethod;
//import org.xutils.http.RequestParams;
//import org.xutils.x;
//
//import java.io.File;
//import java.io.Serializable;
//import java.util.Iterator;
//import java.util.Map;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLSession;
//
//import utils.kkutils.JsonTool;
//import utils.kkutils.common.LogTool;
//import utils.kkutils.http.HttpRequest;
//import utils.kkutils.http.HttpUiCallBack;
//import utils.kkutils.http.InterfaceHttpTool;
//import utils.kkutils.http.SSLTool;
//
///**
// * Created by coder on 15/12/25.
// * 网络请求接口xutils的实现
// */
//public class HttpToolXutils implements InterfaceHttpTool {
//
//    String[] crts;
//
//    /**
//     * 初始化
//     *
//     * @param context
//     */
//    @Override
//    public void init(Context context) {
//        if (context instanceof Application) {
//            x.Ext.init((Application) context);
//            x.Ext.setDebug(LogTool.debug);
//        }
//    }
//
//    @Override
//    public void init(Context context, String... crts) {
//        this.crts = crts;
//        init(context);
//    }
//
//    /***
//     * 同步的, 直接返回请求数据
//     *
//     * @param request
//     * @param clzz
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T extends Serializable> T request(HttpRequest request, Class<T> clzz) {
//        try {
//            request.beginRequest();
//            RequestParams requestParams = convertHttpRequestToRequestParams(request);
//            String temStr = x.http().requestSync(requestParams.getMethod(), requestParams, String.class);
//            T result = JsonTool.toJava(temStr, clzz);
//            request.setResponseDataStr(temStr, clzz);
//            request.endRequest();
//            return result;
//        } catch (Throwable t) {
//            LogTool.ex(t);
//        }
//
//        return null;
//    }
//
//    /***
//     * 异步请求,返回的数据再callBack 中
//     *
//     * @param request
//     * @param clzz
//     * @param callBack
//     * @param <T>
//     */
//    @Override
//    public <T extends Serializable> void request(final HttpRequest request, final Class<T> clzz, final HttpUiCallBack callBack) {
//        try {
//            request.beginRequest();
//            RequestParams requestParams = convertHttpRequestToRequestParams(request);
//            final Callback.Cancelable cancelable= x.http().request(requestParams.getMethod(), requestParams, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String t) {
//                    request.endRequest();
//                    if (callBack != null) {
//                        request.setResponseDataStr(t, clzz);
//                        callBack.notifyState(HttpUiCallBack.State.stateOnSuccess, request, clzz);
//                    }
//                }
//
//                @Override
//                public void onError(Throwable throwable, boolean b) {
//                    request.endRequest();
//                    if (callBack != null) {
//                        callBack.notifyState(HttpUiCallBack.State.stateOnNetLocalError, request, clzz);
//                        LogTool.ex(throwable);
//                    }
//                }
//
//                @Override
//                public void onCancelled(CancelledException e) {
//                    if (callBack != null) {
//                        callBack.notifyState(HttpUiCallBack.State.stateOnCancelled, request, clzz);
//                    }
//                }
//
//                @Override
//                public void onFinished() {
//                    if (callBack != null) {
//                        callBack.notifyState(HttpUiCallBack.State.stateOnFinish, request, clzz);
//                    }
//                }
//            });
//            request.setCacelImp(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        cancelable.cancel();
//                    }catch (Exception e){
//                        LogTool.ex(e);
//                    }
//                }
//            });
//        } catch (Throwable t) {
//            LogTool.ex(t);
//        }
//    }
//
//
//    private RequestParams convertHttpRequestToRequestParams(HttpRequest httpRequest) {
//        RequestParams requestParams = new RequestParams(httpRequest.getUrlStr());
//        if (crts != null) {
//            requestParams.setSslSocketFactory(SSLTool.initSSLFactoryByCrt(crts));
//        }
//        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {//忽略hostname 验证。。。
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
//        requestParams.setCharset(httpRequest.getCharset());
//        requestParams.setUseCookie(httpRequest.isUseCookie());
//        requestParams.setCancelFast(httpRequest.isCancelFast());
//        requestParams.setConnectTimeout(1000 * 60);//超时
//        requestParams.setReadTimeout(1000*60);//文件上传不限制，这个是文件上传的
//        requestParams.setProxy(httpRequest.proxy);//设置代理
//
//        requestParams.setMethod(HttpMethod.valueOf(httpRequest.getRequestMethod().name()));
//
//
//        /***
//         * 放入参数
//         */
//        Iterator<Map.Entry<String, Object>> iterator = httpRequest.getQueryMap().entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, Object> entry = iterator.next();
//            String key = entry.getKey();
//            Object value = entry.getValue();
//
//
//           if (httpRequest.getRequestMethod().equals(HttpRequest.RequestMethod.POST)) {
//                requestParams.setMethod(HttpMethod.POST);
//                if (value instanceof File) {
//                    requestParams.setMultipart(true);
//                    requestParams.addBodyParameter(key, (File) value);
//                    requestParams.setReadTimeout(1000*60*9999);//文件上传不限制
//                } else {
//                    requestParams.addBodyParameter(key, "" + value);
//                }
//            }else {
//                   requestParams.addQueryStringParameter(key, "" + value);
//           }
//        }
//
//        requestParams.setBodyContent(httpRequest.getBodyCountent());
//
//        /***
//         * 放入Header
//         */
//        Iterator<Map.Entry<String, String>> iteratorHeader = httpRequest.getHeaderMap().entrySet().iterator();
//        while (iteratorHeader.hasNext()) {
//            Map.Entry<String, String> entry = iteratorHeader.next();
//            requestParams.setHeader(entry.getKey(), entry.getValue());
//        }
//        return requestParams;
//    }
//}
