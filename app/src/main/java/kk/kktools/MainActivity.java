package kk.kktools;

import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;

import utils.kkutils.AppTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.ParentActivity;
import utils.kkutils.parent.ParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.bigimage.BigImgListFragment;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTool.init(getApplication(),true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        addItem("测试大图", null, new KKViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new BigImgListFragment().go(0, TestData.getTestImgUrlList(2));
            }
        });
        addItem("测试CoordinatorLayout,和选择收货地址", null, new KKViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new TestCoordinatorLayoutFragment().go();
            }
        });

        refresh();
    }

    public static class TestItem{
        public String title;
        public ParentFragment fragment;
        public KKViewOnclickListener onclickListener;
        public TestItem(String title, ParentFragment fragment, KKViewOnclickListener onclickListener) {
            this.title = title;
            this.fragment = fragment;
            this.onclickListener = onclickListener;
        }



    }
    public ArrayList<TestItem> testItems=new ArrayList<>();
    public void addItem(final String title, final ParentFragment fragment, KKViewOnclickListener onclickListener){
        testItems.add(new TestItem(title,fragment,onclickListener));
    }
    public void refresh(){
        KKSimpleRecycleView recycleView=findViewById(R.id.recycleView);
        recycleView.setData(testItems, R.layout.activity_main_item, new KKSimpleRecycleView.WzRecycleAdapter() {
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
                        public void onClickWz(View v) {
                            testItem.fragment.go();
                        }
                    });
                }

            }
        });
    }
}
