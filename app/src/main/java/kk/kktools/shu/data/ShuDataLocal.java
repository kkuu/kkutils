package kk.kktools.shu.data;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.CommonTool;
import utils.kkutils.db.MapDB;

public class ShuDataLocal {
    public static void delete(ShuSerachBean.BookInfo bookInfo) {
        List<ShuSerachBean.BookInfo> bookInfoList = getBookInfoList();
        for (int i = 0; i < bookInfoList.size(); i++) {
            ShuSerachBean.BookInfo bookInfo1 = bookInfoList.get(i);
            if(bookInfo1.Id==bookInfo.Id){
                bookInfoList.remove(bookInfo1);
                i--;
            }
        }
        MapDB.saveObj(true, "book", bookInfoList);
    }

    public static List<ShuSerachBean.BookInfo> getBookInfoList() {
        List<ShuSerachBean.BookInfo> book = MapDB.loadObjList("book", ShuSerachBean.BookInfo.class);
        if (book == null) {
            return new ArrayList<>();
        }
        return book;
    }

    public static void add(ShuSerachBean.BookInfo bookInfo) {
        List<ShuSerachBean.BookInfo> bookInfoList = getBookInfoList();
        bookInfoList.add(0, bookInfo);
        MapDB.saveObj(true, "book", bookInfoList);
        CommonTool.showToast("添加成功");
    }

}
