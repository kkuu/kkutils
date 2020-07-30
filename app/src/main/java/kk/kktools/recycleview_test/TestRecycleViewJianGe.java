package kk.kktools.recycleview_test;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.recycleview.KKDecoration;

public class TestRecycleViewJianGe extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_recyclevew;
    }

    @Override
    public void initData() {


//        parent.setBackgroundColor(Color.parseColor("#eeeeee"));
        recycleView.setBackgroundColor(Color.parseColor("#eeeeee"));
        recycleView.setData(TestData.getTestStrList(11), R.layout.kk_test_recyclevew_jiange_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
            }
        });

//        recycleView.setLayoutManager(new LinearLayoutManager(getContext(),3));
        recycleView.addItemDecoration(new KKDecoration(1,0,10,20,3,Color.BLACK){

            {
                initAllShow();
                setBoder(false, 0);
            }
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
               // UiTool.setWHEqual(view);
            }
        });
        int padding=CommonTool.dip2px(10);
//        recycleView.setPadding(padding,padding,padding, padding);
       // UiTool.setMargin(recycleView, padding, padding, padding, padding);

    }

}
