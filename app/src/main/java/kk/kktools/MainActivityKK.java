package kk.kktools;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.UtilsTransActivity;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import douyin.DouYinFragment;
import kk.kktools.accessibilityTest.AccessibilityUtil;
import kk.kktools.accessibilityTest.AutoQiangGouService;
import kk.kktools.animation.ActivityAnimation1;
import kk.kktools.animation.KKTestCanvas;
import kk.kktools.color.TestColorFragmentKK;
import kk.kktools.http.HttpOkGoConfig;
import kk.kktools.http.HttpOkGoTool;
import kk.kktools.http.KKCallBack;
import kk.kktools.jinrong.JiSuanQi;
import kk.kktools.p2p.Testp2pFragment;
import kk.kktools.recycleview_test.TestRecycleView;
import kk.kktools.shipin.TestGsyplayer;
import kk.kktools.shipin.TestShiPin;
import kk.kktools.shu.ShuJiaFragment;
import kk.kktools.tab.TestTab;
import kk.kktools.tongxunlu.UserRecordFragment;
import kk.kktools.tupian_xuanze.KKChooseImgFragmentKK;
import kk.kktools.tuya.TestEditView;
import kk.kktools.viewpager2.ViewPager2Test;
import kk.kktools.web.WebFragment;
import utils.kkutils.AppTool;
import utils.kkutils.HttpTool;
import utils.kkutils.common.BroadcastReceiverTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.FileTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.MathTool;
import utils.kkutils.common.PermissionTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.TimeTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.common.ViewTool;
import utils.kkutils.encypt.goolecode.GoogleCodeJava;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.jni.GoogleCode;
import utils.kkutils.parent.KKParentActivity;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.bigimage.KKBigImgListFragment;
import utils.kkutils.ui.daojishi.DaoJiShiTool;
import utils.kkutils.ui.dialog.DialogTool;
import utils.kkutils.ui.dialog.datetimedialog.KKDatePickerDialog;
import utils.kkutils.ui.recycleview.KKDecoration;
import utils.kkutils.ui.textview.KKTextToSpeech;
import utils.kkutils.ui.video.douyin2.DouYinFragment2;
import utils.kkutils.ui.webview.X5WebView;
import utils.kkutils.update.Version;
import utils.kkutils.zhifu.KKZhiFuUiTool;

public class MainActivityKK extends KKParentActivity {

