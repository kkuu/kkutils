package kk.kktools.shipin;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.List;

import kk.kktools.R;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.takephoto.TakeMediaTool;
import utils.kkutils.ui.video.douyin2.views.KVideoViewDouble;

public class TestGsyplayer extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_shipin;
    }
    KKGsyVideo kkGsyVideo;
    @Override
    public View initContentView() {
         kkGsyVideo=new KKGsyVideo(getActivity());
        return kkGsyVideo;
    }
    String converImg="http://p3-dy.byteimg.com/large/tos-cn-p-0015/d5dff40c50d74376b8ad99305685571a_1575370621.jpeg?from=2563711402_large";
    String url="https://aweme.snssdk.com/aweme/v1/play/?video_id=v0200f570000bnj3usej5ugo4drennlg&line=0&ratio=540p&watermark=1&media_type=4&vr_type=0&improve_bitrate=0&logo_name=aweme";

    @Override
    public void initData() {
        StatusBarTool.setStatusBarColor(getActivity(),0,true,true);

        kkGsyVideo.initUrl(url,converImg);






    }


}
