package kk.kktools.tupian_xuanze;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.takephoto.TakePhotoFragment;
import utils.kkutils.ui.takephoto.TakePhotoSimpleFragment;

public class KKChooseImgFragmentKK extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_choose_img_fragment;
    }

    @Override
    public void initData() {

        TakePhotoSimpleFragment.initChooseMedia(getChildFragmentManager(),parent);
    }
    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> selectPhotos = TakePhotoSimpleFragment.getSelectPhotos(getChildFragmentManager());
        CommonTool.showToast(selectPhotos);
    }



}
