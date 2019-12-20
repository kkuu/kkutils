package kk.kktools;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import douyin.DouYinFragment;
import kk.kktools.jinrong.JiSuanQi;
import kk.kktools.tupian_xuanze.KKChooseImgFragmentKK;
import kk.kktools.tuya.TestEditView;
import kk.kktools.viewpager2.ViewPager2Test;
import utils.kkutils.AppTool;
import utils.kkutils.HttpTool;
import utils.kkutils.ImgTool;
import utils.kkutils.common.CommonTool;
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
import utils.kkutils.ui.webview.X5WebView;
import utils.kkutils.zhifu.KKZhiFuUiTool;

public class MainActivityKK extends KKParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTool.init(getApplication(), true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItem("计算器", new JiSuanQi(), null);
        addItem("测试大图", null, new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                new KKBigImgListFragment().go(0, TestData.getTestImgUrlList(2));
            }
        });
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
        addItem("viewpager2", new ViewPager2Test(), null);

        addItem("图片选择器", new KKChooseImgFragmentKK(), null);
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
        addItem("图片编辑", new TestEditView(), null);
        refresh();



        HttpTool.request(HttpRequest.url("https://www.baidu.com").addQueryParams("a", "b"), String.class, new HttpUiCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                LogTool.s(data);
            }
        });


        // testWeb();
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
