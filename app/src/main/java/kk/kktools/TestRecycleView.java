package kk.kktools;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;

public class TestRecycleView extends KKParentFragment {
    KKSimpleRecycleView recycler_view;
    View btn_add_top;
    @Override
    public int initContentViewId() {
        return R.layout.test_recyclevew;
    }

    @Override
    public void initData() {
        recycler_view.setNestedScrollingEnabled(true);
        recycler_view.setData(TestData.getTestStrList(5), R.layout.activity_main_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);

                Object o = recycler_view.datas.get(position);
                UiTool.setTextView(itemView,R.id.tv_main_title,"测试"+position+"  "+o );
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
                recycler_view.addDataList(5,list);
            }
        });
    }
}
