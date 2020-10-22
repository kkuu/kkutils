package kk.kktools.shipin;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.List;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.progress.KKProgressYuan;
import utils.kkutils.ui.takephoto.TakeMediaTool;
import utils.kkutils.ui.video.douyin2.library.cache.PreloadManager;
import utils.kkutils.ui.video.douyin2.views.KVideoView;
import utils.kkutils.ui.video.douyin2.views.KVideoViewDouble;
import utils.kkutils.ui.video.douyin2.views.KVideoViewNormal;

public class TestShiPin extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_shipin;
    }

    KVideoViewDouble kvideo_left;
    ImageView imgv_video;
    View btn_choose,btn_rotain;
    @Override
    public void initData() {
        getActivityKK().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StatusBarTool.setStatusBarColor(getActivity(),0,true,true);
        getActivityKK().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        kvideo_left.setCopyImageView(imgv_video);


//        String url="http://vfx.mtime.cn/Video/2020/07/23/mp4/200723104850444893_1080.mp4";
//        PreloadManager.getInstance(getContext()).addPreloadTask(url, 0);
//        kvideo_left.setUrl(PreloadManager.getInstance(getContext()).getPlayUrl(url));
//        kvideo_left.start();



        btn_choose.setOnClickListener(new KKViewOnclickListener(){
            @Override
            public void onClickKK(View v) {
                TakeMediaTool.pick(TestShiPin.this, TakeMediaTool.PickType.video, 1, false, null, new TakeMediaTool.OnImageChooseListener() {
                    @Override
                    public void onImageChoose(List<String> resultList) {
                        kvideo_left.setUrl(resultList.get(0));
                        kvideo_left.start();
                    }
                });
            }
        });
        btn_rotain.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
               if(imgv_video.getRotation()==0){
                   imgv_video.setRotation(90);
               }
               else {
                   imgv_video.setRotation(0);
               }
            }
        });


    }


}
