package utils.kkutils.ui.dialog;

import android.app.Dialog;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.Arrays;

import androidx.annotation.RequiresApi;
import utils.kkutils.AppTool;
import utils.kkutils.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LayoutInflaterTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.animation.FloatView;

public class DialogSimple {

    /***
     * 显示一般的弹出框
     * @param title
     * @param content
     * @param btnQueDingText
     * @param onclickListenerQueDing
     * @param btnQuXiaoText
     * @param onclickListenerQuXiao
     * @return
     */
    public static Dialog showTiShiDialog(String title, String content, String btnQueDingText, final KKViewOnclickListener onclickListenerQueDing,String btnQuXiaoText,final KKViewOnclickListener onclickListenerQuXiao){
        Dialog dialog=showTiShiDialogImp(R.layout.kk_dialog_tishi,title,btnQueDingText,onclickListenerQueDing,btnQuXiaoText,onclickListenerQuXiao);
        final TextView tv_dialog_content=dialog.findViewById(R.id.kk_tv_dialog_content);
        UiTool.setTextView(tv_dialog_content,content);

        tv_dialog_content.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                try {
                    int lineCount = tv_dialog_content.getLineCount();
                    if(lineCount<2){
                        tv_dialog_content.setGravity(Gravity.CENTER);
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }

            }
        });
        return dialog;
    }


    public static Dialog showTiShiDialogImp(int layoutId,String title, String btnQueDingText, final KKViewOnclickListener onclickListenerQueDing,String btnQuXiaoText,final KKViewOnclickListener onclickListenerQuXiao){
        View view= AppTool.currActivity.getLayoutInflater().inflate(layoutId,null);
        final Dialog dialog = DialogTool.initNormalDialog(view, -1);
        UiTool.setTextView(view,R.id.kk_tv_dialog_title,title);


        final TextView btn_dialog_queding=view.findViewById(R.id.kk_btn_dialog_queding);
        UiTool.setTextView(btn_dialog_queding,btnQueDingText);
        btn_dialog_queding.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                try {
                    if(onclickListenerQueDing!=null){
                        onclickListenerQueDing.onClickKK(v);
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }
                dialog.dismiss();
            }
        });


        final TextView btn_dialog_quxiao=view.findViewById(R.id.kk_btn_dialog_quxiao);
        if(btn_dialog_quxiao!=null){
            if(StringTool.isEmpty(btnQuXiaoText)){
                btn_dialog_quxiao.setVisibility(View.GONE);
            }else {
                UiTool.setTextView(btn_dialog_quxiao,btnQuXiaoText);
                btn_dialog_quxiao.setOnClickListener(new KKViewOnclickListener() {
                    @Override
                    public void onClickKK(View v) {
                        try {
                            if(onclickListenerQuXiao!=null){
                                onclickListenerQuXiao.onClickKK(v);
                            }
                        }catch (Exception e){
                            LogTool.ex(e);
                        }
                        dialog.dismiss();
                    }
                });
            }
        }

        dialog.show();
        return dialog;
    }


    /***
     * @param title
     * @param onclickListener
     * @param items
     * @return
     */
    public static Dialog showBottomChooseDialog(String title, final OnBottomChooseDialogClick onclickListener, final String ... items){
        View view= LayoutInflaterTool.getInflater(3,R.layout.kk_dialog_dibu_xuanze).inflate();
        final Dialog dialog = DialogTool.initBottomDialog(view);

        UiTool.setTextView(view,R.id.kk_tv_dialog_dibu_xuanze_title,title);

        ViewGroup kk_vg_dialog_dibu_xuanze_items=dialog.findViewById(R.id.kk_vg_dialog_dibu_xuanze_items);
        kk_vg_dialog_dibu_xuanze_items.removeAllViews();


        for(int i=0;i<items.length;i++){
            final String item=items[i];
            TextView textView=new TextView(new ContextThemeWrapper(AppTool.getApplication(),R.style.kk_tv_dibu_dialog_xuanze_btn));
            UiTool.setTextView(textView,item);
            View lineView=new View(new ContextThemeWrapper(AppTool.getApplication(),R.style.kk_line_h));
            lineView.setBackgroundColor(AppTool.getApplication().getResources().getColor(R.color.kk_line));
            kk_vg_dialog_dibu_xuanze_items.addView(lineView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
            kk_vg_dialog_dibu_xuanze_items.addView(textView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonTool.dip2px(58)));
            final int index = i;
            textView.setOnClickListener(new KKViewOnclickListener() {
                @Override
                public void onClickKK(View v) {
                    if(onclickListener!=null){
                        onclickListener.onClick(dialog,items, index,item);
                    }
                }
            });
        }

        view.findViewById(R.id.kk_tv_dialog_dibu_xuanze_quxiao).setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }
    public static interface OnBottomChooseDialogClick{
        public void onClick(Dialog dialog,String[] items,int index,String item);
    }
}
