package kk.kktools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import java.util.ArrayList;

import utils.wzutils.AppTool;
import utils.wzutils.HttpTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LanguageTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.PwdTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.wzutils.http.HttpRequest;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzNormalFragmentActivity;
import utils.wzutils.parent.WzParentActivity;
import utils.wzutils.parent.WzParentFragment;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.bigimage.BigImgTool;
import utils.wzutils.ui.bigimage.WzBigImgListFragment;

public class MainActivity extends WzParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTool.init(getApplication(),true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        addItem("测试大图", null, new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new WzBigImgListFragment().go(0, TestData.getTestImgUrlList(2));
            }
        });
        addItem("测试CoordinatorLayout,和选择收货地址", null, new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new TestCoordinatorLayoutFragment().go();
            }
        });

        refresh();
    }

    public static class TestItem{
        public String title;
        public WzParentFragment fragment;
        public WzViewOnclickListener onclickListener;
        public TestItem(String title, WzParentFragment fragment, WzViewOnclickListener onclickListener) {
            this.title = title;
            this.fragment = fragment;
            this.onclickListener = onclickListener;
        }



    }
    public ArrayList<TestItem> testItems=new ArrayList<>();
    public void addItem(final String title, final WzParentFragment fragment,WzViewOnclickListener onclickListener){
        testItems.add(new TestItem(title,fragment,onclickListener));
    }
    public void refresh(){
        WzSimpleRecycleView recycleView=findViewById(R.id.recycleView);
        recycleView.setData(testItems, R.layout.activity_main_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final TestItem testItem = testItems.get(position);

                UiTool.setTextView(itemView,R.id.tv_main_title,testItem.title);
                if(testItem.onclickListener!=null){
                    itemView.setOnClickListener(testItem.onclickListener);
                }else {
                    itemView.setOnClickListener(new WzViewOnclickListener() {
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
