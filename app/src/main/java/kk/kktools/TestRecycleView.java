package kk.kktools;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.SaoMaTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.pullrefresh.KKRefreshLayout;
import utils.kkutils.ui.textview.SpanTextTool;

public class TestRecycleView extends KKParentFragment {
    View btn_add_top;
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_recyclevew;
    }

    @Override
    public void initData() {
        recycleView.setNestedScrollingEnabled(true);

        refreshLayout.setRefreshPrimaryColor(Color.BLUE,Color.RED);
        refreshLayout.bindLoadDataAndRefresh(null, new KKRefreshLayout.LoadListDataInterface() {
            @Override
            public void loadPageData(int page) {
                refreshLayout.stopRefresh(null);

                initRecycleView(TestData.getTestStrList(100));
            }
        });


    }
    public void initRecycleView(List<String> list){
        recycleView.setData(list, R.layout.kk_test_recyclevew_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);


                KKSimpleRecycleView recycleView_heng=itemView.findViewById(R.id.recycleView_heng);
                recycleView_heng.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
                recycleView_heng.setData(TestData.getTestStrList(10), R.layout.kk_test_recyclevew_item_item, new KKSimpleRecycleView.KKRecycleAdapter() {
                    @Override
                    public void initData(int position, int type, View itemView) {
                        super.initData(position, type, itemView);

                        TextView textView=itemView.findViewById(R.id.textView);
                        new SpanTextTool("").addStringSpan("s", SpanTextTool.getImageGifSpan(R.drawable.kk_test_gif,100, 100, textView, 80))
                                .setTextView(textView);
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
