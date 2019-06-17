package kk.kktools;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import utils.wzutils.common.LogTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.wzutils.parent.ParentFragment;
import utils.wzutils.parent.KKViewOnclickListener;
import utils.wzutils.ui.KKSimpleRecycleView;

public class TestCoordinatorLayoutFragment extends ParentFragment {
    KKSimpleRecycleView recycler_view;
    TextView btn_choose_dizhi;
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_coordinatorlayout_demo;
    }

    @Override
    public void initData() {
        recycler_view.setNestedScrollingEnabled(true);
        recycler_view.setData(TestData.getTestStrList(30), R.layout.activity_main_item, new KKSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
            }
        });
        btn_choose_dizhi.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new KK_XuanZheShouHuoDiZhiFragment().goForResult(TestCoordinatorLayoutFragment.this,1);
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
