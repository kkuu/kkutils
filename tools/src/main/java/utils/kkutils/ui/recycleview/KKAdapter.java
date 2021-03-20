package utils.kkutils.ui.recycleview;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OVER_SCROLL_NEVER;

public class KKAdapter extends RecyclerView.Adapter {
    public List dataList = new ArrayList();
    public List<KKItemProvider> itemProviderList = new ArrayList<>();
    public RecyclerView recyclerView;

    public KKAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        setHasStableIds(true);
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /***
     * 用于标志这个RecyclerView 已经设置了 KKAdapter 这种adapter了 直接返回就行了
     */
    static int keyAdapterTag = 100 << 24;

    public static KKAdapter bind(RecyclerView recyclerView) {
        Object tag = recyclerView.getTag(keyAdapterTag);
        if (tag != null && tag instanceof KKAdapter) {
            ((KKAdapter) tag).setDataList(null);
            ((KKAdapter) tag).itemProviderList.clear();
            return (KKAdapter) tag;
        }
        return new KKAdapter(recyclerView);
    }

    protected void setAdapter() {
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }
        //防止重复设置闪烁  重复设置layoutmanager 也会导致闪烁
        if (recyclerView.getAdapter() != this) {
            recyclerView.setAdapter(this);
            recyclerView.setTag(keyAdapterTag, this);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        KKItemProvider itemProviderByType = getItemProviderByType(viewType);
        if (itemProviderByType != null) {
            itemView = itemProviderByType.getView(parent);
        } else {
            itemView = emptyViewProvider.itemProviderEmpty.getView(parent);
        }
        if (itemView == null) itemView = new View(parent.getContext());
        return new RecyclerView.ViewHolder(itemView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            int itemViewType = getItemViewType(position);
            KKItemProvider itemProviderByType = getItemProviderByType(itemViewType);
            if (itemProviderByType != null)
                itemProviderByType.bind(holder, getDataList().get(position), position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataList().size()) {
            Object o = getDataList().get(position);
            if (o == null) return -1;
            for (KKItemProvider kkItemProvider : itemProviderList) {
                if (kkItemProvider.isThisType(getDataList(), o, position)) {
                    return kkItemProvider.layout();
                }
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return getDataList().size() > 0 ? getDataList().size() : 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List getDataList() {
        if (dataList == null) dataList = new ArrayList();
        return dataList;
    }

    public KKAdapter setDataList(List dataList) {
        if (dataList == null) dataList = new ArrayList();
        this.dataList = dataList;

        setAdapter();
        notifyDataSetChanged();
        return this;
    }

    public KKAdapter addItemProvider(KKItemProvider... itemProviders) {
        if (itemProviders != null) {
            for (KKItemProvider itemProvider : itemProviders) {
                itemProviderList.add(itemProvider);
            }
        }
        notifyDataSetChanged();
        return this;
    }

    public KKItemProvider getItemProviderByType(int type) {
        for (KKItemProvider kkItemProvider : itemProviderList) {
            if (type == kkItemProvider.layout()) {
                return kkItemProvider;
            }
        }
        return null;
    }


    EmptyViewProvider emptyViewProvider = new EmptyViewProvider();

    public KKAdapter setEmptyView(View view) {
        emptyViewProvider.setEmptyView(view);
        return this;
    }

    public KKAdapter setEmptyView(int layoutId) {
        emptyViewProvider.setEmptyView(layoutId);
        return this;
    }

    public static void setEmptyViewGlobal(View view) {
        EmptyViewProvider.setEmptyViewGlobal(view);
    }

    public static void setEmptyViewGlobal(int layoutId) {
        EmptyViewProvider.setEmptyViewGlobal(layoutId);
    }

    /**
     * 去掉重复设置，免得闪烁
     *
     * @param layoutManager
     * @return
     */
    public KKAdapter setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        RecyclerView.LayoutManager old = recyclerView.getLayoutManager();
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(recyclerView.getContext());

        if (old == null || !old.getClass().equals(layoutManager.getClass())) {
            recyclerView.setLayoutManager(layoutManager);
            return this;
        }


        if (layoutManager instanceof GridLayoutManager && old instanceof GridLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            boolean reverseLayout = ((LinearLayoutManager) layoutManager).getReverseLayout();
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();

            int orientation1 = ((LinearLayoutManager) old).getOrientation();
            boolean reverseLayout1 = ((LinearLayoutManager) old).getReverseLayout();
            int spanCount1 = ((GridLayoutManager) old).getSpanCount();

            if (orientation != orientation1 || reverseLayout != reverseLayout1 || spanCount != spanCount1) {
                recyclerView.setLayoutManager(layoutManager);
            }
            return this;

        }

        if (layoutManager instanceof StaggeredGridLayoutManager && old instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            int spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();

            int orientation1 = ((StaggeredGridLayoutManager) old).getOrientation();
            int spanCount1 = ((StaggeredGridLayoutManager) old).getSpanCount();

            if (orientation != orientation1 || spanCount != spanCount1) {
                recyclerView.setLayoutManager(layoutManager);
            }
            return this;
        }


        if (layoutManager instanceof LinearLayoutManager && old instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            boolean reverseLayout = ((LinearLayoutManager) layoutManager).getReverseLayout();
            int orientation1 = ((LinearLayoutManager) old).getOrientation();
            boolean reverseLayout1 = ((LinearLayoutManager) old).getReverseLayout();
            if (orientation != orientation1 || reverseLayout != reverseLayout1) {
                recyclerView.setLayoutManager(layoutManager);
            }
            return this;
        }


        LogUtils.v("两次layoutManager一样，不设置了");
        return this;
    }

    public static abstract class KKItemProvider<T> {
        public abstract int layout();

        public abstract boolean isThisType(List dataList, Object data, int position);

        public abstract void bind(RecyclerView.ViewHolder holder, T data, int position);

        public View getView(ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(layout(), parent, false);
        }
    }

    public static abstract class KKItemSingleProvider<T> extends KKItemProvider<T> {
        public boolean isThisType(List dataList, Object data, int position) {
            return true;
        }
    }

    public static class EmptyViewProvider {
        /***
         * 全局的,默认的空布局
         */
        public static KKItemProvider itemProviderEmptyGlobal = getSimple(null, -1);
        public KKItemProvider itemProviderEmpty = itemProviderEmptyGlobal;

        public static void setEmptyViewGlobal(View view) {
            itemProviderEmptyGlobal = getSimple(view, -1);
        }

        public void setEmptyView(View view) {
            itemProviderEmpty = getSimple(view, -1);
        }

        public static void setEmptyViewGlobal(int layoutId) {
            itemProviderEmptyGlobal = getSimple(null, layoutId);
        }

        public void setEmptyView(int layoutId) {
            itemProviderEmpty = getSimple(null, layoutId);
        }

        public static KKItemProvider getSimple(View view, int layoutId) {
            return new KKItemProvider() {
                @Override
                public int layout() {
                    return 0;
                }

                @Override
                public boolean isThisType(List dataList, Object data, int position) {
                    return false;
                }

                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                @Override
                public View getView(ViewGroup parent) {
                    {//用设置的view
                        if (view != null) {
                            if (view.getParent() != null) {
                                ViewGroup viewGroup = (ViewGroup) view.getParent();
                                viewGroup.removeView(view);
                            }
                            return view;
                        }
                    }


                    {//用设置的布局文件
                        if (layoutId > 0) {
                            try {
                                Activity activity = (Activity) parent.getContext();
                                View inflate = activity.getLayoutInflater().inflate(layoutId, parent, false);
                                return inflate;
                            } catch (Exception e) {
                                LogUtils.e(e);
                            }

                        }
                    }


                    //默认的空布局
                    View viewEmpty = new View(parent.getContext());
                    viewEmpty.setBackgroundColor(Color.WHITE);
                    viewEmpty.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    return viewEmpty;
                }

                @Override
                public void bind(RecyclerView.ViewHolder holder, Object data, int position) {

                }
            };
        }
    }

}
