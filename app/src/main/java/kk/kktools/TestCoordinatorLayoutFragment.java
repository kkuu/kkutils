package kk.kktools;

import android.view.View;

import utils.wzutils.common.TestData;
import utils.wzutils.parent.WzParentFragment;
import utils.wzutils.ui.WzSimpleRecycleView;

public class TestCoordinatorLayoutFragment extends WzParentFragment {
    WzSimpleRecycleView recycler_view;
    @Override
    public int initContentViewId() {
        return R.layout.test_coordinatorlayout_demo;
    }

    @Override
    public void initData() {
        recycler_view.setNestedScrollingEnabled(true);
        recycler_view.setData(TestData.getTestStrList(30), R.layout.activity_main_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
            }
        });
    }

    @Override
    public void initListener() {

    }
}
