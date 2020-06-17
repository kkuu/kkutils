package kk.kktools.color;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.shixia.colorpickerview.ColorPickerView;
import com.shixia.colorpickerview.OnColorChangeListener;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.TestData;
import utils.kkutils.common.UiTool;
import utils.kkutils.fragment.dizhi.KK_XuanZheShouHuoDiZhiFragment;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKSimpleRecycleView;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.dialog.DialogTool;

public class TestColorFragmentKK extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_color;
    }

    @Override
    public void initData() {

        StatusBarTool.setStatusBarColor(getActivity(), Color.TRANSPARENT, false, true);
        parent.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                ColorPickerView colorPicker = new ColorPickerView(getContext(), null);
                //对控件进行回调监听，获取颜色值color
                colorPicker.setOnColorChangeListener(new OnColorChangeListener() {
                    @Override
                    public void colorChanged(int color) {
                        parent.setBackgroundColor(color);
                        String colorStr=toHexEncoding(color);
                        CommonTool.showToast(colorStr);
                    }
                });
                Dialog dialog = DialogTool.initBottomDialog(colorPicker);
                dialog.show();
            }
        });


    }
    public static String toHexEncoding(int color) {
        String R, G, B;
        StringBuffer sb = new StringBuffer();
        R = Integer.toHexString(Color.red(color));
        G = Integer.toHexString(Color.green(color));
        B = Integer.toHexString(Color.blue(color));
        //判断获取到的R,G,B值的长度 如果长度等于1 给R,G,B值的前边添0
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        sb.append("0x");
        sb.append(R);
        sb.append(G);
        sb.append(B);
        return sb.toString();
    }

}
