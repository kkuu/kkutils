package utils.kkutils.ui.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import utils.kkutils.AppTool;
import utils.kkutils.R;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKViewOnclickListener;

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
}
