package kk.kktools.douyin;


import android.view.View;

import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.dueeeke.videoplayer.util.L;


import java.util.List;

import androidx.viewpager2.widget.ViewPager2;
import kk.kktools.R;
import kk.kktools.douyin.util.DataUtil;
import kk.kktools.douyin.util.DouYinAdapter;
import kk.kktools.douyin.util.VideoBean;
import kk.kktools.douyin.util.cache.PreloadManager;
import kk.kktools.douyin.util.cache.ProxyVideoCacheManager;
import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.StatusBarTool;

public class DouYinFragment extends KKParentFragment {


    @Override
    public int initContentViewId() {
        return R.layout.kk_douyin_fragment;
    }

    @Override
    public void initData() {
        StatusBarTool.setStatusBarColor(getActivity(),getResources().getColor(R.color.kk_tv_h0),true,false);
        CommonTool.setSoftInputAdjustPan(getActivity());


        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)
                .setPlayerFactory(IjkPlayerFactory.create())
                //               .setPlayerFactory(ExoMediaPlayerFactory.create())
//                .setEnableOrientation(true)
//                .setEnableMediaCodec(true)
//                .setUsingSurfaceView(true)
//                .setEnableParallelPlay(true)
//                .setEnableAudioFocus(false)
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                .build());

        mVideoList = DataUtil.getTikTokVideoList();
        initViewPager();
        mPreloadManager = PreloadManager.getInstance(getContext());

    }


    @Override
    public void initListener() {

    }



    private int mCurrentPosition;
    private int mPlayingPosition;
    private List<VideoBean> mVideoList;
    private ViewPager2 mViewPager;

    private PreloadManager mPreloadManager;
    /**
     * VerticalViewPager是否反向滑动
     */
    private boolean mIsReverseScroll;



    private void initViewPager() {
        mViewPager = parent.findViewById(R.id.kk_view_pager2);
        mViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mViewPager.setOffscreenPageLimit(4);

        mViewPager.setAdapter(new DouYinAdapter(mVideoList));

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

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                startPlay(0);
            }
        });


    }

    private void startPlay(int position) {
        {
            View itemView = mViewPager.findViewWithTag(position);
            VideoView videoView = itemView.findViewById(R.id.kk_video_view);
            VideoBean tiktokBean = mVideoList.get(position);
            String playUrl = mPreloadManager.getPlayUrl(tiktokBean.getUrl());
            L.i("startPlay: " + "position: " + position + "  url: " + playUrl);
            videoView.setUrl(playUrl);
            videoView.start();
            mPlayingPosition = position;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        VideoViewManager.instance().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoViewManager.instance().resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VideoViewManager.instance().release();
        mPreloadManager.removeAllPreloadTask();

        //清除缓存，实际使用可以不需要清除，这里为了方便测试
       // ProxyVideoCacheManager.clearAllCache(getContext());
    }


}
