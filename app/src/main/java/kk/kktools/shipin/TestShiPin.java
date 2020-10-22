package kk.kktools.shipin;

import android.graphics.Color;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.progress.KKProgressYuan;
import utils.kkutils.ui.video.douyin2.library.cache.PreloadManager;
import utils.kkutils.ui.video.douyin2.views.KVideoView;
import utils.kkutils.ui.video.douyin2.views.KVideoViewNormal;

public class TestShiPin extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_shipin;
    }

    KVideoViewNormal kvideo_left,kvideo_right;
    @Override
    public void initData() {

        String url="http://vfx.mtime.cn/Video/2020/07/23/mp4/200723104850444893_1080.mp4";
        PreloadManager.getInstance(getContext()).addPreloadTask(url, 0);

        kvideo_left.setUrl(url);
        kvideo_right.setUrl(url);



        kvideo_left.start();
        kvideo_right.start();


        kvideo_left.postDelayed(new Runnable() {
            @Override
            public void run() {
                kvideo_left.seekTo(100);
                kvideo_right.seekTo(100);
            }
        },1000);

    }


}
