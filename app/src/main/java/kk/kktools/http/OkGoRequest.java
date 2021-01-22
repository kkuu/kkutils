package kk.kktools.http;

import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.BodyRequest;

import okhttp3.Request;
import okhttp3.RequestBody;

public class OkGoRequest <T,R extends OkGoRequest> extends BodyRequest<T, OkGoRequest> {
    public HttpMethod httpMethod=HttpMethod.GET;
    public OkGoRequest(String url) {
        super(url);
    }
    public static OkGoRequest url(String url){
        return new OkGoRequest(url);
    }
    public R get(){
        httpMethod=HttpMethod.GET;
        return (R) this;
    }
    public R post(){
        httpMethod=HttpMethod.POST;
        return (R) this;
    }
    @Override
    public HttpMethod getMethod() {
        return httpMethod;
    }
    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = generateRequestBuilder(requestBody);
        if(getMethod()==HttpMethod.GET){
            requestBuilder.get();
        }else {
            requestBuilder.post(requestBody);
        }
        return requestBuilder.url(url).tag(tag).build();
    }
}
