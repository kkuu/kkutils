package kk.kktools.tupian_xuanze;

import utils.kkutils.common.CommonTool;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.json.JsonDataParent;

public class Data_UploadInfo extends JsonDataParent {

    public DataBean data;

    public static void load(HttpUiCallBack<Data_UploadInfo> callBack){
        HttpRequest httpRequest=HttpRequest.url("http://192.168.110.200:8081/OssTool/getUploadInfo");
        httpRequest.send(Data_UploadInfo.class, callBack);
    }

    @Override
    public boolean isDataOk() {
        return code==1;
    }

    @Override
    public boolean isDataOkAndToast() {
        if(!isDataOk()) CommonTool.showToast(msg);
        return isDataOk();
    }


    public static class DataBean {

        public String OSSAccessKeyId;
        public String Signature;
        public String url;
        public String policy;
    }
}
