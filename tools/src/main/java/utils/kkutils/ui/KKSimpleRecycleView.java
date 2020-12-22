package utils.kkutils.ui;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import utils.kkutils.common.CollectionsTool;
import utils.kkutils.common.LayoutInflaterTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ViewTool;
import utils.kkutils.parent.KKParentRecycleView;

/**
 * Created by ishare on 2016/6/7.
 * 主要是用于简化 列表操作的
 * <p>
 * recycle  如果加了 item type  好像效率不理想
 *
 *
 * 技巧：
 *
 * 1 . notifyItemChanged(position,"使用这个参数可以不经历清空数据再刷新，不会被删除再重新初始化，性能更高，(有视频的话不会重置播放状态)");
 *
 *
 */
public class KKSimpleRecycleView extends KKParentRecycleView {
    /***
     * 默认的空界面
     */
    public static int defaultEmptyResId;
    public AdapterRecycle adapter;
    public List datas = new ArrayList();
    public int[] types = new int[0];
    public int[] viewsResId = new int[0];
    public KKRecycleAdapter kkRecycleAdapter;
    public Class<WzViewHolder> holderClass;

    public static boolean inflateViewUseParentDefault=true;//是否inflate 用parentView; 临时兼容老项目的，以后一直是true 全局的，
    public boolean inflateViewUseParent=inflateViewUseParentDefault;//部分的用这个改

    /***
     * 设置 缓存的 layout 的个数， 和recyle cache 不一样， LayoutInflaterTool 这个用的
     */
    public int layoutCacheCount = 20;

    public KKSimpleRecycleView(Context context) {
        super(context);
    }

    public KKSimpleRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KKSimpleRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getLayoutCacheCount() {
        return layoutCacheCount;
    }

    public void setLayoutCacheCount(int layoutCacheCount) {
        this.layoutCacheCount = layoutCacheCount;
    }

    @Override
    public void init() {
        super.init();
        setOverScrollMode(OVER_SCROLL_NEVER);
        //setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false)); //这个在父类里面设置了
        adapter = new AdapterRecycle(this);
        setAdapter(adapter);
    }

    public static int[] getTypes(int... type) {
        return type;
    }


    public void setData(List datas, int resId, KKRecycleAdapter KKRecycleAdapter) {
        setData(datas, null, resId, KKRecycleAdapter);
    }

    public void setData(List datas, int[] viewsResId, KKRecycleAdapter KKRecycleAdapter) {
        setDataImp(datas, null, viewsResId, viewsResId, KKRecycleAdapter);
    }

    @Deprecated
    public void setData(List datas, int[] types, int[] viewsResId, KKRecycleAdapter KKRecycleAdapter) {
        setDataImp(datas, null, types, viewsResId, KKRecycleAdapter);
    }

    public void setData(List datas, Class<WzViewHolder> holderClass, int resId, KKRecycleAdapter KKRecycleAdapter) {
        types = new int[]{0};
        viewsResId = new int[]{resId};
        setDataImp(datas, holderClass, types, viewsResId, KKRecycleAdapter);
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if(layoutManager!=layoutManagerEmpty){
            layoutManagerNotEmpty=layoutManager;
        }
    }
    public LayoutManager layoutManagerNotEmpty;
    public LayoutManager layoutManagerEmpty;
    public void checkEmpty(){
        {//检查是否空
            if(CollectionsTool.isEmptyList(datas)){
                if(layoutManagerEmpty==null)layoutManagerEmpty=new LinearLayoutManager(getContext(),VERTICAL,false);
                setLayoutManager(layoutManagerEmpty);
            }else {
                if(layoutManagerNotEmpty!=null)setLayoutManager(layoutManagerNotEmpty);
            }

        }
    }
    protected void setDataImp(List datas, Class<WzViewHolder> holderClass, int[] types, int[] viewsResId, KKRecycleAdapter KKRecycleAdapter) {
        if (getEmptyView() == null) setEmptyResId(defaultEmptyResId);


        if (datas == null) datas = new ArrayList<>();
        this.datas = datas;
        {//检查是否空
           checkEmpty();
        }

        if (types != null && viewsResId != null && types.length == viewsResId.length) {
            this.types = types;
            this.viewsResId = viewsResId;
        }
        this.holderClass = holderClass;
        if (KKRecycleAdapter != null) this.kkRecycleAdapter = KKRecycleAdapter;
//        adapter.notifyDataSetChanged();
        this.adapter.notifyItemRangeChanged(0,datas.size());
    }

