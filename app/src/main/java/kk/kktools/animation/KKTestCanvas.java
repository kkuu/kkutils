package kk.kktools.animation;

import android.view.View;
import android.view.ViewGroup;

import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;

public class KKTestCanvas extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return 0;
    }


    @Override
    public View initContentView() {
        KKCanvasView kkCanvasView=new KKCanvasView(getContext());
        UiTool.setWH(kkCanvasView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return kkCanvasView;
    }

    @Override
    public void initData() {

    }
}
