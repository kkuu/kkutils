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

        new ChouJiang9Tool(grid_view) {
            @Override
            public boolean onChouJiangClick() {
//                startChoujiang(3,3);
                startChoujiangRandom(0);
                return true;
            }

            @Override
            public ChouJiangViewInterface newView() {
                return new ChouJiang9Tool.ChouJiangViewDefault(getContext());
            }
        };


    }

    @Override
    public void initListener() {

    }
}
