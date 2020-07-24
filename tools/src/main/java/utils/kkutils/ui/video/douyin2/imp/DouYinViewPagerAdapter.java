package utils.kkutils.ui.video.douyin2.imp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.ui.video.douyin2.library.cache.PreloadManager;
import utils.kkutils.ui.video.douyin2.views.KVideoControlView;


public abstract class DouYinViewPagerAdapter<T extends DouYinData> extends PagerAdapter {

    /**
     * View缓存池，从ViewPager中移除的item将会存到这里面，用来复用
     */
    private List<View> mViewPool = new ArrayList<>();

    public List<T> getDatas() {
        return datas;
    }

    /**
     * 数据源
     */
    public List<T> datas;

    public DouYinViewPagerAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        DouYinData item = datas.get(position);
        Context context = container.getContext();
        View view = null;
        if (mViewPool.size() > 0) {//取第一个进行复用
            view = mViewPool.get(0);
            mViewPool.remove(0);
        }


        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(getLayoutId(), container, false);
            viewHolder = new ViewHolder(view);
            viewHolder.controlView=view.findViewById(getControlViewId());
            viewHolder.mPlayerContainer=view.findViewById(getPlayerContainerId());
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        initItem(viewHolder,item,position);


        //开始预加载
        viewHolder.controlView.preLoad(item.getCoverImgUrl(),item.getVideoDownloadUrl(), position);
        viewHolder.mPosition = position;
        container.addView(view);
        return view;
    }


    public abstract void initItem(ViewHolder viewHolder,DouYinData douYinData,int position);
    public abstract int getLayoutId();
    public abstract  int getControlViewId();
    public abstract  int getPlayerContainerId();


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View itemView = (View) object;
        container.removeView(itemView);
        DouYinData item = datas.get(position);
        //取消预加载
        PreloadManager.getInstance(container.getContext()).removePreloadTask(item.getVideoDownloadUrl());
        //保存起来用来复用
        mViewPool.add(itemView);
    }
    /**
     * 借鉴ListView item复用方法
     */
    public static class ViewHolder {
        public int mPosition;
        public KVideoControlView controlView;
        public FrameLayout mPlayerContainer;
        public View itemView;
        ViewHolder(View itemView) {
            this.itemView=itemView;
            itemView.setTag(this);
        }
    }
}
