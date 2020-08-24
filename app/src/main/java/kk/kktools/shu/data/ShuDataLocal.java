package kk.kktools.shu.data;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.CommonTool;
import utils.kkutils.db.MapDB;
import utils.kkutils.parent.KKParentActivity;

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
        bookInfoList = MapDB.loadObjList("book", ShuInfoBean.BookInfo.class);
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
}
