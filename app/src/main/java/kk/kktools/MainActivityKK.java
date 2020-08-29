package kk.kktools;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import douyin.DouYinFragment;
import kk.kktools.animation.KKTestCanvas;
import kk.kktools.color.TestColorFragmentKK;
import kk.kktools.jinrong.JiSuanQi;
import kk.kktools.recycleview_test.TestRecycleView;
import kk.kktools.shu.ShuJiaFragment;
import kk.kktools.tab.TestTab;
import kk.kktools.tupian_xuanze.KKChooseImgFragmentKK;
import kk.kktools.tuya.TestEditView;
import kk.kktools.viewpager2.ViewPager2Test;
import kk.kktools.web.WebFragment;
import utils.kkutils.AppTool;
import utils.kkutils.HttpTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.parent.KKParentActivity;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.bigimage.KKBigImgListFragment;
import utils.kkutils.ui.daojishi.DaoJiShiTool;
import utils.kkutils.ui.recycleview.KKDecoration;
import utils.kkutils.ui.video.douyin2.DouYinFragment2;
import utils.kkutils.ui.webview.X5WebView;
import utils.kkutils.update.Version;
import utils.kkutils.zhifu.KKZhiFuUiTool;

public class MainActivityKK extends KKParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTool.init(getApplication(), true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_span=findViewById(R.id.tv_span);

        testItems.clear();
        addItem("Boo",  new ShuJiaFragment(),null);
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
                new TestCoordinatorLayoutFragmentKK().go();
            }
        });
        addItem("测试recycleView", new TestRecycleView(), null);
        addItem("测试视频播放", new TestRecycleView(), new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                // KKVideoPlayer.go();
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

        addItem("拼多多", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                String url="https://mobile.yangkeduo.com/app.html?use_reload=1&launch_url=duo_coupon_landing.html%3Fgoods_id%3D95338609834%26pid%3D10421334_140080857%26cpsSign%3DCC_200509_10421334_140080857_ec5843bacb7bd87c61f810a79da659a4%26duoduo_type%3D2&campaign=ddjb&cid=launch_dl_force_";

                ShopTool.openUrl(url);
            }
        });
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
        //testVersion();

        // testWeb();
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
        version.isHtml="1";
        version.isForce="1";
        version.updateUrl="https://hellowvideo.oss-cn-hongkong.aliyuncs.com/download/android/hello-video-app-8.apk";
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
