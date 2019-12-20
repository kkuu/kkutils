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
        if(!checkRequest(request))return null;

        request.setResponseClass(clzz);
        return httpTool.request(request, clzz);
    }

    public static <T extends Serializable> void request(HttpRequest request, Class<T> clzz, HttpUiCallBack<T> callBack) {
        if(!checkRequest(request))return;

        request.setResponseClass(clzz);
        httpTool.request(request, clzz, callBack);
    }


    static Map<Object ,Long> requestMap=new HashMap<>();
    public static boolean checkRequest(HttpRequest request){
        try {
            {//频繁请求过滤
                String key=request.getCacheKey();
                Long time = requestMap.get(key);
                if(requestMap.size()>500)requestMap.clear();//清理
                if(time!=null){
                    if(System.currentTimeMillis()-time<request.getMinimumTimeInterval()){
                        LogTool.s("请求太频繁,取消请求"+request.getUrlRequestGet());
                        return false;
                    }
                }
                requestMap.put(request.getCacheKey(),System.currentTimeMillis());
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

        return true;

    }


}
