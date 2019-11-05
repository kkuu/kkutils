package kk.kktools.douyin.util;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dueeeke.videoplayer.player.VideoView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kk.kktools.R;
import kk.kktools.douyin.util.cache.PreloadManager;
import utils.kkutils.AppTool;
import utils.kkutils.ImgTool;


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
        View view = LayoutInflater.from(AppTool.getApplication()).inflate(R.layout.kk_douyin_fragment_item, parent,false);
        return new DouYinViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DouYinViewHolder viewHolder= (DouYinViewHolder) holder;
        VideoBean item = mVideoList.get(position);
        //开始预加载
        PreloadManager.getInstance(AppTool.getApplication()).addPreloadTask(item.getUrl(), position);
        ImgTool.loadImage(item.getThumb(),viewHolder.mThumb);
        viewHolder.mTitle.setText(item.getTitle());
        viewHolder.mPosition = position;
        holder.itemView.setTag(position);
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
        public int mPosition;
        public TextView mTitle;//标题
        public ImageView mThumb;//封面图

        DouYinViewHolder(View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(R.id.kk_video_view);
            mVideoView.setLooping(true);
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
            mTikTokController = new TikTokController(itemView.getContext());
            mVideoView.setVideoController(mTikTokController);
            mTitle = mTikTokController.findViewById(R.id.kk_tv_title);
            mThumb = mTikTokController.findViewById(R.id.kk_iv_thumb);
        }

    }
}