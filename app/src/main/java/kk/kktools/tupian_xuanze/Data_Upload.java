package kk.kktools.tupian_xuanze;

import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.json.JsonDataParent;

public class Data_Upload extends JsonDataParent {

    public String path;

    /**
     *  <input type="text" name="OSSAccessKeyId" value="{{upload.OSSAccessKeyId}}">
     *     <input type="text" name="policy" value="{{upload.policy}}">
     *     <input type="text" name="Signature" value="{{upload.Signature}}">
     *     <input type="text" name="key" value="上传name">
     *     <input type="text" name="success_action_redirect" value="http://oss.aliyun.com">
     *     <input type="text" name="success_action_status" value="201">
     *     <input name="file" type="file" id="file">
     *
     * @param uploadInfo
     * @param path
     * @param callBack
     */
    public static void load(Data_UploadInfo uploadInfo,String path,HttpUiCallBack<Data_Upload> callBack){
        HttpRequest httpRequest=HttpRequest.url(uploadInfo.data.url);
        httpRequest.addQueryParams("OSSAccessKeyId",uploadInfo.data.OSSAccessKeyId);
        httpRequest.addQueryParams("policy",uploadInfo.data.policy);
        httpRequest.addQueryParams("Signature",uploadInfo.data.Signature);
        httpRequest.addQueryParams("key", new File(path).getName());
        httpRequest.addQueryParams("success_action_redirect","");
        httpRequest.addQueryParams("success_action_status","201");
        httpRequest.addQueryParams("file",new File(path));
        httpRequest.post();

        httpRequest.send(Data_Upload.class, new HttpUiCallBack<Data_Upload>() {
            @Override
            public void onSuccess(Data_Upload data) {
                Data_Upload data_upload=new Data_Upload();

                if(!data.isDataOkAndToast()){
                    try {
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(new ByteArrayInputStream(httpRequest.getResponseDataStr().getBytes()));
                        NodeList location = document.getElementsByTagName("Location");
                        Node item = location.item(0);
                        String nodeValue = item.getTextContent();
                        data_upload.code=1;
                        data_upload.path=nodeValue;
                        callBack.onSuccess(data_upload);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    callBack.onSuccess(data);
                }
            }
        });

    }

    @Override
    public boolean isDataOk() {
        return isDataOkNotInUiThread();
    }

    @Override
    public boolean isDataOkAndToast() {
        if(!isDataOk()) CommonTool.showToast(msg);
        return isDataOk();
    }

    @Override
    public boolean isDataOkNotInUiThread() {

            return code==1;
    }


}
