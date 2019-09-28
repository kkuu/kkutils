package utils.kkutils.ui.tablayout;

import android.content.Context;
import android.graphics.Color;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;


/***
 *
 * <tjyutils.ui.KTabLayout
 *             android:id="@+id/tab"
 *             android:layout_width="match_parent"
 *             android:layout_height="40dp"
 *             app:tl_tab_space_equal="true"
 *             ></tjyutils.ui.KTabLayout>
 *
 *         <utils.wzutils.ui.WzViewPager
 *             android:id="@+id/viewPager"
 *             android:layout_width="match_parent"
 *             android:layout_height="match_parent"></utils.wzutils.ui.WzViewPager>
 *
 *
 */
public class KTabLayout extends SlidingTabLayout {
    public static final int TEXT_BOLD_NONE = 0;
    public static final int TEXT_BOLD_WHEN_SELECT = 1;
    public static final int TEXT_BOLD_BOTH = 2;

    public KTabLayout(Context context) {
        super(context);
        init();
    }

    public KTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setIndicatorWidthEqualTitle(true);
        setIndicatorHeight(2);
        setTextsize(15);
        setTabSpaceEqual(true);

        setTextSelectColor(Color.parseColor("#E2231A"));
        setTextUnselectColor(Color.parseColor("#333333"));
        setIndicatorColor(Color.parseColor("#E2231A"));

        notifyDataSetChanged();//很重要，设置样式后都需要调用这个才能修改
    }

    @Override
    public void setCurrentTab(int currentTab, boolean smoothScroll) {
        super.setCurrentTab(currentTab, smoothScroll);
        onPageSelected(currentTab);//这样才可以 加粗选中
    }

    @Override
    public void setCurrentTab(int currentTab) {
        super.setCurrentTab(currentTab);
        onPageSelected(currentTab);////这样才可以 加粗选中
    }

    public void initViewPager(FragmentManager fragmentManager, ViewPager viewPager, final List<Fragment> fragments, List<String> titles) {
        initViewPager(null,fragmentManager,viewPager,fragments,titles.toArray(new String[0]));
    }
    public void initViewPager(FragmentManager fragmentManager, ViewPager viewPager, final List<Fragment> fragments, String ...titles) {
       initViewPager(null,fragmentManager,viewPager,fragments,titles);
    }


    public void initViewPager(KTabLaoutBuilder kTabLaoutBuilder,FragmentManager fragmentManager, ViewPager viewPager, final List<Fragment> fragments, String ...titles) {
        if(kTabLaoutBuilder!=null){
            kTabLaoutBuilder.build(this);
        }
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        setViewPager(viewPager, titles);
    }




    public static abstract class KTabLaoutBuilder{
        public abstract void build(KTabLayout tabLayout);
    }
}
