package kk.kktools.jinrong;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import kk.kktools.R;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.MathTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;

public class JiSuanQi extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_jisuanqi;
    }


    @Override
    public void initData() {

        initLiXi();
    }
    TextView btn_lixi_jisuan,tv_lixi_money,tv_lixi_lilv,tv_lixi_time,tv_lixi_jieguo;
    public void initLiXi(){

        btn_lixi_jisuan.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                double v1 = MathTool.computeNianLiXi(Double.valueOf(tv_lixi_money.getText().toString()), Double.valueOf(tv_lixi_lilv.getText().toString()), Integer.valueOf(tv_lixi_time.getText().toString()));
                UiTool.setTextView(tv_lixi_jieguo,""+v1);
            }
        });
    }

    @Override
    public void initListener() {

    }
}
