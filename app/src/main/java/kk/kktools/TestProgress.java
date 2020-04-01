package kk.kktools;

import android.graphics.Color;

import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.lunbo.LunBoTool;
import utils.kkutils.ui.progress.KKProgressYuan;

public class TestProgress extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_pregress;
    }
    KKProgressYuan progress_yuan;

    @Override
    public void initData() {

        progress_yuan.setProgColor(Color.parseColor("#67FFA6"));
        progress_yuan.setBackColor(Color.parseColor("#4c67FFA6"));
        progress_yuan.setBackWidth(CommonTool.dip2px(10));
        progress_yuan.setProgWidth(CommonTool.dip2px(10));

        progress_yuan.setAngleStart(90+44);
        progress_yuan.setAngleEnd(90-44);
        progress_yuan.setProgress(10);
    }

    @Override
    public void initListener() {

    }
}
