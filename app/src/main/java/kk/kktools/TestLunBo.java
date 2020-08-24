package kk.kktools;

import android.widget.ImageView;

import com.dueeeke.videocontroller.StandardVideoController;

import utils.kkutils.ImgTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.lunbo.LunBoTool;
import utils.kkutils.ui.video.douyin2.views.KVideoView;

public class TestLunBo extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_lunbo;
    }

    @Override
    public void initData() {

        LunBoTool.initAds(parent,R.id.vg_lunbo_content,R.id.vg_lunbo_btns,R.layout.kk_lunbo_item,R.id.cb_lunbo_dot,-1, LunBoTool.LunBoData.getTest(),true,false);


        String url="https://qupinpin.img.hdianzan.com/images/goods/goods/media/image/202008130638566233.mp4";

       // video.setUrl(url);
//        video.setVideoController(new StandardVideoController(getContext()));
//      //  video.start();
//        KVideoControlViewNormal  videoControl=new KVideoControlViewNormal(getContext());
//        video.getController().addControlComponent(videoControl);
//
//
//        videoControl.post(new Runnable() {
//            @Override
//            public void run() {
//                videoControl.preLoad(""+ImgTool.convertByAliYun(url,videoControl.getWidth(),0),url,0);
//            }
//        });

    }
    KVideoView video;


}
