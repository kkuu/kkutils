package douyin.util.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.player.VideoView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import douyin.DouYinController;
import douyin.util.cache.PreloadManager;
import douyin.util.simple.VideoBean;

public class DouYinViewPagerAdapter extends PagerAdapter {

    /**
     * View缓存池，从ViewPager中移除的item将会存到这里面，用来复用
     */
    private List<View> mViewPool = new ArrayList<>();

    /**
     * 数据源
     */
    private List<VideoBean> mVideoBeans;

    public DouYinViewPagerAdapter(List<VideoBean> videoBeans) {
        this.mVideoBeans = videoBeans;
    }

    @Override
    public int getCount() {
        return mVideoBeans == null ? 0 : mVideoBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Context context = container.getContext();
        View view = null;
        if (mViewPool.size() > 0) {//取第一个进行复用
            view = mViewPool.get(0);
            mViewPool.remove(0);
        }

        ViewHolder viewHolder;
        if (view == null) {
            view = new VideoView(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        VideoBean item = mVideoBeans.get(position);
        //开始预加载
        PreloadManager.getInstance().addPreloadTask(item.getUrl(), position);
        Glide.with(context)
                .load(item.getThumb())
                .placeholder(android.R.color.white)
                .into(viewHolder.mThumb);
        viewHolder.mPosition = position;
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View itemView = (View) object;
        container.removeView(itemView);
        VideoBean item = mVideoBeans.get(position);
        //取消预加载
        PreloadManager.getInstance().removePreloadTask(item.getUrl());
        //保存起来用来复用
        mViewPool.add(itemView);
    }

    /**
     * 借鉴ListView item复用方法
     */
    public static class ViewHolder {

        public DouYinController mTikTokController;
        public VideoView mVideoView;
        public int mPosition;
        public ImageView mThumb;//封面图

        ViewHolder(View itemView) {
            mVideoView = (VideoView) itemView;
            mVideoView.setLooping(true);
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
            mTikTokController = new DouYinController(itemView.getContext());
            mVideoView.setVideoController(mTikTokController);

            mTikTokController.initView();
            mThumb = mTikTokController.getThumb();

            itemView.setTag(this);
        }
    }
}
