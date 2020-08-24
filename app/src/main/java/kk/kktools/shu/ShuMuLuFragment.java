package kk.kktools.shu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import kk.kktools.R;
import kk.kktools.shu.data.ShuInfoBean;
import kk.kktools.shu.data.ShuMuLuBean;
import kk.kktools.shu.data.ShuSerachBean;
import kk.kktools.shu.data.ShuTool;
import kk.kktools.shu.data.ShuXiangQingBean;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;

public class ShuMuLuFragment extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.shu_sousuo;
    }

    View btn_sousuo;
    EditText et_sousuo;
    @Override
    public void initData() {
        ShuInfoBean.BookInfo bookInfo= (ShuInfoBean.BookInfo) getArgument("bookInfo",new ShuInfoBean.BookInfo());
        UiTool.setTextView(et_sousuo, bookInfo.Name);
        btn_sousuo.setVisibility(View.GONE);
        et_sousuo.setEnabled(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new ShuTool().mulu(bookInfo.Id, new HttpUiCallBack<ShuMuLuBean>() {
                    @Override
                    public void onSuccess(ShuMuLuBean data) {
                        data.setParent(bookInfo);
                        refreshLayout.finishRefresh();
                        initList(data);
                    }
                });
            }
        });
        refreshLayout.autoRefresh();
    }
    public void initList(ShuMuLuBean data){

        List<ShuMuLuBean.MuLuItem> list = data.getAll();

        recycleView.setData(list, R.layout.shu_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView, KKSimpleRecycleView.WzViewHolder wzViewHolder) {
                super.initData(position, type, itemView, wzViewHolder);

                ShuMuLuBean.MuLuItem muLuItem = list.get(position);
                UiTool.setTextView(itemView,R.id.tv_shu_name, muLuItem.name);
                itemView.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View v) {
                        ShuXiangQingFragment.byData(muLuItem).go();
                        getActivity().finish();
                    }
                });

                UiTool.setWH(itemView, ViewGroup.LayoutParams.MATCH_PARENT, CommonTool.dip2px(50));
                itemView.findViewById(R.id.tv_shu_zuixin).setVisibility(View.GONE);

            }
        });
    }

    public static KKParentFragment byData(ShuInfoBean.BookInfo bookInfo){
        return new ShuMuLuFragment().addArgument("bookInfo",bookInfo);
    }
}
