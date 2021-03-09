package utils.kkutils.ui.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }
    }

    public static KKAdapter bind(RecyclerView recyclerView) {
        return new KKAdapter(recyclerView);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType > 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        } else {
            itemView = new View(parent.getContext());
        }
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
        for (KKItemProvider kkItemProvider : itemProviderList) {
            if (kkItemProvider.isThisType(getDataList(), getDataList().get(position), position)) {
                return kkItemProvider.layout();
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public KKAdapter setDataList(List dataList) {
        this.dataList = dataList;
        recyclerView.setAdapter(this);
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

    public List getDataList() {
        if (dataList == null) dataList = new ArrayList();
        return dataList;
    }

    public KKItemProvider getItemProviderByType(int type) {
        for (KKItemProvider kkItemProvider : itemProviderList) {
            if (type == kkItemProvider.layout()) {
                return kkItemProvider;
            }
        }
        return null;
    }


    public static abstract class KKItemProvider {
        public abstract int layout();

        public abstract boolean isThisType(List dataList, Object data, int position);

        public abstract void bind(RecyclerView.ViewHolder holder, Object data, int position);
    }

    public static abstract class KKItemSingleProvider extends KKItemProvider {
        public boolean isThisType(List dataList, Object data, int position) {
            return true;
        }
    }
}