    List<Object> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTool.init(getApplication(), true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewTool.initViews(getWindow().getDecorView(),this,null);



        testItems.clear();
        addItem("计算器", new JiSuanQi(), null);
        addItem("测试大图", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                new KKBigImgListFragment().go(0, TestData.getTestImgUrlList(2));
            }
        });
        addItem("测试canvas", new KKTestCanvas(), null);
        addItem("测试CoordinatorLayout,和选择收货地址", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
//                KKTextToSpeech.textToSpeech_after_jinbi("支付宝到账。101元");
                KKTextToSpeech.textToSpeech_after_jinbi("去拼拼到账 123456.0789元");

//                new TestCoordinatorLayoutFragmentKK().go();
            }
        });
        addItem("测试recycleView", new TestRecycleView(), null);
        addItem("测试视频播放", new TestRecycleView(), new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
//                play();
                new TestShiPin().go();
                // KKVideoPlayer.go();
            }
        });
        addItem("测试gsy视频播放", new TestRecycleView(), new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                new TestGsyplayer().go();
            }
        });

        addItem("测试调色板",null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                PaletteTest.test(MainActivityKK.this);
            }
        });
        addItem("测试抖音效果", new DouYinFragment(), null);
        addItem("测试抖音效果2", new DouYinFragment2(), null);
        addItem("viewpager2", new ViewPager2Test(), null);

        addItem("图片选择器", new KKChooseImgFragmentKK(), null);
        addItem("颜色选择器", new TestColorFragmentKK(), null);
        addItem("密码输入", new KKChooseImgFragmentKK(), new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                new KKZhiFuUiTool().showZhiFuMiMa(false, new KKZhiFuUiTool.ZhiFuInterface() {
                    @Override
                    public void zhifu(Dialog dialogZhiFuMiMa, String password, String captcha) {
                    }
                }, null, null).show();
            }
        });
        addItem("webview", WebFragment.byData("https://www.baidu.com","123"), null);
        addItem("tab", new TestTab(), null);
        addItem("图片编辑", new TestEditView(), null);
        addItem("轮播", new TestLunBo(), null);
        addItem("进度条", new TestProgress(), null);
        addItem("抽奖", new TestChouJiang(), null);
        addItem("通讯录", new UserRecordFragment(), null);
        addItem("拼多多", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                String url="https://mobile.yangkeduo.com/app.html?use_reload=1&launch_url=duo_coupon_landing.html%3Fgoods_id%3D95338609834%26pid%3D10421334_140080857%26cpsSign%3DCC_200509_10421334_140080857_ec5843bacb7bd87c61f810a79da659a4%26duoduo_type%3D2&campaign=ddjb&cid=launch_dl_force_";

                ShopTool.openUrl(url);

//                PermissionTool.test();



            }
        });
        addItem("日期选择", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {

                KKDatePickerDialog kkDatePickerDialog = new KKDatePickerDialog(getBaseContext());
                kkDatePickerDialog.setColorSelected(R.color.kk_tv_h1);
                kkDatePickerDialog.setColorNormal(R.color.kk_tv_h3);
                kkDatePickerDialog .show();


            }
        });
        addItem("内存使用", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                int [] tem=new int[1024*1024*10];
                list.add(tem);
                CommonTool.printMemory();


            }
        });
        addItem("jni",null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                String test=GoogleCode.stringFromJNI(1286874120,6,"1f53de416ca79895086c64a7a2e5818a");
                CommonTool.showToast("验证码"+test);

                String test2=GoogleCode.stringFromJNI(1286874600,8,"25971966bac6e7c0dedcf1082a6ed266");
                LogTool.s("验证码"+test2);
                String test3=GoogleCode.stringFromJNI((int) (System.currentTimeMillis()/1000),8,"25971966bac6222a大e7c0dedcf1082a6ed266");
                LogTool.s("验证码"+test3);


                LogTool.s("验证码java:"+ GoogleCodeJava.test());
            }
        });

        addItem("抢购",null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                AccessibilityUtil.checkSetting(MainActivityKK.this, AutoQiangGouService.class); // "辅助功能"设置
            }
        });

        addItem("okGo",null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                HttpOkGoConfig.initOkGo(getApplication());
                HttpOkGoTool.get("https://www.baidu.com", new KKCallBack<String>(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        super.onSuccess(response);
                        CommonTool.showToast(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonTool.showToast(response.getException());
                    }
                });
                HttpOkGoTool.downLoad("https://down.qq.com/qqweb/QQ_1/android_apk/Android_8.5.5.5105_537066978.apk", FileTool.getCacheDir("apk").getAbsolutePath(),"qq",new KKCallBack<File>(){
                    @Override
                    public void onSuccess(Response<File> response) {
                        super.onSuccess(response);
                        CommonTool.showToast("下载完成"+response.body().length());
                    }
                });
            }
        });

        addItem("p2p通信",new Testp2pFragment(), null);
        addItem("转场动画", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                ActivityAnimation1.go();
            }
        });


        btn_go_shujia.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                new ShuJiaFragment().go();
            }
        });

        ShuJiaFragment.preLoad(null);
        refresh();


        LogTool.s(DaoJiShiTool.getDaoJiShi_Tian(1232184793));

        LogTool.s(DaoJiShiTool.getDaoJiShi_Long(1232184793));

        LogTool.s(DaoJiShiTool.getDaoJiShi_Long(0));
