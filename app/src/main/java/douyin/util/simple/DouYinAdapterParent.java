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
import douyin.DouYinController;
import douyin.util.cache.PreloadManager;
import utils.kkutils.AppTool;
import utils.kkutils.common.LogTool;


/**
 * Created by castorflex on 12/29/13.
 * Just a copy of the original ViewPager modified to support vertical Scrolling
 */
public abstract class DouYinAdapterParent extends RecyclerView.Adapter {
    public List<VideoBean> mVideoList;
    public PreloadManager mPreloadManager;
    public int mPlayingPosition;
    public int mCurrentPosition;
    public ViewGroup container;
    /**
     * VerticalViewPager是否反向滑动
     */
    public boolean mIsReverseScroll;
    public Lifecycle lifecycle;
    public DouYinAdapterParent(Lifecycle lifecycle, ViewGroup container, List<VideoBean> mVideoList) {
        if (mVideoList == null) mVideoList = new ArrayList<>();
        this.mVideoList = mVideoList;
        mPreloadManager = PreloadManager.getInstance();
        this.lifecycle=lifecycle;
        this.container=container;
        init();
    }
    public abstract void initViewsImp();
    public abstract void setCurrentPosition(int position);
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
        initViewsImp();

        container.post(new Runnable() {
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

    public void startPlay(int position) {
        {
            VideoView videoView = container.findViewWithTag(position);
            if(videoView==null){
                LogTool.s("播放失败没找到VideoView"+position);
                return;
            }
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
        LogTool.s("预加载："+position+"  "+item.getUrl());

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