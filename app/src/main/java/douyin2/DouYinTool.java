package douyin2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.dueeeke.videoplayer.render.IRenderView;
import com.dueeeke.videoplayer.render.RenderViewFactory;
import com.dueeeke.videoplayer.render.TextureRenderView;

import douyin2.library.cache.PreloadManager;
import douyin2.imp.DouYinData;
import douyin2.imp.DouYinViewPagerAdapter;
import douyin2.views.KVideoView;
import douyin2.library.TikTokRenderView;
import douyin2.library.VerticalViewPager;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;

public class DouYinTool {
    public Context context;
    public VerticalViewPager mViewPager;
    public int mCurPos=-1;
    public PreloadManager mPreloadManager;
    public KVideoView mVideoView;



    public DouYinTool(ViewGroup container,DouYinViewPagerAdapter adapter) {
        context = container.getContext();
        DouYinTool.init();
        mPreloadManager = PreloadManager.getInstance(context);
        mVideoView = getDouYinVideoView(context);


        {//初始化ViewPager
            VerticalViewPager mViewPager = new VerticalViewPager(container.getContext());
            container.addView(mViewPager);
            UiTool.setWH(mViewPager, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            initViewPager(mViewPager, adapter);
        }

    }



    public void startPlayPosition(int position){
        mViewPager.setCurrentItem(position);
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                startPlay(position);
            }
        });
    }



    protected void initViewPager(VerticalViewPager viewPager, PagerAdapter pagerAdapter) {

        mViewPager = viewPager;
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            private int mCurItem;
            /**
             * VerticalViewPager是否反向滑动
             */
            private boolean mIsReverseScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == mCurItem) {
                    return;
                }
                mIsReverseScroll = position < mCurItem;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mCurPos) return;
                startPlay(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                    mCurItem = mViewPager.getCurrentItem();
                }

                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurPos, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurPos, mIsReverseScroll);
                }
            }
        });
    }

    protected String getVideoUrl(int position) {
       return ( (DouYinData)((DouYinViewPagerAdapter)mViewPager.getAdapter()).getDatas().get(position)).getVideoDownloadUrl();
    }

    /***
     * 内部才调用这个
     * 外部用
     * @see #startPlayPosition
     * @param position
     */
    protected   void startPlay(int position) {
        String url=getVideoUrl(position);
        int count = mViewPager.getChildCount();
        for (int i = 0; i < count; i ++) {
            View itemView = mViewPager.getChildAt(i);
            DouYinViewPagerAdapter.ViewHolder viewHolder = (DouYinViewPagerAdapter.ViewHolder) itemView.getTag();
            if (viewHolder.mPosition == position) {
                mVideoView.release();
                ViewGroup parentView = UiTool.getParentView(mVideoView);
                if(parentView!=null) parentView    .removeView(mVideoView);

                String playUrl = PreloadManager.getInstance(mVideoView.getContext()).getPlayUrl(url);
                LogTool.s("startPlay: " + "position: " + position + "  url: " + playUrl);
                mVideoView.setUrl(playUrl);
                mVideoView.getController().addControlComponent(viewHolder.controlView, true);
                viewHolder.mPlayerContainer.addView(mVideoView, 0);
                mVideoView.start();
                mCurPos = position;
                break;
            }
        }
    }





    public static KVideoView getDouYinVideoView(Context context) {
        KVideoView mVideoView = new KVideoView(context);
        mVideoView.setLooping(true);
        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(new RenderViewFactory() {
            @Override
            public IRenderView createRenderView(Context context) {
                return new TikTokRenderView(new TextureRenderView(context));
            }
        });
//        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        BaseVideoController mController = new BaseVideoController(mVideoView.getContext()) {
            @Override
            protected int getLayoutId() {
                return 0;
            }
        };
        mVideoView.setVideoController(mController);
        return mVideoView;
    }

    public static void init() {
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)//调试的时候请打开日志，方便排错
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
//                .setEnableOrientation(true)
//                .setEnableAudioFocus(false)
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
//                .setAdaptCutout(false)
//                .setPlayOnMobileNetwork(true)
//                .setProgressManager(new ProgressManagerImpl())
                .build());
    }




}