    public void addData(int position, Object data) {
        this.datas.add(position, data);
        adapter.notifyItemInserted(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
    }

    public void addDataList(int position, List dataList) {
        if (CollectionsTool.NotEmptyList(dataList)) {
            this.datas.addAll(position, dataList);
            adapter.notifyItemRangeInserted(position, dataList.size());
            adapter.notifyItemRangeChanged(0, position);//刷新前面的
            adapter.notifyItemRangeChanged(position + dataList.size(), datas.size() - dataList.size());//刷新后面的
        }
    }


    public void setEmptyResId(int emptyResId) {
        try {
            if (emptyResId != 0) {
                setEmptyView(LayoutInflaterTool.getInflater(3, emptyResId).inflate());
            } else {
                setEmptyView(null);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void setEmptyView(View emptyView) {
        adapter.emptyView = emptyView;
    }

    @Override
    public View getEmptyView() {
        return adapter.emptyView;
    }

    public static class AdapterRecycle extends Adapter {
        public static final int emptyType = -999;
        public View emptyView;
        KKSimpleRecycleView wzRecycleView;

        public AdapterRecycle(KKSimpleRecycleView wzRecycleView) {
            this.wzRecycleView = wzRecycleView;
            setHasStableIds(true);//getItemId  配合这个 ，保证 同一个position  使用同一个 view ， 这样就可以防止刷新的时候闪烁。。 goole 是坑，不知道默认返回position
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int resId = 0;
            View itemView = null;
            if (emptyView != null && emptyType == viewType) {//显示空控件
                //UiTool.setWH(emptyView, wzRecycleView.getMeasuredWidth(), wzRecycleView.getMeasuredHeight());
                itemView = emptyView;
            } else {
                for (int i = 0; i < wzRecycleView.types.length; i++) {
                    if (viewType == wzRecycleView.types[i]) {
                        resId = wzRecycleView.viewsResId[i];
                    }
                }

                if (wzRecycleView.getLayoutCacheCount() > 0) {
                    itemView = LayoutInflaterTool.getInflater(wzRecycleView.getLayoutCacheCount(), resId).inflate(wzRecycleView.inflateViewUseParent?parent:null);
                } else {
                    itemView = LayoutInflater.from(wzRecycleView.getContext()).inflate(resId, wzRecycleView.inflateViewUseParent?parent:null,false);
                }

            }

            WzViewHolder holder = null;
            if (wzRecycleView.holderClass != null) {
                try {
                    holder = (WzViewHolder) wzRecycleView.holderClass.getConstructor(KKSimpleRecycleView.class, View.class).newInstance(wzRecycleView, itemView);
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
            if (holder == null) holder = new WzViewHolder(wzRecycleView, itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof WzViewHolder) {
                ((WzViewHolder) holder).initData(position);
            }
        }

        @Override
        public int getItemCount() {
            if (emptyView != null && wzRecycleView.datas.size() == 0) {//如果当前要显示空的控件并且当前数据就是空的， 那么就返回1
                return 1;
            }
            return wzRecycleView.datas.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (emptyView != null && wzRecycleView.datas.size() == 0) {//如果当前要显示空的控件并且当前数据就是空的， 那么就返回 空控件
                return emptyType;
            }
            return wzRecycleView.kkRecycleAdapter.getItemViewType(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


    public static class WzViewHolder extends ViewHolder implements Serializable {
        public static final int keyHolderTag = 1;
        KKSimpleRecycleView recycleView;

        public WzViewHolder(KKSimpleRecycleView recycleView, View itemView) {
            super(itemView);
            this.recycleView = recycleView;
            ViewTool.setTag(itemView, this, keyHolderTag);
            ViewTool.initViews(itemView, this, null);
        }

        public void initData(int position) {
            try {
                int type = recycleView.getAdapter().getItemViewType(position);
                if (recycleView.kkRecycleAdapter.isEmptyType(type)) {
                    recycleView.kkRecycleAdapter.initEmptyData(position, type, itemView);
                } else {
                    recycleView.kkRecycleAdapter.initData(position, type, itemView);
                    recycleView.kkRecycleAdapter.initData(position, type, itemView, this);
                }
            } catch (Exception e) {
                LogTool.ex(e);
            }
        }
    }

    public static abstract class KKRecycleAdapter {
        public int getItemViewType(int position) {
            return 0;
        }

        public void initData(int position, int type, View itemView) {
        }

        public void initData(int position, int type, View itemView, WzViewHolder wzViewHolder) {

        }

        /***
         * 初始化 空数据显示
         * @param position
         * @param type
         * @param itemView
         */
        public void initEmptyData(int position, int type, View itemView) {

        }

        public boolean isEmptyType(int type) {
            return type == AdapterRecycle.emptyType;
        }
    }
}
