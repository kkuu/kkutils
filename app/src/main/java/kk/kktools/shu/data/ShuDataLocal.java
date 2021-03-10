package kk.kktools.shu.data;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.db.MapDB;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.parent.KKParentActivity;
import utils.kkutils.ui.dialog.DialogSimple;
import utils.kkutils.ui.dialog.DialogTool;

public class ShuDataLocal {
    public static void delete(ShuInfoBean.BookInfo bookInfo) {
        List<ShuInfoBean.BookInfo> bookInfoList = getBookInfoList();
        for (int i = 0; i < bookInfoList.size(); i++) {
            ShuInfoBean.BookInfo bookInfo1 = bookInfoList.get(i);
            if(bookInfo1.Id==bookInfo.Id){
                bookInfoList.remove(bookInfo1);
                i--;
            }
        }
        save();
    }

    public static List<ShuInfoBean.BookInfo> bookInfoList;
    public static List<ShuInfoBean.BookInfo> getBookInfoList() {
        if(bookInfoList==null){
            bookInfoList = MapDB.loadObjList("book", ShuInfoBean.BookInfo.class);
        }
        if (bookInfoList == null) {
            return new ArrayList<>();
        }
        return bookInfoList;
    }

    public static void add(ShuInfoBean.BookInfo bookInfo) {
        KKParentActivity.showWaitingDialogStac("");
        List<ShuInfoBean.BookInfo> bookInfoList = getBookInfoList();
        bookInfoList.add(0, bookInfo);
        save();
        CommonTool.showToast("添加成功");
        KKParentActivity.hideWaitingDialogStac();
    }

    public static void save() {
        MapDB.saveObj(true, "book", bookInfoList);
    }

    public static void savePage(ShuXiangQingBean xiangQingBean, int scrollY) {
        try {
            if(xiangQingBean==null)return;
            String key=""+xiangQingBean.data.id;//+"-"+xiangQingBean.data.cid;
            MapDB.saveObj(true, key,xiangQingBean.data.cid+"="+scrollY);
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
    public static ShuMuLuBean.MuLuItem getPreMuLuInfo(ShuInfoBean.BookInfo bookInfo) {
        String s = MapDB.loadObjByDefault("" + bookInfo.Id, String.class, bookInfo.FirstChapterId + "=" + 0);//默认第一章位置 0
        String[] split = s.split("=");
        ShuMuLuBean.MuLuItem muLuItem=new ShuMuLuBean.MuLuItem();
        muLuItem.id=Integer.valueOf(split[0]);
        muLuItem.parentId=bookInfo.Id;
        muLuItem.pageY=Integer.valueOf(split[1]);
        muLuItem.name=bookInfo.Name;
        return muLuItem;
    }

    /***
     * 缓存之后章节
     * @param parentId
     * @param id
     */
    public static void cache(int parentId, int id) {
        CommonTool.showToast("缓存开始");
        new ShuTool().xiangQing(parentId, id, new HttpUiCallBack<ShuXiangQingBean>() {
            @Override
            public void onSuccess(ShuXiangQingBean data) {
                if(data.data.nid>0){
                    cache(parentId, data.data.nid);
                }else {
                    DialogSimple.showTiShiDialog("缓存完成", "", "确定", null, "", null);
                }
            }
        });
    }
}
