package kk.kktools;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.palette.graphics.Palette;

import utils.kkutils.ImgTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.common.ViewTool;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.dialog.DialogTool;

/***
 * 调色板测试
 */
public class PaletteTest {

    public static void test(final Activity activity){
        final ImageView imageView=new ImageView(activity);

        final LinearLayout linearLayout=new LinearLayout(activity);
        linearLayout.addView(imageView,-1,-1);
        UiTool.setWH(linearLayout, -1, -1);
        Dialog dialog = DialogTool.initBottomDialog(linearLayout);
        dialog.getWindow().setDimAmount(0);
        dialog .show();


        final int key= ViewTool.initKey();
        imageView.setTag(key,5);
        ImgTool.loadImage(TestData.getTestImgUrl((Integer) imageView.getTag(key)), imageView);
        imageView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                try {
                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();

                    Palette palette = Palette.from(imageView.getDrawingCache()).generate();
                    int darkMutedColor =palette.getMutedColor(Color.TRANSPARENT);
                    if(darkMutedColor!=Color.TRANSPARENT){
                        linearLayout.setBackgroundColor(darkMutedColor);
                        StatusBarTool.setStatusBarColor(activity,darkMutedColor,false,false);
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
        });
        imageView.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                imageView.setTag(key,(Integer) imageView.getTag(key)+1);
                ImgTool.loadImage(TestData.getTestImgUrl((Integer) imageView.getTag(key)), imageView);
            }
        });
    }
}
