package utils.wzutils.ui.lunbo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;

public class LunBoToolNormalView {
    public static void initAds(final ViewPager adsContainer, final LinearLayout vg_viewpager_btn, final int dotBtnLayoutResId, final int dotBtnCompoundButtonResId, final int count, final LunBoGetView lunBoGetView) {
        try {

            adsContainer.setOffscreenPageLimit(count);
            /**
             * 添加底部小点
             */
            {
                vg_viewpager_btn.removeAllViews();
                for (int i = 0; i < count; i++) {
                    View dotBtn = LayoutInflaterTool.getInflater(20, dotBtnLayoutResId).inflate();
                    vg_viewpager_btn.addView(dotBtn);
                }
            }
            final PagerAdapter adAdapter = new PagerAdapter() {
                @Override
                public int getCount() {
                    return LunBoTool.maxCount;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int positionIn) {
                    positionIn= CommonTool.loopPosition(count, LunBoTool.beginPosition,positionIn);
                    View view=null;
                    if(lunBoGetView!=null){
                        view=lunBoGetView.getView(positionIn);
                        container.addView(view);
                    }
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    try {
                        container.removeView((View) object);
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            };
            adsContainer.setAdapter(adAdapter);

            adsContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int positionIn) {
                    try {
                        positionIn= CommonTool.loopPosition(count, LunBoTool.beginPosition,positionIn);
                        for (int i = 0; i < vg_viewpager_btn.getChildCount(); i++) {
                            CompoundButton rb = (CompoundButton) vg_viewpager_btn.getChildAt(i).findViewById(dotBtnCompoundButtonResId);
                            if (positionIn == i) {
                                rb.setChecked(true);
                            } else {
                                rb.setChecked(false);
                            }
                        }
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            adsContainer.setCurrentItem(LunBoTool.beginPosition,false);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static interface LunBoGetView{
        public View getView(int position);
    }

}
