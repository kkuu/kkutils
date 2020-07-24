package kk.kktools;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.lunbo.LunBoTool;

public class TestLunBo extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_lunbo;
    }

    @Override
    public void initData() {

        LunBoTool.initAds(parent,R.id.vg_lunbo_content,R.id.vg_lunbo_btns,R.layout.kk_lunbo_item,R.id.cb_lunbo_dot,-1, LunBoTool.LunBoData.getTest(),true,false);
    }


}
