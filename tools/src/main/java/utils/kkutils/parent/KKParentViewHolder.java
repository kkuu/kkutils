package utils.kkutils.parent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;

import utils.kkutils.common.ViewTool;

/**
 * Created by coder on 15/12/30.
 */
public abstract class KKParentViewHolder extends RecyclerView.ViewHolder implements Serializable {
    public KKParentViewHolder(View itemView, View.OnClickListener onClickListener) {
        super(itemView);
        ViewTool.initViews(itemView, this, onClickListener);
    }

    public void initViews(View view) {

        ViewTool.initViews(view, this, null);
    }

    public void initViews(View view, View.OnClickListener onClickListener) {
        ViewTool.initViews(view, this, onClickListener);
    }

}
