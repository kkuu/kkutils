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
