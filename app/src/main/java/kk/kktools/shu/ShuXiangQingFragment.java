package kk.kktools.shu;

import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import kk.kktools.R;
import kk.kktools.shu.data.ShuDataLocal;
import kk.kktools.shu.data.ShuInfoBean;
import kk.kktools.shu.data.ShuMuLuBean;
import kk.kktools.shu.data.ShuSerachBean;
import kk.kktools.shu.data.ShuTool;
import kk.kktools.shu.data.ShuXiangQingBean;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.db.MapDB;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.parent.KKParentActivity;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKScrollView;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.KKViewPager;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.dialog.DialogTool;

public class ShuXiangQingFragment extends KKParentFragment {


    @Override
    public int initContentViewId() {
        return R.layout.shu_xiangqing;
    }


    ShuMuLuBean.MuLuItem muLuItem;
    View btn_go_next,btn_go_next_page;
    TextView tv_shu_content;
    KKScrollView shu_scrollview;
    View tv_go_mulu;
    @Override
    public void initData() {
         muLuItem = (ShuMuLuBean.MuLuItem) getArgument("muLuItem", new ShuMuLuBean.MuLuItem());

        StatusBarTool.setStatusBarColor(getActivity(), R.color.zxing_transparent, true, true);
        loadData(muLuItem.parentId,muLuItem.id);

        btn_go_next.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                if(xiangQingBean.data.nid>0){
                    loadData(muLuItem.parentId, xiangQingBean.data.nid);
                }
            }
        });
        btn_go_next_page.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                shu_scrollview.scrollBy(0, shu_scrollview.getHeight()-shu_scrollview.getPaddingTop()*2- CommonTool.dip2px(10));
            }
        });
        tv_go_mulu.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                ShuInfoBean.BookInfo bookInfo=new ShuInfoBean.BookInfo();
                bookInfo.Id=muLuItem.parentId;
                bookInfo.Name=muLuItem.name;
                ShuMuLuFragment.byData(bookInfo).go();
                getActivity().finish();
            }
        });
    }
    ShuXiangQingBean xiangQingBean;
    public void loadData(int parentId, int id){
        KKParentActivity.showWaitingDialogStac("");
        new ShuTool().xiangQing(parentId, id, new HttpUiCallBack<ShuXiangQingBean>() {
            @Override
            public void onSuccess(ShuXiangQingBean data) {
                KKParentActivity.hideWaitingDialogStac();
                xiangQingBean=data;
                UiTool.setTextView(parent,R.id.tv_curr_zhang,xiangQingBean.data.name+"   "+xiangQingBean.data.cname);
                UiTool.setTextView(tv_shu_content, data.data.content);

                if(xiangQingBean.data.cid==muLuItem.id){
                    shu_scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            shu_scrollview.scrollTo(0, muLuItem.pageY);
                        }
                    });
                }else {
                    shu_scrollview.scrollTo(0, 0);
                }


            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCurr();
    }
    public void saveCurr(){
        ShuDataLocal.savePage(xiangQingBean,shu_scrollview.getScrollY());
    }

    public static KKParentFragment byData(ShuMuLuBean.MuLuItem muLuItem) {
        return new ShuXiangQingFragment().addArgument("muLuItem",muLuItem);
    }

}
