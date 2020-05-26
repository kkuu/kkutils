package utils.kkutils.ui.lunbo;

/**
 * Created by kk on 2017/3/23.
 */

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.ImgTool;
import utils.kkutils.JsonTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LayoutInflaterTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.ViewTool;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKImageView;
import utils.kkutils.ui.bigimage.PinchImageView;

/***
 * 用于轮播的
 *
 */
public class LunBoTool {
    public static final int maxCount = 3000;
    public static final int beginPosition = maxCount / 2;
    static final int key = ViewTool.initKey();



    public static void initAds(final View parent, final int vg_lunbo_content, final int vg_lunbo_btns, final int lunbo_dot_layout_resid, final int cb_lunbo_dot, final int autoPlayDuration, List<LunBoData> lunBoDatas) {
        try {
            ViewPager adsContainer = (ViewPager) parent.findViewById(vg_lunbo_content);
            LinearLayout vg_viewpager_btn = (LinearLayout) parent.findViewById(vg_lunbo_btns);
            initAds(adsContainer, vg_viewpager_btn, lunbo_dot_layout_resid, cb_lunbo_dot, autoPlayDuration, lunBoDatas, false);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void initAds(final View parent, final int vg_lunbo_content, final int vg_lunbo_btns, final int lunbo_dot_layout_resid, final int cb_lunbo_dot, final int autoPlayDuration, List<LunBoData> lunBoDatas,boolean isLoop,boolean imageCanScale) {
        try {
            ViewPager adsContainer = (ViewPager) parent.findViewById(vg_lunbo_content);
            LinearLayout vg_viewpager_btn = (LinearLayout) parent.findViewById(vg_lunbo_btns);
            initAds(adsContainer, vg_viewpager_btn, lunbo_dot_layout_resid, cb_lunbo_dot, autoPlayDuration, lunBoDatas, isLoop,imageCanScale,null);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    /***
     * 查看大图用的
     * @param parent
     * @param vg_lunbo_content
     * @param vg_lunbo_btns
     * @param lunbo_dot_layout_resid
     * @param cb_lunbo_dot
     * @param autoPlayDuration
     * @param lunBoDatas
     */
    public static void initAdsBigImage(final View parent, final int vg_lunbo_content, final int vg_lunbo_btns, final int lunbo_dot_layout_resid, final int cb_lunbo_dot, final int autoPlayDuration, List<LunBoData> lunBoDatas) {
        try {
            ViewPager adsContainer = (ViewPager) parent.findViewById(vg_lunbo_content);
            LinearLayout vg_viewpager_btn = (LinearLayout) parent.findViewById(vg_lunbo_btns);
            initAds(adsContainer, vg_viewpager_btn, lunbo_dot_layout_resid, cb_lunbo_dot, 0, lunBoDatas, true);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     *
     * @param adsContainer                  ViewPager   放轮播图片的
     * @param vg_viewpager_btn              LinearLayout   放轮播图片下面的 小红点的
     * @param cb_lunbo_dot     cb_lunbo_dot  小红点里面 的CompoundButton id ， 这个用于显示当前选中的
     * @param lunBoDatas                    轮播数据
     * @param autoPlayDuration              自动播放间隔时间， 大于0 并且 有超过1页的数据就自动播放，
     *
     * @param  imageCanScale 查看大图用的
     *
     */
    public static void initAds(final ViewPager adsContainer, final LinearLayout vg_viewpager_btn, final int lunbo_dot_layout_resid, final int cb_lunbo_dot, final int autoPlayDuration, List<LunBoData> lunBoDatas, final boolean imageCanScale) {

        initAds(adsContainer,vg_viewpager_btn,lunbo_dot_layout_resid,cb_lunbo_dot,autoPlayDuration,lunBoDatas,!imageCanScale,imageCanScale,null);

    }
    /***
     *
     * @param adsContainer                  ViewPager   放轮播图片的
     * @param vg_viewpager_btn              LinearLayout   放轮播图片下面的 小红点的
     * @param cb_lunbo_dot     cb_lunbo_dot  小红点里面 的CompoundButton id ， 这个用于显示当前选中的
     * @param lunBoDatas                    轮播数据
     * @param autoPlayDuration              自动播放间隔时间， 大于0 并且 有超过1页的数据就自动播放，
     *
     * @param  imageCanScale 查看大图用的
     *
     */
    public static void initAds(final ViewPager adsContainer, final LinearLayout vg_viewpager_btn, final int lunbo_dot_layout_resid, final int cb_lunbo_dot, final int autoPlayDuration, List<LunBoData> lunBoDatas,boolean isLoop, final boolean imageCanScale,LunBoToolGetView lunBoToolGetView) {
        try {
            if (lunBoDatas == null) lunBoDatas = new ArrayList<>();
            if (lunBoDatas.size() < 1) {
                lunBoDatas.add(new LunBoData("",null));
            }
            if(lunBoToolGetView==null)lunBoToolGetView=LunBoToolGetView.getDefault();
            final List<LunBoData> datasList = lunBoDatas;

            adsContainer.setOffscreenPageLimit(1);
            /**
             * 添加底部小点
             */
            {
                String data = JsonTool.toJsonStr(lunBoDatas);
                String oldData = "" + adsContainer.getTag();
                if (oldData.equals(data)) {
                    return;
                } else {
                    adsContainer.setTag(data);
                }
                vg_viewpager_btn.removeAllViews();
                if(lunBoDatas.size()>1){
                    for (int i = 0; i < lunBoDatas.size(); i++) {
                        View dotBtn = LayoutInflaterTool.getInflater(20, lunbo_dot_layout_resid).inflate();
                        vg_viewpager_btn.addView(dotBtn);
                    }
                }

            }

            LunBoToolGetView finalLunBoToolGetView = lunBoToolGetView;
            final PagerAdapter adAdapter = new PagerAdapter() {
                @Override
                public int getCount() {
                    if (datasList == null) return 0;
                    if (datasList.size() == 1) return 1;//1个的时候禁止无限滑动
                    if (!isLoop) return datasList.size();
                    return maxCount;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int positionIn) {
                    if (isLoop) {
                        positionIn = CommonTool.loopPosition(datasList.size(), beginPosition, positionIn);
                    }
                    final LunBoData lunBoData = datasList.get(positionIn);
                    View view = finalLunBoToolGetView.getView(container, lunBoData, positionIn, isLoop, imageCanScale);
                    if(view.getParent()==null){
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
            {//设置自动轮播
                if (autoPlayDuration > 0 && datasList.size() > 1) {
                    if (ViewTool.getTag(adsContainer, key) == null) {
                        Runnable runnableSwitch = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (adsContainer.isAttachedToWindow()) {//必须要在界面中才执行
                                        Object runableObj = ViewTool.getTag(adsContainer, key);
                                        if (this.equals(runableObj)) {
                                            if (adsContainer.isShown()) {
                                                int count = adAdapter.getCount();
                                                int current = adsContainer.getCurrentItem();
                                                current++;
                                                if (current >= count) {
                                                    current = 0;
                                                }
                                                adsContainer.setCurrentItem(current);
                                            }
                                            adsContainer.postDelayed(this, autoPlayDuration);
                                            return;
                                        }
                                    }
                                } catch (Exception e) {
                                    LogTool.ex(e);
                                }
                            }
                        };
                        ViewTool.setTag(adsContainer, runnableSwitch, key);
                        adsContainer.postDelayed(runnableSwitch, autoPlayDuration);
                    }
                }

            }

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int positionIn) {
                    try {
                        if (isLoop)
                            positionIn = CommonTool.loopPosition(datasList.size(), beginPosition, positionIn);
                        for (int i = 0; i < vg_viewpager_btn.getChildCount(); i++) {
                            CompoundButton rb = (CompoundButton) vg_viewpager_btn.getChildAt(i).findViewById(cb_lunbo_dot);
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
            };
            adsContainer.addOnPageChangeListener(onPageChangeListener);
            if(isLoop){
                adsContainer.setCurrentItem(beginPosition, false);
            }else {
                adsContainer.setCurrentItem(0, false);
            }
            onPageChangeListener.onPageSelected(adsContainer.getCurrentItem());

        } catch (Exception e) {
            LogTool.ex(e);
        }

    }
    public static class LunBoData {
        public Object imageUrl = "";
        public LunBoClickListener lunBoClickListener;

        public LunBoData(Object imageUrl) {
            this.imageUrl =  imageUrl;
        }

        public LunBoData(Object imageUrl, LunBoClickListener lunBoClickListener) {
            this.imageUrl =  imageUrl;
            this.lunBoClickListener = lunBoClickListener;
        }

        public static List<LunBoData> getTest() {
            List<LunBoData> lunBoDatas = new ArrayList<>();
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(1)));
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(2)));
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(3)));
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(4)));
            return lunBoDatas;
        }

