package kk.kktools.recycleview_test;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.recycleview.KKDecorationSimple;

public class TestRecycleViewJianGe extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_recyclevew;
    }

    @Override
    public void initData() {


        recycleView.setData(TestData.getTestStrList(90), R.layout.kk_test_recyclevew_jiange_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
            }
        });

        recycleView.setLayoutManager(new GridLayoutManager(getContext(),4));
        recycleView.addItemDecoration(new KKDecorationSimple(4,0,10,1,R.color.colorAccent));
    }

}
