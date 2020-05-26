package kk.kktools;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.SaoMaTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.pullrefresh.KKRefreshLayout;

public class TestRecycleView extends KKParentFragment {
    View btn_add_top;
    @Override
    public int initContentViewId() {
        return R.layout.test_recyclevew;
    }

    @Override
    public void initData() {
        recycleView.setNestedScrollingEnabled(true);

        refreshLayout.setRefreshPrimaryColor(Color.BLUE,Color.RED);
        refreshLayout.bindLoadDataAndRefresh(null, new KKRefreshLayout.LoadListDataInterface() {
            @Override
            public void loadPageData(int page) {
                refreshLayout.stopRefresh(null);

                initRecycleView(TestData.getTestStrList(10*page));
            }
        });


    }
    public void initRecycleView(List<String> list){
        recycleView.setData(list, R.layout.activity_main_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);

                Object o = recycleView.datas.get(position);
                UiTool.setTextView(itemView,R.id.tv_main_title,"测试"+position+"  "+o );

                itemView.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View v) {
                        SaoMaTool.startSaoMa(TestRecycleView.this);
                    }
                });
            }
        });
    }


    @Override
    public void initListener() {
        btn_add_top.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                //recycler_view.addData(0,new Object());

                List list=new ArrayList();
                list.add(new Object());
                list.add(new Object());
                recycleView.addDataList(5,list);
            }
        });
    }
}
