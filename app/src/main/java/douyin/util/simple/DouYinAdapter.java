package douyin.util.simple;


import android.view.View;
import android.view.ViewGroup;

import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import douyin.DouYinController;
import douyin.util.cache.PreloadManager;
import utils.kkutils.AppTool;
import utils.kkutils.common.LogTool;


/**
 * Created by castorflex on 12/29/13.
 * Just a copy of the original ViewPager modified to support vertical Scrolling
 */
public class DouYinAdapter extends RecyclerView.Adapter {
    public List<VideoBean> mVideoList;
    public ViewPager2 mViewPager;
    public PreloadManager mPreloadManager;
    public int mPlayingPosition;
    public int mCurrentPosition;
    /**
     * VerticalViewPager是否反向滑动
     */
    public boolean mIsReverseScroll;
    public Lifecycle lifecycle;
    public DouYinAdapter(Lifecycle lifecycle, ViewGroup container, List<VideoBean> mVideoList) {
        if (mVideoList == null) mVideoList = new ArrayList<>();
        this.mVideoList = mVideoList;
        mPreloadManager = PreloadManager.getInstance();
        this.lifecycle=lifecycle;

        {
            this.mViewPager = new ViewPager2(container.getContext());
            this.mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            container.addView(mViewPager);
        }

        init();
    }
    public void initLife(){
        lifecycle.addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if(event== Lifecycle.Event.ON_RESUME){
                    VideoViewManager.instance().resume();
                }
                if(event== Lifecycle.Event.ON_PAUSE){
                    VideoViewManager.instance().pause();
                }
                if(event== Lifecycle.Event.ON_DESTROY){
                    VideoViewManager.instance().release();
                    PreloadManager.getInstance().removeAllPreloadTask();
                }
            }
        });
    }
    public void init() {
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)
                .setPlayerFactory(IjkPlayerFactory.create())
                .build());

        initLife();


        mViewPager.setAdapter(this);
        mViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mViewPager.setOffscreenPageLimit(2);
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
                onPageSelectedImp(position);
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

    /***
     * 可复写这个 改变
     * @return
     */
    public DouYinController newController() {
        return new DouYinController(AppTool.getApplication());
    }

    public void onPageSelectedImp(int position) {
        startPlay(position);
    }

    public void startPlay(int position) {
        {
            VideoView videoView = mViewPager.findViewWithTag(position);
            VideoBean tiktokBean = mVideoList.get(position);
            String playUrl = mPreloadManager.getPlayUrl(tiktokBean.getUrl());
            LogTool.s("startPlay: " + "position: " + position + "  url: " + playUrl);
            videoView.setUrl(playUrl);
            videoView.start();
            mPlayingPosition = position;
        }
    }

















    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoView videoView = new VideoView(AppTool.getApplication());
        videoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        DouYinViewHolder douYinViewHolder = new DouYinViewHolder(videoView);
        douYinViewHolder.setVideoController(newController());
        return douYinViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DouYinViewHolder viewHolder = (DouYinViewHolder) holder;
        VideoBean item = mVideoList.get(position);
        viewHolder.mTikTokController.initData(item);
        holder.itemView.setTag(position);
        //开始预加载
        mPreloadManager.addPreloadTask(item.getUrl(), position);
    }
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
    /**
     * 借鉴ListView item复用方法
     */
    public static class DouYinViewHolder extends RecyclerView.ViewHolder {
        public DouYinController mTikTokController;
        public VideoView mVideoView;

        DouYinViewHolder(View itemView) {
            super(itemView);
            mVideoView = (VideoView) itemView;
            mVideoView.setLooping(true);
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        }
        public void setVideoController(DouYinController newController) {
            mVideoView.setVideoController(newController);
            this.mTikTokController = newController;
        }
    }
}