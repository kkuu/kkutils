package kk.kktools.viewpager2;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import kk.kktools.R;
import utils.kkutils.ImgTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LayoutInflaterTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.MathTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKImageView;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.bigimage.PinchImageView;

public class ViewPager2Test extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_viewpager2_test;
    }

    ViewPager2 view_pager2;
    ViewPager view_pager;
    @Override
    public void initData() {

        initViewPager2();

        initViewPager();
    }

    public void initViewPager(){
        final List<String> imgUrlList = TestData.getTestImgUrlList(10);
        view_pager.setOffscreenPageLimit(5);
        view_pager.setPageMargin(CommonTool.dip2px(10));
        view_pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgUrlList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new KKImageView(parent.getContext());//不能用 curractivity
                imageView.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                ImgTool.loadImage(imgUrlList.get(position), imageView);
                container.addView(imageView);
                return imageView;
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                try {
                    container.removeView((View) object);
                }catch (Exception e){
                    LogTool.ex(e);
                }
            }
        });
    }
    public void initViewPager2(){
        final List<String> imgUrlList = TestData.getTestImgUrlList(10);
        view_pager2.setOffscreenPageLimit(5);//重要需要加上
        view_pager2.setAdapter(new RecyclerView.Adapter() {
            class TestHolder extends RecyclerView.ViewHolder{
                public ImageView imageView;
                public TestHolder(@NonNull View itemView) {
                    super(itemView);
                    imageView= (ImageView) itemView;
                }
            }
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ImageView imageView = new KKImageView(parent.getContext());//不能用 curractivity
                imageView.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return new TestHolder(imageView);
            }

            int padding=CommonTool.dip2px(10);
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TestHolder testHolder= (TestHolder) holder;
                ImgTool.loadImage(imgUrlList.get(position), testHolder.imageView);
                if(position==0){
                    testHolder.imageView.setPadding(0,0,padding,0);
                }else {
                    testHolder.imageView.setPadding(padding/2,0,padding/2,0);
                }
            }

            @Override
            public int getItemCount() {
                return imgUrlList.size();
            }
        });
    }

}
