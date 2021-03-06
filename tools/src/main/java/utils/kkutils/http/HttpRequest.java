package utils.kkutils.http;


import java.net.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import utils.kkutils.HttpTool;
import utils.kkutils.JsonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.db.MapDB;
import utils.kkutils.encypt.Md5Tool;
import utils.kkutils.json.JsonDataParent;
import utils.kkutils.ui.pullrefresh.PageControl;

/**
 * Created by coder on 15/12/25.
 * http 请求的参数
 */
public class HttpRequest {
    public Proxy proxy;//设置代理
    public long timeBeginRequest = 0;//开始请求的时间
    public long timeEndRequest = 0;//结束请求的时间
    public String cacheStr = "";
    private String urlStr = "";
    private String charset = "UTF-8";
    private boolean useCookie = true; // 是否在请求过程中启用cookie
    private boolean cancelFast = true; // 是否可以被立即停止, true: 为请求创建新的线程, 取消时请求线程被立即中断.
    private boolean useCache = false;//是否启用缓存
    private RequestMethod requestMethod = RequestMethod.GET;
    public static Map<String, Object> queryDefaultMap = new LinkedHashMap<>();//默认参数
    private Map<String, Object> queryMap = new LinkedHashMap<String, Object>();
    private Map<String, String> headerMap = new LinkedHashMap<String, String>();
    private String bodyCountent = "";//发送内容就是一个字符串
    private Class responseClass;//返回数据的class
    String[]excludeCacheParams;//缓存key  不包含字段





    /**
     * 返回的数据
     */
    private Object responseData;
    /**
     * 返回的字符串
     */
    private String responseDataStr;

