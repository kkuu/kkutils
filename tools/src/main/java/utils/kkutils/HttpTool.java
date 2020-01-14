package utils.kkutils;

import android.content.Context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import utils.kkutils.common.LogTool;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.http.InterfaceHttpTool;
import utils.kkutils.http.implement.HttpToolOkHttp;
import utils.kkutils.http.implement.HttpToolXutils;

/**
 * Created by coder on 15/12/24.
 * 网络请求工具类
 */
public class HttpTool {
    public static InterfaceHttpTool httpTool = new HttpToolOkHttp();

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        httpTool.init(context);
    }

    /***
     * @param context
     * @param crts    证书
     */
    public static void init(Context context, String... crts) {
        httpTool.init(context, crts);
    }

    public static <T extends Serializable> T request(HttpRequest request, Class<T> clzz) {
        request.setResponseClass(clzz);
        return httpTool.request(request, clzz);
    }

    public static <T extends Serializable> void request(HttpRequest request, Class<T> clzz, HttpUiCallBack<T> callBack) {
        request.setResponseClass(clzz);
        callBack.setRequest(request);
        httpTool.request(request, clzz, callBack);
    }





}
