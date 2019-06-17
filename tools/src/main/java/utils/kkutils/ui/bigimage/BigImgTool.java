package utils.kkutils.ui.bigimage;

import android.view.View;
import android.widget.ImageView;

import java.util.List;

import utils.kkutils.parent.KKViewOnclickListener;

/**
 * abc on 2017/11/22.
 */

public class BigImgTool {
    /***
     * 显示大图， 需要传入当前显示小图的ImageView
     * @param imageView
     */
    public static void showBigImgSingle(ImageView imageView) {
        PicViewActivity.go(imageView);
    }



    public static void bindShowBigImgSingle(final ImageView imageView) {
        imageView.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                showBigImgSingle(imageView);
            }
        });
    }
    public static void bindShowBigImgs(View itemView, final int positon, final String... images) {
        itemView.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new BigImgListFragmentKK().go(positon,  images);
            }
        });
    }
    public static void bindShowBigImgs(View itemView, final int positon, final List<String> images) {
        itemView.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new BigImgListFragmentKK().go(positon,  images);
            }
        });
    }
}
