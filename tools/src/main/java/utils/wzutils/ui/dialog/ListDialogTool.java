package utils.wzutils.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;

import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;

/**
 * Created by kk on 2016/5/13.
 */
public class ListDialogTool {
    public static Dialog showListDialog(String title, CharSequence[] data, DialogInterface.OnClickListener onItemClickListener) {

        return showListDialog(title,data,40,60,onItemClickListener);
    }
    public static Dialog showListDialog(String title, CharSequence[] data,int dpMarginLeftRight,int dpMarginTopBottom, DialogInterface.OnClickListener onItemClickListener) {
        Dialog dialog = null;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AppTool.currActivity);
            if (!StringTool.isEmpty(title)) {
                builder.setTitle(title);
            }
            dialog = builder.setItems(data, onItemClickListener).create();
            dialog.show();

            int w=dpMarginLeftRight>0?CommonTool.getWindowSize().x - CommonTool.dip2px(dpMarginLeftRight * 2):ViewGroup.LayoutParams.WRAP_CONTENT;
            int h=dpMarginTopBottom>0?CommonTool.getWindowSize().y - CommonTool.dip2px(dpMarginTopBottom * 2+22):ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(w,h);//默认的宽宥边距 不要默认宽

//            dialog.getWindow().setBackgroundDrawableResource(R.color.white);

            dialog.setCanceledOnTouchOutside(true);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return dialog;
    }
}
