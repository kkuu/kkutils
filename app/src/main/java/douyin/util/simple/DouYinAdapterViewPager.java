package douyin.util.simple;


import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;


/**
 *使用ViewPager2 实现，setCurrentPosition 暂时定位不准确
 */
@Deprecated
public class DouYinAdapterViewPager extends DouYinAdapterParent {

    public DouYinAdapterViewPager(Lifecycle lifecycle, ViewGroup container, List<VideoBean> mVideoList) {
        super(lifecycle, container, mVideoList);
    }
    public ViewPager2 mViewPager;
    @Override
    public void initViewsImp() {
        {
            ViewPager2 mViewPager = new ViewPager2(container.getContext());
            mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            container.addView(mViewPager);


            mViewPager.setAdapter(this);
            mViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            mViewPager.setOffscreenPageLimit(1);
            mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
            mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                    if (position > mPlayingPosition) {
                        mIsReverseScroll = false;
                    } else if (position < mPlayingPosition) {
                        mIsReverseScroll = true;
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    mCurrentPosition = position;
                    if (position == mPlayingPosition) return;
                    startPlay(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);

                    if (mCurrentPosition == mPlayingPosition) return;
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        mPreloadManager.resumePreload(mCurrentPosition, mIsReverseScroll);
                    } else {
                        mPreloadManager.pausePreload(mCurrentPosition, mIsReverseScroll);
                    }
                }
            });
        }
    }

    @Override
    public void setCurrentPosition(int position) {
        mViewPager.setCurrentItem(position);
    }


}