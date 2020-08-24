package kk.kktools.shu;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kk.kktools.R;
import kk.kktools.shu.data.ShuDataLocal;
import kk.kktools.shu.data.ShuInfoBean;
import kk.kktools.shu.data.ShuMuLuBean;
import kk.kktools.shu.data.ShuSerachBean;
import kk.kktools.shu.data.ShuTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.db.MapDB;
import utils.kkutils.http.HttpUiCallBack;
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
        List<ShuInfoBean.BookInfo> bookInfoList = ShuDataLocal.getBookInfoList();

        for (ShuInfoBean.BookInfo bookInfo : bookInfoList) {
            initCache(bookInfo);
        }


        initList();

    }
    public void initList(){
        List<ShuInfoBean.BookInfo> bookInfoList = ShuDataLocal.getBookInfoList();
        recycleView.setData(bookInfoList, R.layout.shu_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView, KKSimpleRecycleView.WzViewHolder wzViewHolder) {
                super.initData(position, type, itemView, wzViewHolder);
                ShuInfoBean.BookInfo bookInfo = bookInfoList.get(position);
                ShuSouSuoFragment.initItem(bookInfo, itemView);


                TextView tv_shu_control = itemView.findViewById(R.id.tv_shu_control);
                UiTool.setTextView(tv_shu_control, "删除");
                tv_shu_control.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View v) {
                        DialogSimple.showTiShiDialog("提示", "是否删除", "删除", new KKViewOnclickListener() {
                            @Override
                            public void onClickKK(View v) {
                                ShuDataLocal.delete(bookInfo);
                                loadData();
                            }
                        },"取消", null);
                    }
                });

            }
        });
    }
    public void initCache(ShuInfoBean.BookInfo bookInfo){
            new ShuTool().mulu(bookInfo.Id, new HttpUiCallBack<ShuMuLuBean>() {
                @Override
                public void onSuccess(ShuMuLuBean data) {

                }
            });
            new ShuTool().info(bookInfo.Id, new HttpUiCallBack<ShuInfoBean>() {
                @Override
                public void onSuccess(ShuInfoBean data) {
                    bookInfo.LastChapter=data.data.LastChapter;
                    bookInfo.LastChapterId=data.data.LastChapterId;
                    bookInfo.LastTime=data.data.LastTime;
                    ShuDataLocal.save();
                    initList();
                }
            });
    }



}
