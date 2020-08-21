package kk.kktools.shu.data;


import utils.kkutils.HttpTool;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;

public class ShuTool {
    public String url = "https://infosxs.pysmei.com/";//url = "https://souxs.leeyegy.com/";
    public void search(String name, HttpUiCallBack<ShuSerachBean> callBack) {
        String urlStr=url + "search.aspx?siteid=app2&" + "key=" + name;
        HttpTool.request(HttpRequest.url(urlStr), ShuSerachBean.class, callBack);
    }
    public void mulu(int id, HttpUiCallBack<ShuMuLuBean> callBack){
        String urlStr=getPath(id)+"/index.html";
        HttpTool.request(HttpRequest.url(urlStr), ShuMuLuBean.class, callBack);
    }
    public void xiangQing(int id,int contentId,HttpUiCallBack<ShuXiangQingBean> callBack){
        String urlStr=getPath(id)+""+contentId+".html";
        HttpTool.request(HttpRequest.url(urlStr), ShuXiangQingBean.class, callBack);
    }
    public String getPath(int id){
        int parent= (int) Math.ceil(id*1.0/1000);
        String urlStr=url+"BookFiles/Html/"+parent+"/"+id+"/";
        return urlStr;
    }

}