    /**
     * 构造一个请求
     *
     * @param url
     * @return
     */
    public static HttpRequest url(String url) {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.urlStr = url;
        return httpRequest;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public HttpRequest setUseCache(boolean useCache) {
        this.useCache=useCache;
        return this;
    }
    //缓存key  不包含字段
    public HttpRequest setExcludeCacheParams(String...excludeCacheParams) {
        this.excludeCacheParams=excludeCacheParams;
        return this;
    }





    /**
     * 放入一个请求参数
     *
     * @param queryKey
     * @param queryValue
     * @return
     */
    public HttpRequest addQueryParams(String queryKey, Object queryValue) {
        if (queryValue != null && !StringTool.isEmpty("" + queryValue)){

            {//处理 queryValue 自身带有多个参数的情况
                try {
                    if(queryValue instanceof String){
                        String value=""+queryValue;
                        if(value.contains("&")){
                            String[] split = value.split("&");
                            for(String sp:split){
                                String[] splitKeyValue = sp.split("=");
                                if(splitKeyValue.length==1){
                                    this.queryMap.put(queryKey, splitKeyValue[0]);
                                }
                                if(splitKeyValue.length>1){
                                    this.queryMap.put(splitKeyValue[0], sp.substring(splitKeyValue[0].length()+1));
                                }
                            }
                            return this;
                        }
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }

            }


            this.queryMap.put(queryKey, queryValue);
        }else {
            Object defaultObj=queryDefaultMap.get(queryKey);
            if (defaultObj != null && !StringTool.isEmpty("" + defaultObj)){
                this.queryMap.put(queryKey, defaultObj);
            }
        }
        return this;
    }

    /***
     * 放入很多参数
     * @param params
     * @return
     */
    public HttpRequest addQueryParamsAll(Map<String ,String> params) {
        if(params!=null&&params.size()>0){
            for(Map.Entry<String ,String> entry:params.entrySet()){
                addQueryParams(entry.getKey(),entry.getValue());
            }
        }
        return this;
    }
    /***
     * 删除一个指定的参数
     *
     * @param queryKey
     * @return
     */
    public HttpRequest removeParams(String queryKey) {
        this.queryMap.remove(queryKey);
        return this;
    }

    /**
     * 放入一个header
     *
     * @return
     */
    public HttpRequest addHeader(String headKey, String headerValue) {
        this.headerMap.put(headKey, headerValue);
        return this;
    }

    public HttpRequest get() {
        requestMethod = RequestMethod.GET;
        return this;
    }

    public HttpRequest post() {
        requestMethod = RequestMethod.POST;
        return this;
    }

    public String getBodyCountent() {
        return bodyCountent;
    }

    public void setBodyCountent(String bodyCountent) {
        this.bodyCountent = bodyCountent;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public HttpRequest setUrlStr(String urlStr) {
        this.urlStr = urlStr;
        return this;
    }

    public boolean isUseCookie() {
        return useCookie;
    }

    public void setUseCookie(boolean useCookie) {
        this.useCookie = useCookie;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public HttpRequest setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isCancelFast() {
        return cancelFast;
    }

    public void setCancelFast(boolean cancelFast) {
        this.cancelFast = cancelFast;
    }

    public Object getResponseData() {
        if(responseData==null){
            if(getResponseClass()!=null){
                setResponseData(JsonTool.toJava(responseDataStr, getResponseClass()));
            }
        }
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public String getResponseDataStr() {
        return "" + responseDataStr;
    }

    public void setResponseDataStr(String responseDataStr, Class clzz) {
        responseDataStr = "" + responseDataStr;
        setResponseData(null);//清空数据
        this.responseDataStr = responseDataStr;
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(getRequestMethod() + "     " + getUrlStr());
        if (!getUrlStr().contains("?")) {
            sb.append("?");
        }

        Iterator iterator = getQueryMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (RequestMethod.GET.equals(getRequestMethod())) {
                sb.append("&" + entry.getKey() + "=" + entry.getValue());
            } else {
                sb.append("\n");
                sb.append(entry.getKey() + "=" + entry.getValue());
            }
        }


        sb.append("\n");
        sb.append("返回数据:    ");
        sb.append(getResponseDataStr());
        sb.append("\n");

        return sb.toString();
    }

    /***
     * 获取请求连接， get的
     * @return
     */
    public String getUrlRequestGet(){
        StringBuffer sb = new StringBuffer(getUrlStr());
        if (!sb.toString().contains("?")) {
            sb.append("?");
        }
        Iterator iterator = this.getQueryMap().entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        return sb.toString();
    }


    public void printLogPost() {
        LogTool.printPart(false,"参数:");
        LogTool.printPart(false,"");
        Iterator iterator = getQueryMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            LogTool.printPart(!iterator.hasNext(),entry.getKey() + "=" + entry.getValue());
        }
    }

    public void printLog() {
        synchronized (LogTool.class){
            LogTool.printBegin();
            LogTool.printPart(true,"请求耗时  "+((timeEndRequest-timeBeginRequest))+"  ：  "+getRequestMethod()+"  "+getUrlRequestGet());

            printLogPost();
            LogTool.printPart("返回数据: ");
            LogTool.printPart(getResponseDataStr());
            LogTool.printEnd();
        }
    }

    public void beginRequest() {
        try {
            timeBeginRequest = System.currentTimeMillis();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    public void endRequest(){
        try {
            timeEndRequest = System.currentTimeMillis();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }

    public String getCacheKey() {
        try {

            Map<String, Object> queryMap=new HashMap(getQueryMap());
            if(excludeCacheParams!=null){
                for(String param:excludeCacheParams){
                    queryMap.remove(param);
                }
            }
            String keyTem = getUrlStr() + getBodyCountent() + JsonTool.toJsonStr(getHeaderMap()) + JsonTool.toJsonStr(queryMap);
            String key = Md5Tool.md5(keyTem);
            return key;
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";
    }

    public String getCacheStr() {
        try {
            String key = getCacheKey();
            if (!StringTool.isEmpty(key)) {
                if (!StringTool.isEmpty(cacheStr)) {
                    return cacheStr;
                } else {
                    cacheStr = MapDB.loadObj(getCacheKey(), String.class);
                    return cacheStr;
                }

            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";
    }

    public void setCacheStr(String cacheStr) {
        try {
            if(!canSaveCacheToLocal(cacheStr)){
                LogTool.s("不能缓存接口");
                return;
            }
            String key = getCacheKey();
            if (!StringTool.isEmpty(key)) {
                this.cacheStr = cacheStr;
                MapDB.saveObj(false,getCacheKey(), cacheStr);
                LogTool.s("保存了接口缓存：");
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void saveToLocalCache() {
        if (isUseCache() && StringTool.notEmpty(getResponseDataStr())) {
            setCacheStr(getResponseDataStr());
        }
    }

    public Object getCacheData() {
        try {
            if (getResponseClass() != null) {
                return JsonTool.toJava(getCacheStr(), getResponseClass());
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return null;
    }

    /***
     * 使用缓存的列表， 子类覆盖
     * @return
     */
    public String[] getCacheList(){
        return null;
    }

    /***
     * 是否可以保存缓存， 一般返回结果正常才保存
     * @param cacheStr
     * @return
     */
    public boolean canSaveCacheToLocal(String cacheStr){
        return true;
    }



    /***
     * 初始化是否使用缓存
     */
    void initUseCache(){
        if(getCacheList()==null)return;
        String url=getUrlStr();
        String[] list=getCacheList();
        for(String tem:list){
            if(url.contains(tem)){
                setUseCache(true);
                return;
            }
        }
    }
    /*******************
     * 下面是方便qin
     ***********************************/


    public <T extends JsonDataParent> HttpRequest send(Class<T> clzz, HttpUiCallBack<T> callBack) {
        try {//没有更多数据了 的判断 如果不要可注释
            if((""+PageControl.noMoreDataPage).equals(""+getQueryMap().get("page"))){
                JsonDataParent jsonDataParent=clzz.newInstance();
                jsonDataParent.msg="没有更多数据了";
                callBack.onSuccess((T) jsonDataParent);
                return this;
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
        initUseCache();




        setResponseClass(clzz);
        if (callBack == null) callBack = new HttpUiCallBack<T>() {
            @Override
            public void onSuccess(T data) {

            }
        };
        if (isUseCache()) {
            String cacheStr = getCacheStr();
            if (!StringTool.isEmpty(cacheStr)) {
                callBack.notifyState(HttpUiCallBack.State.stateOnCache, this, clzz);
            }
        }
        HttpTool.request(this, clzz, callBack);
        return this;
    }

    /***
     * 取消当前请求
     *
     * 需要在http 实现里面添加  setCacelImp()
     */
    public void cancel(){
        if(cacelImp!=null){
            try {
                cacelImp.run();
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
    }


     Runnable cacelImp;

    /***
     * 需要在http 实现里面添加
     * @param cacelImp
     */
    public void setCacelImp(Runnable cacelImp) {
        this.cacelImp = cacelImp;
    }
    public enum RequestMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        PATCH("PATCH"),
        HEAD("HEAD"),
        MOVE("MOVE"),
        COPY("COPY"),
        DELETE("DELETE"),
        OPTIONS("OPTIONS"),
        TRACE("TRACE"),
        CONNECT("CONNECT");
        String value = "GET";
        RequestMethod(String valueIn) {
            this.value = valueIn;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
