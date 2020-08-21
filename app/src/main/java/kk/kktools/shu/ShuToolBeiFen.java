//package kk.kktools.shu;
//
//
//import utils.kkutils.HttpTool;
//import utils.kkutils.http.HttpRequest;
//import utils.kkutils.http.HttpUiCallBack;
//
//public class ShuToolBeiFen {
//    public String url = "https://infosxs.pysmei.com/";//url = "https://souxs.leeyegy.com/";
//    public ShuToolBeiFen(){
//
//    }
//
//    public ShuSerachBean search(String name) {
//        String urlStr=url + "search.aspx?siteid=app2&" + "key=" + name;
//        HttpTool.request(HttpRequest.url(urlStr), ShuSerachBean.class,new HttpUiCallBack<shu>() )
//        String str = OKHttpTool.i().getString(this.url + "search.aspx?siteid=app2&" + "key=" + name);
//        ShuSerachBean shuSerachBean = JSON.parseObject(str, ShuSerachBean.class);
//
//        return shuSerachBean;
//    }
//    public ShuMuLuBean mulu(int id){
//        String urlStr=getPath(id)+"/index.html";
//        String str = OKHttpTool.i().getString(urlStr);
//        ShuMuLuBean shuMuLuBean = JSON.parseObject(str, ShuMuLuBean.class);
//        System.out.println(str);
//        return shuMuLuBean;
//    }
//    public String getPath(int id){
//        int parent= (int) Math.ceil(id*1.0/1000);
//        String urlStr=url+"BookFiles/Html/"+parent+"/"+id+"/";
//        return urlStr;
//    }
//
//    public ShuXiangQingBean xiangQing(int id,int contentId){
//        String urlStr=getPath(id)+""+contentId+".html";
//        String str = OKHttpTool.i().getString(urlStr);
//        ShuXiangQingBean shuXiangQingBean = JSON.parseObject(str, ShuXiangQingBean.class);
//        System.out.println(str);
//        return shuXiangQingBean;
//    }
//
//    public static void main(String [] args) throws Exception {
//        ShuToolBeiFen shuTool=new ShuToolBeiFen();
//        ShuSerachBean serach=shuTool.search("大梦");
//        int id=serach.data.get(0).Id;
//        ShuMuLuBean mulu = shuTool.mulu(id);
//        shuTool.xiangQing(id,mulu.data.list.get(0).list.get(0).id);
//        System.out.println(serach);
//    }
//
//}