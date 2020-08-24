package kk.kktools.shu;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kk.kktools.R;
import kk.kktools.shu.data.ShuSerachBean;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.db.MapDB;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.dialog.DialogSimple;
import utils.kkutils.ui.dialog.DialogTool;
import utils.kkutils.ui.lunbo.LunBoTool;

public class ShuJiaFragment extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.shu_shujia;
    }

    View tv_go_sousuo;

    @Override
    public void initData() {
        tv_go_sousuo.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                new ShuSouSuoFragment().go();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        List<ShuSerachBean.BookInfo> bookInfoList = getBookInfoList();
        recycleView.setData(bookInfoList, R.layout.shu_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView, KKSimpleRecycleView.WzViewHolder wzViewHolder) {
                super.initData(position, type, itemView, wzViewHolder);
                ShuSerachBean.BookInfo bookInfo = bookInfoList.get(position);
                ShuSouSuoFragment.initItem(bookInfo, itemView);


                TextView tv_shu_control = itemView.findViewById(R.id.tv_shu_control);
                UiTool.setTextView(tv_shu_control, "删除");
                tv_shu_control.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View v) {
                        DialogSimple.showTiShiDialog("提示", "是否删除", "删除", new KKViewOnclickListener() {
                            @Override
                            public void onClickKK(View v) {
                                ShuJiaFragment.delete(bookInfo);
                                loadData();
                            }
                        },"取消", null);
                    }
                });

            }
        });
    }

    private static void delete(ShuSerachBean.BookInfo bookInfo) {
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
