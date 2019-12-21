package kk.kktools.tab;

import android.graphics.Color;

import com.flyco.tablayout.listener.OnTabSelectListener;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.tablayout.KTabNormalLayout;

public class TestTab extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_test_tab;
    }

    KTabNormalLayout tab_guanzhu;
    @Override
    public void initData() {

        initTabEqual(tab_guanzhu);
        tab_guanzhu.setTabData(-1,"关注","粉丝");
        tab_guanzhu.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                CommonTool.showToast("选中了"+position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /***
     * 等距 铺满
     * @param tab
     */
    public static void initTabEqual(KTabNormalLayout tab) {
        tab.setIndicatorHeight(2);
        tab.setTextsize(13);
        //  tab.setTextBold(2);
        tab.setTabSpaceEqual(true);//这个属性好像只能在xml 里面设置
        tab.setTextSelectColor(Color.parseColor("#ffffff"));
        tab.setTextUnselectColor(Color.parseColor("#666666"));
        tab.setIndicatorColor(Color.parseColor("#F3CE41"));
        tab.notifyDataSetChanged();
    }


    @Override
    public void initListener() {

    }
}
