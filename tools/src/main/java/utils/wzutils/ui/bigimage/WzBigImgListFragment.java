package utils.wzutils.ui.bigimage;

import android.graphics.Color;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.WindowManager;


import com.blankj.utilcode.util.BarUtils;

import java.util.ArrayList;
import java.util.List;

import utils.wzutils.R;
import utils.wzutils.parent.WzNormalFragmentActivity;
import utils.wzutils.parent.WzParentFragment;
import utils.wzutils.ui.lunbo.LunBoTool;

/**
 * abc on 2017/9/15.
 *
 * WzBigImgListFragment.init(R.layout.fragmentLayoutId,R.id.viewPagerResId,R.id.dotParentResId,R.layout.dotItemLayoutId,R.id.cb_dot);
 * 同轮播的初始化
 *
 */

public class WzBigImgListFragment extends WzParentFragment {
    List<String> stringArrayList = new ArrayList<>();
    int currentIndex;

    public static int fragmentLayoutId;
    public static int viewPagerResId;
    public  static int dotParentResId;
    public static int dotItemLayoutId;
    public  static int cb_dot;


    public static void init(int fragmentLayoutId,int viewPagerResId,int dotParentResId,int dotItemLayoutId,int cb_dot){
        WzBigImgListFragment.fragmentLayoutId = fragmentLayoutId;
        WzBigImgListFragment.viewPagerResId =viewPagerResId;
        WzBigImgListFragment.dotParentResId =dotParentResId;
        WzBigImgListFragment.dotItemLayoutId=dotItemLayoutId;
        WzBigImgListFragment.cb_dot=cb_dot;
    };

    static {
        WzBigImgListFragment.init(R.layout.kk_lunbo_layout, R.id.viewPagerResId,R.id.dotParentResId,R.layout.kk_lunbo_item,R.id.cb_dot);

    }

    @Override
    public int initContentViewId() {
        return fragmentLayoutId;
    }

    @Override
    public void initData() {
        parent.setBackgroundColor(Color.BLACK);
        getActivity(). getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BarUtils.setNavBarColor(getActivity(),Color.BLACK);

        if(getArguments()!=null){
            stringArrayList = getArguments().getStringArrayList("stringArrayList");
            currentIndex = getArguments().getInt("currentIndex", 0);
        }

        ArrayList<LunBoTool.LunBoData> lunBoDataArrayList=new ArrayList<>();
        for(String tem:stringArrayList){
            LunBoTool.LunBoData lunBoData=new LunBoTool.LunBoData(tem);
            lunBoDataArrayList.add(lunBoData);
        }

        LunBoTool.initAdsBigImage(parent, viewPagerResId, dotParentResId,dotItemLayoutId, cb_dot, 0, lunBoDataArrayList);
        ((ViewPager) parent.findViewById(viewPagerResId)).setCurrentItem(currentIndex);
    }
    @Override
    public void initListener() {

    }


    public void go(int currentIndex, String... src) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < src.length; i++) {
            stringArrayList.add(src[i]);
        }
        go(currentIndex, stringArrayList);
    }


    public void go(int currentIndex, List<String> stringArrayList) {
        ArrayList <String> arrayList=new ArrayList<>(stringArrayList);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("stringArrayList", arrayList);
        bundle.putInt("currentIndex", currentIndex);
        setArguments(bundle);
        new WzNormalFragmentActivity().go(this);
    }
}
