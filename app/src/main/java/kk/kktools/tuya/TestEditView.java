package kk.kktools.tuya;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kk.kktools.R;
import utils.kkutils.common.SaoMaTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.pullrefresh.KKRefreshLayout;

public class TestEditView extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return 0;
    }

    @Override
    public View initContentView() {
        EditViewParent editViewParent = new EditViewParent(getContext());
        editViewParent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return editViewParent;
    }

    @Override
    public void initData() {



    }

    @Override
    public void initListener() {

    }

}
