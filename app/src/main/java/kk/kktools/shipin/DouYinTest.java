package kk.kktools.shipin;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.FixedPreloadSizeProvider;
import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kk.kktools.R;
import utils.kkutils.common.LogTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.KKSimpleRecycleView;

public class DouYinTest extends KKParentFragment {
    public KKSimpleRecycleView recycler_view;
    public VideoView mVideoView;
    public  TikTokController mTikTokController;
    public List<VideoBean> tikTokVideoList;

    @Override
    public int initContentViewId() {
        return R.layout.kk_douyin_fragment;
    }

    @Override
    public void initData() {
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)
                .setPlayerFactory(IjkPlayerFactory.create(getContext()))
//                .setPlayerFactory(ExoMediaPlayerFactory.create(this))
//                .setAutoRotate(true)
//                .setEnableMediaCodec(true)
//                .setUsingSurfaceView(true)
//                .setEnableParallelPlay(true)
//                .setEnableAudioFocus(false)
//                .setScreenScale(VideoView.SCREEN_SCALE_MATCH_PARENT)
                .build());

        tikTokVideoList = getTikTokVideoList();
        mVideoView = new VideoView(getContext());
        mVideoView.setLooping(true);
        mTikTokController = new TikTokController(getContext());
        mVideoView.setVideoController(mTikTokController);

        initList();
    }
    private int mCurrentPosition;
    public int w=0;
    public int h=0;
    public void initList(){
        recycler_view.isOnCreateViewUseRootView=true;
        recycler_view.setItemViewCacheSize(10);
        ViewPagerLayoutManager viewPagerLayoutManager = new ViewPagerLayoutManager(getContext(), RecyclerView.VERTICAL);
        viewPagerLayoutManager.setOnViewPagerListener(new ViewPagerLayoutManager.OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第一条
                startPlay(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurrentPosition == position) {
                    mVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurrentPosition == position) return;
                startPlay(position);
                mCurrentPosition = position;
            }
        });
        recycler_view.setLayoutManager(viewPagerLayoutManager);
        recycler_view.setData(tikTokVideoList, R.layout.kk_douyin_fragment_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                w=recycler_view.getWidth();
                h=recycler_view.getHeight();
                VideoBean videoBean = tikTokVideoList.get(position);
                getImgBuilder("initData   "+ position,videoBean.getThumb())  .into((ImageView) itemView.findViewById(R.id.thumb));
            }
        });
    }
    private void startPlay(int position) {
        LogTool.s("startplay      "+position);
        View itemView=recycler_view.getChildAt(0);
        VideoBean videoBean=tikTokVideoList.get(position);
        FrameLayout frameLayout = itemView.findViewById(R.id.container);
        getImgBuilder("startplay",videoBean.getThumb()).into(mTikTokController.getThumb());
        ViewParent parent = mVideoView.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(mVideoView);
        }
        frameLayout.addView(mVideoView);
        mVideoView.setUrl(videoBean.getUrl());
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        mVideoView.start();
    }

    public RequestBuilder getImgBuilder(final String tag, String url){
       return Glide.with(getActivity())

                .addDefaultRequestListener(new RequestListener<Object>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Object> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target<Object> target, DataSource dataSource, boolean isFirstResource) {
                        LogTool.s(tag+"         "+resource+"  "+dataSource+"  "+ isFirstResource+"  "+model);
                        return false;
                    }
                })

                .load(url)
                .placeholder(android.R.color.black)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(w,h);
    }
    /**
     * 抖音演示数据
     */
    public static List<VideoBean> getTikTokVideoList() {
        List<VideoBean> videoList = new ArrayList<>();
        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4c87000639ab0f21c285.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bea0014e31708ecb03e.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bb500130248a3bcdad0.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b8300007d1906573584.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b61000b6a4187626dda.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4c87000639ab0f21c285.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bea0014e31708ecb03e.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bb500130248a3bcdad0.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b8300007d1906573584.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b61000b6a4187626dda.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4c87000639ab0f21c285.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bea0014e31708ecb03e.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bb500130248a3bcdad0.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b8300007d1906573584.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b61000b6a4187626dda.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0"));
        return videoList;
    }
    @Override
    public void initListener() {

    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.release();
    }


    @Override
    public boolean onBackPressed() {
        if (!mVideoView.onBackPressed()) {
            return super.onBackPressed();
        }
        return false;
    }
}
