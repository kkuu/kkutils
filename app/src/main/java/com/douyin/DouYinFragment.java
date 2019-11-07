package com.douyin;


import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.douyin.util.simple.DataUtil;
import com.douyin.util.simple.DouYinAdapter;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.StatusBarTool;

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
        StatusBarTool.setStatusBarColor(getActivity(),getResources().getColor(R.color.kk_tv_h0),true,false);
        CommonTool.setSoftInputAdjustPan(getActivity());

        initDouYin();

    }
    public void initDouYin(){
        //核心就这一句，自定义UI注意重写DouYinController
        new DouYinAdapter(getLifecycle(),relativeLayout, DataUtil.getTikTokVideoList()){
            @Override
            public DouYinController newController() {
                return super.newController();
            }

            @Override
            public void onPageSelectedImp(int position) {
                super.onPageSelectedImp(position);
            }
        };
    }
    @Override
    public void initListener() {

    }




}
