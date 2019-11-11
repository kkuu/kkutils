//package douyin.util.recycleview;
//
//import android.view.View;
//import android.view.ViewParent;
//import android.widget.FrameLayout;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.RequestBuilder;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//import com.dueeeke.videoplayer.BuildConfig;
//import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
//import com.dueeeke.videoplayer.player.VideoView;
//import com.dueeeke.videoplayer.player.VideoViewConfig;
//import com.dueeeke.videoplayer.player.VideoViewManager;
//import com.tjy.hellovideo.R;
//
//import java.util.List;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//import douyin.DouYinController;
//import douyin.util.simple.DataUtil;
//import douyin.util.simple.VideoBean;
//import utils.kkutils.AppTool;
//import utils.kkutils.common.LogTool;
//import utils.kkutils.ui.KKSimpleRecycleView;
//
//public class DouYinRecycleViewTool {
//    public KKSimpleRecycleView recycler_view;
//    public VideoView mVideoView;
//    public  DouYinController mTikTokController;
//    public List<VideoBean> tikTokVideoList;
//
//    public int startPosition;
//    public void init(KKSimpleRecycleView kkSimpleRecycleView, int postion){
//        this.recycler_view=kkSimpleRecycleView;
//        this.startPosition=postion;
//
//
//        //播放器配置，注意：此为全局配置，按需开启
//        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
//                .setLogEnabled(BuildConfig.DEBUG)
//                .setPlayerFactory(IjkPlayerFactory.create())
//                .build());
//
//        tikTokVideoList = DataUtil.getTikTokVideoList();
//        mVideoView = new VideoView(AppTool.getApplication());
//        mVideoView.setLooping(true);
//        mTikTokController = new DouYinController(AppTool.getApplication());
//        mVideoView.setVideoController(mTikTokController);
//
//        initList();
//
//    }
//    public void initList(){
//        initLayoutManager();
//
//        recycler_view.setData(tikTokVideoList, R.layout.douyin_item, new KKSimpleRecycleView.KKRecycleAdapter() {
//            @Override
//            public void initData(int position, int type, View itemView) {
//                super.initData(position, type, itemView);
//                LogTool.s("initData  "+position);
//            }
//        });
//        recycler_view.scrollToPosition(startPosition);
//
//    }
//
//
//    public int mCurrentPosition;
//    public int w=0;
//    public int h=0;
//    public void initLayoutManager(){
//        recycler_view.isOnCreateViewUseRootView=true;
//        recycler_view.setItemViewCacheSize(10);
//        final RecycleViewViewPagerLayoutManager viewPagerLayoutManager = new RecycleViewViewPagerLayoutManager(AppTool.getApplication(), RecyclerView.VERTICAL);
//        viewPagerLayoutManager.setOnViewPagerListener(new RecycleViewViewPagerLayoutManager.OnViewPagerListener() {
//            @Override
//            public void onInitComplete() {
//                //自动播放第一条
//                startPlay(startPosition);
//            }
//
//            @Override
//            public void onPageRelease(boolean isNext, int position) {
//                if (mCurrentPosition == position) {
//                    mVideoView.release();
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position, boolean isBottom) {
//                if (mCurrentPosition == position) return;
//                startPlay(position);
//                mCurrentPosition = position;
//            }
//        });
//        recycler_view.setLayoutManager(viewPagerLayoutManager);
//
//    }
//
//
//    public void startPlay(int position) {
//        LogTool.s("startplay      "+position);
//        VideoBean videoBean=tikTokVideoList.get(position);
//        startPlay(videoBean.getUrl(),videoBean.getThumb());
//    }
//    public void startPlay(String videoUrl,String videoImg) {
//        try {
//            LogTool.s("开始播放视频 "+videoUrl);
//            View itemView=recycler_view.getChildAt(0);
//
//            FrameLayout frameLayout = itemView.findViewById(R.id.douyin_parent);
//
//            getImgBuilder("startplay",videoImg).into(mTikTokController.getThumb());
//            ViewParent parent = mVideoView.getParent();
//            if (parent instanceof FrameLayout) {
//                ((FrameLayout) parent).removeView(mVideoView);
//            }
//            frameLayout.addView(mVideoView);
//            mVideoView.setUrl(videoUrl);
//            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
//            mVideoView.start();
//        }catch (Exception e){
//            LogTool.ex(e);
//        }
//
//    }
//    public RequestBuilder getImgBuilder(final String tag, String url){
//        return Glide.with(AppTool.getApplication())
//                .addDefaultRequestListener(new RequestListener<Object>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Object> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Object resource, Object model, Target<Object> target, DataSource dataSource, boolean isFirstResource) {
//                        LogTool.s(tag+"         "+resource+"  "+dataSource+"  "+ isFirstResource+"  "+model);
//                        return false;
//                    }
//                })
//                .load(url)
//                .placeholder(android.R.color.black)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .override(w,h);
//    }
//
//    public void scrollToPosition(int position) {
//        recycler_view.scrollToPosition(position);
//        startPlay(position);
//    }
//}