//         List<String> urlsDefault=new ArrayList<String>(){
//            {
//                add("https://api.hellovideos.org");
//                add("https://api.95y8l.cn");
//                add("https://api.hellovideo.io");
//            }
//        };
//
//         loadData(urlsDefault);
//        testVersion();

        // testWeb();
    }



    View btn_go_shujia;

    public void play(){


        MediaPlayer mediaPlayer = MediaPlayer.create(AppTool.getApplication(), utils.kkutils.R.raw.jinbi_zhifubao);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                MediaPlayer mediaPlayer = MediaPlayer.create(AppTool.getApplication(), utils.kkutils.R.raw.a_daozhang);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        HashMap<String ,Integer> mapNumVoice=new HashMap<>();
                        mapNumVoice.put("零",R.raw.a_0);
                        mapNumVoice.put("一",R.raw.a_1);
                        mapNumVoice.put("二",R.raw.a_2);
                        mapNumVoice.put("三",R.raw.a_3);
                        mapNumVoice.put("四",R.raw.a_4);
                        mapNumVoice.put("五",R.raw.a_5);
                        mapNumVoice.put("六",R.raw.a_6);
                        mapNumVoice.put("七",R.raw.a_7);
                        mapNumVoice.put("八",R.raw.a_8);
                        mapNumVoice.put("九",R.raw.a_9);
                        mapNumVoice.put("十",R.raw.a_10);
                        mapNumVoice.put("百",R.raw.a_bai);
                        mapNumVoice.put("千",R.raw.a_qian);
                        mapNumVoice.put("万",R.raw.a_wan);
                        mapNumVoice.put("亿",R.raw.a_yi);
                        mapNumVoice.put("负",R.raw.a_fu);
                        mapNumVoice.put("点",R.raw.a_dian);
                        mapNumVoice.put("元",R.raw.a_yuan);

                        MathTool.NumberUtil.speak(49.01,"元",1,mapNumVoice);
                    }
                });
                mediaPlayer.start();


            }
        });
        mediaPlayer.start();



    }




    public static void loadData( final List<String>  urls){
        for( final String urlStr:urls){
            HttpTool.request(HttpRequest.url(urlStr+"/api/queryversion"), String.class, new HttpUiCallBack<String>() {
                @Override
                public void onSuccess(String data) {
                    LogTool.s(data);
                }
            });

        }
    }

















    public void testVersion(){
        Version version=new Version();
        version.versionCode=100;
        version.isHtml="0";
        version.isForce="0";
        version.updateUrl="https://qupinpin.img.hdianzan.com/zhugechao-app/download/android/qupinpin-app-11.apk";
        Version.checkUpDate(this,version,null);
    }

    public void testWeb() {
        //x5web 不支持x86
        X5WebView x5WebView = new X5WebView(this);
        ViewGroup parent = (ViewGroup) getWindow().getDecorView();
        parent.addView(x5WebView);
        UiTool.setWH(x5WebView, -1, -1);
        // x5WebView.loadUrl("https://blz-videos.nosdn.127.net/1/HearthStone/f6cd63b590d416821d3e27e0.mp4");
        x5WebView.loadUrl("https://v.zmengzhu.com/play/10027924?inviter=4789850");
    }

    public static class TestItem {
        public String title;
        public KKParentFragment fragment;
        public KKViewOnclickListener onclickListener;

        public TestItem(String title, KKParentFragment fragment, KKViewOnclickListener onclickListener) {
            this.title = title;
            this.fragment = fragment;
            this.onclickListener = onclickListener;
        }


    }

    public ArrayList<TestItem> testItems = new ArrayList<>();

    public void addItem(final String title, final KKParentFragment fragment, KKViewOnclickListener onclickListener) {
        testItems.add(new TestItem(title, fragment, onclickListener));
    }

    public void refresh() {
        KKSimpleRecycleView recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        recycleView.setItemDecoration(new KKDecoration(3, 0, 10, 10, 1, Color.BLACK){
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                TextView tv_main_title=view.findViewById(R.id.tv_main_title);
               // UiTool.setWHEqual(tv_main_title);
            }
        });

        recycleView.setData(testItems, R.layout.activity_main_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final TestItem testItem = testItems.get(position);

                UiTool.setTextView(itemView, R.id.tv_main_title, testItem.title);
                if (testItem.onclickListener != null) {
                    itemView.setOnClickListener(testItem.onclickListener);
                } else {
                    itemView.setOnClickListener(new KKViewOnclickListener() {
                        @Override
                        public void onClickKK(View v) {
                            testItem.fragment.go();
                        }
                    });
                }

            }
        });
    }
}
