package kk.kktools;

import android.view.View;

import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.KKGridView;
import utils.kkutils.ui.choujiang.ChouJiang9Tool;

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
            public boolean onChouJiangClick(View view) {
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


}
