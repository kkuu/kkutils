package kk.kktools;

import android.graphics.Color;

import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.KKGridView;
import utils.kkutils.ui.choujiang.ChouJiang9Tool;
import utils.kkutils.ui.progress.KKProgressYuan;

public class TestChouJiang extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_choujiang;
    }
    KKGridView grid_view;

    @Override
    public void initData() {

        ChouJiang9Tool.initChouJiang(grid_view, new ChouJiang9Tool.ChouJiangViewFactory() {
            @Override
            public ChouJiang9Tool.ChouJiangViewInterface newView() {
                return new ChouJiang9Tool.ChouJiangViewDefault(getContext());
            }
        });

    }

    @Override
    public void initListener() {

    }
}
