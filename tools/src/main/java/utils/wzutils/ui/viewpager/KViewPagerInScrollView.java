package utils.wzutils.ui.viewpager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/***
 *用于放在scrollview 中， 根据子控件自动 改变自身高度
 */
public class KViewPagerInScrollView extends ViewPager {
    public KViewPagerInScrollView(@NonNull Context context) {
        super(context);
        init();
    }

    public KViewPagerInScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init(){
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                initSelected(i);
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                initSelected(0);
            }
        });
    }
    public void initSelected(int i){
        View child = getChildAt(i);
        if(child==null)return;
        child.measure(MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE/2,MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int h = child.getMeasuredHeight();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        } else {
            layoutParams.height = h;
        }
        setLayoutParams(layoutParams);
    }


}
