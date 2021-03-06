package kk.kktools.shu;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import kk.kktools.R;
import kk.kktools.shu.data.ShuDataLocal;
import kk.kktools.shu.data.ShuInfoBean;
import kk.kktools.shu.data.ShuMuLuBean;
import kk.kktools.shu.data.ShuSerachBean;
import kk.kktools.shu.data.ShuTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.parent.KKParentActivity;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;

public class ShuSouSuoFragment extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.shu_sousuo;
    }

    View btn_sousuo;
    EditText et_sousuo;
    @Override
    public void initData() {


        btn_sousuo.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                refreshLayout.autoRefresh();

            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                String key= UiTool.checkNullAndToast(et_sousuo);
                new ShuTool().search(key, new HttpUiCallBack<ShuSerachBean>() {
                    @Override
                    public void onSuccess(ShuSerachBean data) {
                        refreshLayout.finishRefresh();
                        initList(data);
                    }
                });
            }
        });
    }
    public void initList(ShuSerachBean data){
        List<ShuInfoBean.BookInfo> list = data.data;
        recycleView.setData(list, R.layout.shu_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView, KKSimpleRecycleView.WzViewHolder wzViewHolder) {
                super.initData(position, type, itemView, wzViewHolder);
                ShuInfoBean.BookInfo bookInfo = list.get(position);
                initItem(bookInfo,itemView);

                TextView tv_shu_control = itemView.findViewById(R.id.tv_shu_control);
                UiTool.setTextView(tv_shu_control, "添加");
                tv_shu_control.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View v) {
                        KKParentActivity.showWaitingDialogStac("");
                        new ShuTool().info(bookInfo.Id, new HttpUiCallBack<ShuInfoBean>() {
                            @Override
                            public void onSuccess(ShuInfoBean data) {
                                KKParentActivity.hideWaitingDialogStac();
                                ShuDataLocal.add(data.data);
                                getActivity().finish();
                            }
                        });
                    }
                });
            }
        });
    }
    public static void initItem(ShuInfoBean.BookInfo bookInfo, View itemView){
        UiTool.setTextView(itemView,R.id.tv_shu_name, bookInfo.Name);
        UiTool.setTextView(itemView,R.id.tv_shu_zuixin, bookInfo.LastChapter);
        UiTool.setTextView(itemView,R.id.tv_shu_zuozhe, bookInfo.Author);
        itemView.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                ShuMuLuFragment.byData(bookInfo).go();
            }
        });
        ShuMuLuBean.MuLuItem preMuLuInfo = ShuDataLocal.getPreMuLuInfo(bookInfo);
        if(preMuLuInfo.id<bookInfo.LastChapterId){
            UiTool.setTextColor(itemView.findViewById(R.id.tv_shu_name), Color.RED);
        }else {
            UiTool.setTextColor(itemView.findViewById(R.id.tv_shu_name), R.color.kk_tv_h0);
        }
    }


}
