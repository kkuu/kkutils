//package utils.kkutils.ui.media;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//
//import com.dueeeke.videocontroller.StandardVideoController;
//import com.dueeeke.videoplayer.player.IjkVideoView;
//
//import utils.kkutils.AppTool;
//import utils.kkutils.ImgTool;
//import utils.kkutils.common.CommonTool;
//import utils.kkutils.common.TestData;
//import utils.kkutils.common.UiTool;
//import utils.kkutils.parent.KKParentActivity;
//
//public class KKVideoPlayer extends KKParentActivity {
//    IjkVideoView ijkVideoView;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        ijkVideoView=new IjkVideoView(this);
//        setContentView(ijkVideoView);
//        initMedia("测试电影","https://blz-videos.nosdn.127.net/1/HearthStone/f6cd63b590d416821d3e27e0.mp4", TestData.getTestImgUrl(1));
//    }
//    public void initMedia(String name, String url, String image){
//        {
//            //String url="https://blz-videos.nosdn.127.net/1/HearthStone/f6cd63b590d416821d3e27e0.mp4";
//            int h=1080* CommonTool.getWindowSize().x/1920;
//            UiTool.setWH(ijkVideoView,-100,h);
//            ijkVideoView.setUrl(url); //设置视频地址
//            StandardVideoController controller = new StandardVideoController(this);
//            controller.setTitle(name); //设置视频标题
//            ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
//            ImgTool.loadImage(image, controller.getThumb());
//            ijkVideoView.start(); //开始播放，不调用则不自动播放
//        }
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        ijkVideoView.pause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        ijkVideoView.resume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ijkVideoView.release();
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        if (!ijkVideoView.onBackPressed()) {
//            super.onBackPressed();
//        }
//    }
//    public static void go(){
//        Intent intent=new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setClass(AppTool.getApplication(),KKVideoPlayer.class);
//        AppTool.getApplication().startActivity(intent);
//    }
//}
