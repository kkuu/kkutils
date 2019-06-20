package kk.kktools;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import utils.kkutils.AppTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentActivity;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.bigimage.KKBigImgListFragment;
import utils.kkutils.ui.webview.X5WebView;

public class MainActivityKK extends KKParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTool.init(getApplication(),true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        refresh();

        testWeb();
    }

    public void testWeb(){
        //x5web 不支持x86
        X5WebView x5WebView=new X5WebView(this);
        ViewGroup parent= (ViewGroup) getWindow().getDecorView();
        parent.addView(x5WebView);
        UiTool.setWH(x5WebView,-1,-1);
        // x5WebView.loadUrl("https://blz-videos.nosdn.127.net/1/HearthStone/f6cd63b590d416821d3e27e0.mp4");
        x5WebView.loadUrl("https://v.zmengzhu.com/play/10027924?inviter=4789850");
    }
    public static class TestItem{
        public String title;
        public KKParentFragment fragment;
        public KKViewOnclickListener onclickListener;
        public TestItem(String title, KKParentFragment fragment, KKViewOnclickListener onclickListener) {
            this.title = title;
            this.fragment = fragment;
            this.onclickListener = onclickListener;
        }



    }
    public ArrayList<TestItem> testItems=new ArrayList<>();
    public void addItem(final String title, final KKParentFragment fragment, KKViewOnclickListener onclickListener){
        testItems.add(new TestItem(title,fragment,onclickListener));
    }
    public void refresh(){
        KKSimpleRecycleView recycleView=findViewById(R.id.recycleView);
        recycleView.setData(testItems, R.layout.activity_main_item, new KKSimpleRecycleView.KKRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final TestItem testItem = testItems.get(position);

                UiTool.setTextView(itemView,R.id.tv_main_title,testItem.title);
                if(testItem.onclickListener!=null){
                    itemView.setOnClickListener(testItem.onclickListener);
                }else {
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
