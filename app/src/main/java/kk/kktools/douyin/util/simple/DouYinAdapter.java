package kk.kktools.douyin.util.simple;


import android.view.View;
import android.view.ViewGroup;

import com.dueeeke.videoplayer.player.VideoView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kk.kktools.douyin.util.TikTokController;
import kk.kktools.douyin.util.cache.PreloadManager;
import utils.kkutils.AppTool;


/**
 * Created by castorflex on 12/29/13.
 * Just a copy of the original ViewPager modified to support vertical Scrolling
 */
public class DouYinAdapter extends RecyclerView.Adapter {
    List<VideoBean> mVideoList;
    public DouYinAdapter(List<VideoBean> mVideoList){
        if(mVideoList==null)mVideoList=new ArrayList<>();
        this.mVideoList=mVideoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoView videoView=new VideoView(AppTool.getApplication());
        videoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        DouYinViewHolder douYinViewHolder = new DouYinViewHolder(videoView);
        douYinViewHolder.setVideoController(newController());
        return douYinViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DouYinViewHolder viewHolder= (DouYinViewHolder) holder;
        VideoBean item = mVideoList.get(position);
        viewHolder.mTikTokController.initData(item);
        holder.itemView.setTag(position);


        //开始预加载
        PreloadManager.getInstance(AppTool.getApplication()).addPreloadTask(item.getUrl(), position);

    }

    /***
     * 可复写这个 改变
     * @return
     */
    public TikTokController newController(){
        return new TikTokController(AppTool.getApplication());
    }
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    /**
     * 借鉴ListView item复用方法
     */
    public static class DouYinViewHolder extends RecyclerView.ViewHolder {
        public TikTokController mTikTokController;
        public VideoView mVideoView;

        DouYinViewHolder(View itemView) {
            super(itemView);
            mVideoView = (VideoView) itemView;
            mVideoView.setLooping(true);
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        }


        public void setVideoController(TikTokController newController) {
            mVideoView.setVideoController(newController);
            this.mTikTokController=newController;
        }
    }
}