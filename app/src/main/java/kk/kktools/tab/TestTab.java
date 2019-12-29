package kk.kktools.tab;

import android.graphics.Color;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.xutils.db.table.TableEntity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import kk.kktools.R;
import kk.kktools.tuya.TestEditView;
import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.tablayout.KTabNormalLayout;

public class TestTab extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_test_tab;
    }
    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    KTabNormalLayout tab_guanzhu;
    CommonTabLayout tl_8;
    SlidingTabLayout tl_10;
    SegmentTabLayout tl_5;
    @Override
    public void initData() {
        for (String title : mTitles) {
//            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
//            mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }
        for (int i = 0; i < mTitles.length; i++) {
            final int finalI = i;
            mTabEntities.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return mTitles[finalI];
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
        }


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
        initDefaut();


    }
    ViewPager view_pager;
    public void initDefaut(){
        view_pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return new TestEditView();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return ""+position;
            }
        });
        tl_8.setTabData(mTabEntities);
        tl_10.setViewPager(view_pager);
        tl_5.setTabData(mTitles);
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
