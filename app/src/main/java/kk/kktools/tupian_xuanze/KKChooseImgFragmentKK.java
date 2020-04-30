package kk.kktools.tupian_xuanze;

import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kk.kktools.R;
import utils.kkutils.ImgTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.takephoto.TakeMediaTool;
import utils.kkutils.ui.takephoto.TakePhotoSimpleFragment;

public class KKChooseImgFragmentKK extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_choose_img_fragment;
    }

    ImageView image_touxiang;
    @Override
    public void initData() {

        TakePhotoSimpleFragment.initChooseMedia(getChildFragmentManager(),parent);


        image_touxiang.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                TakeMediaTool.pick(KKChooseImgFragmentKK.this, TakeMediaTool.PickType.video, 1, false, null, new TakeMediaTool.OnImageChooseListener() {
                    @Override
                    public void onImageChoose(List<String> resultList) {
                        ImgTool.loadImage(resultList.get(0),image_touxiang);
                    }
                });
//                TakeMediaTool.pickSingleImg(KKChooseImgFragmentKK.this,false, new TakeMediaTool.OnImageChooseListener() {
//                    @Override
//                    public void onImageChoose(List<String> resultList) {
//                        ImgTool.loadImage(resultList.get(0),image_touxiang);
//                        LogTool.s("大小："+new File(resultList.get(0)).length());
//
//                    }
//                });

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> selectPhotos = TakePhotoSimpleFragment.getSelectPhotos(getChildFragmentManager());
        CommonTool.showToast(selectPhotos);
    }



}
