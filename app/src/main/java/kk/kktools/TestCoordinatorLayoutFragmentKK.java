package kk.kktools;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;

public class TestCoordinatorLayoutFragmentKK extends KKParentFragment {
    KKSimpleRecycleView recycler_view;
    TextView btn_choose_dizhi;
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_coordinatorlayout_demo;
    }

    @Override
    public void initData() {
        recycler_view.setNestedScrollingEnabled(true);
        recycler_view.setData(TestData.getTestStrList(30), R.layout.activity_main_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
            }
        });
        btn_choose_dizhi.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                new KK_XuanZheShouHuoDiZhiFragment().goForResult(TestCoordinatorLayoutFragmentKK.this,1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(data!=null&&data.getExtras()!=null){
                KK_XuanZheShouHuoDiZhiFragment.DiZhiChoose diZhiChoose = KK_XuanZheShouHuoDiZhiFragment.getDiZhiByIntent(data);
                UiTool.setTextView(btn_choose_dizhi, diZhiChoose.address_province+" "+diZhiChoose.address_city+" "+diZhiChoose.address_area);
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    @Override
    public void initListener() {

    }
}
