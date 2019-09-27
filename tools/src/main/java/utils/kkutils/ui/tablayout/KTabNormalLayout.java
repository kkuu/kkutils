package utils.kkutils.ui.tablayout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;



/***
 *
 * <tjyutils.ui.KTabLayout
 *             android:id="@+id/tab"
 *             android:layout_width="match_parent"
 *             android:layout_height="40dp"
 *             app:tl_tab_space_equal="true"
 *             ></tjyutils.ui.KTabLayout>
 *

 *
 */
public class KTabNormalLayout extends CommonTabLayout {
    public KTabNormalLayout(Context context) {
        super(context);
        init();
    }

    public KTabNormalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KTabNormalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        //setIndicatorWidthEqualTitle(true);
        setIndicatorHeight(2);
        setTextsize(15);
        setTabSpaceEqual(false);

        setTextSelectColor(Color.parseColor("#E2231A"));
        setTextUnselectColor(Color.parseColor("#333333"));
        setIndicatorColor(Color.parseColor("#E2231A"));

        notifyDataSetChanged();//很重要，设置样式后都需要调用这个才能修改
    }

    /***
     * 不等距，不铺满的
     *      * <utils.kkutils.ui.tablayout.KTabNormalLayout
     *      *             android:id="@+id/c2c_tab_jiaoyi"
     *      *             android:layout_width="match_parent"
     *      *             android:layout_height="40dp"
     *      *             android:layout_marginLeft="-18dp"
     *      *             >
     *
     * @param tab
     */
    public static void initTab(KTabNormalLayout tab){
        tab.setTabData(0,"购买","出售","订单","发布");
        tab.setIndicatorHeight(4);
        tab.setIndicatorWidth(30);
        tab.setTextsize(18);
        tab.setTextBold(2);//都加粗
        tab.setTabPadding(20);//tab 间距， 需要靠左的话就设置 tab 的marginLeft=-20;
        tab.setTabSpaceEqual(false);

        tab.setTextSelectColor(Color.parseColor("#E2231A"));
        tab.setTextUnselectColor(Color.parseColor("#999999"));
        tab.setIndicatorColor(Color.parseColor("#E2231A"));
        tab.notifyDataSetChanged();
    }

    /***
     * 等距 铺满
     * @param tab
     */
    public static void initTabEqual(KTabNormalLayout tab){
        tab.setIndicatorHeight(4);
        tab.setTextsize(18);
        tab.setTextBold(2);
        tab.setTabSpaceEqual(true);//这个属性好像只能在xml 里面设置

        tab.setTextSelectColor(Color.parseColor("#E2231A"));
        tab.setTextUnselectColor(Color.parseColor("#999999"));
        tab.setIndicatorColor(Color.parseColor("#E2231A"));

        tab.notifyDataSetChanged();
    }












    public void setTabData(int tabWidthDp,String ...tabDatas){
        setTabWidth(tabWidthDp);
        ArrayList<CustomTabEntity> customTabEntities = new ArrayList<>();

        for(final String tab:tabDatas){
            customTabEntities.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return tab;
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
        setTabData(customTabEntities);
    }

}
