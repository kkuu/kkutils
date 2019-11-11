package douyin;


import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import douyin.util.simple.DataUtil;
import douyin.util.simple.DouYinAdapterViewPager;
import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.StatusBarTool;

/***
 * 直接复制当前包到项目 根 包下面
 */
public class DouYinFragment extends KKParentFragment {



    @Override
    public int initContentViewId() {
        return 0 ;
    }
    RelativeLayout relativeLayout;
    @Override
    public View initContentView() {
        relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return relativeLayout;
    }

    @Override
    public void initData() {
        StatusBarTool.setStatusBarColor(getActivity(),Color.BLACK,true,false);
        CommonTool.setSoftInputAdjustPan(getActivity());

        initDouYin();

    }
    public void initDouYin(){
        //核心就这一句，自定义UI注意重写DouYinController
        new DouYinAdapterViewPager(getLifecycle(),relativeLayout, DataUtil.getTikTokVideoList()){
            @Override
            public DouYinController newController() {
                return super.newController();
            }

        };
    }
    @Override
    public void initListener() {

    }




}