        public static List<LunBoData> initData(Object... strings) {
            List<LunBoData> lunBoDatas = new ArrayList<>();
            for (Object str : strings) {
                lunBoDatas.add(new LunBoData( str));
            }
            return lunBoDatas;
        }
    }

    public static abstract class LunBoClickListener extends KKViewOnclickListener {

        public abstract void onClickLunBo(View v, LunBoData lunBoData);

        @Override
        public void onClickKK(View v) {

        }
    }
    public static interface LunBoToolGetView{
        public View getView(ViewGroup container,LunBoData lunBoData,int position,boolean isLoop,boolean imageCanScale);
        public static LunBoToolGetView getDefault(){
            return new LunBoToolGetView() {
                @Override
                public View getView(ViewGroup container, LunBoData lunBoData, int position, boolean isLoop, boolean imageCanScale) {
                    ImageView imageView = new KKImageView(container.getContext());//不能用 curractivity
                    if (imageCanScale) {
                        imageView = new PinchImageView(container.getContext());
                    }
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ImgTool.loadImage(lunBoData.imageUrl, imageView);
                    if(lunBoData.lunBoClickListener!=null){
                        imageView.setOnClickListener(new KKViewOnclickListener() {
                            @Override
                            public void onClickKK(View v) {
                                if (lunBoData.lunBoClickListener != null){
                                    lunBoData.lunBoClickListener.onClickLunBo(v, lunBoData);
                                }
                            }
                        });
                    }
                    return imageView;
                }
            };
        }
    }

}
