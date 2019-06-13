package utils.wzutils.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.R;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.ViewTool;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/1/9.
 * <p>
 * <style name="dialog_transparent" parent="@android:style/Theme.DeviceDefault.Dialog">
 * <item name="android:windowFrame">@null</item><!--边框-->
 * <item name="android:windowIsFloating">true</item><!--是否浮现在activity之上-->
 * <item name="android:windowIsTranslucent">false</item><!--半透明-->
 * <item name="android:windowNoTitle">true</item><!--无标题-->
 * <item name="android:windowBackground">@android:color/transparent</item><!--背景透明-->
 * <item name="android:backgroundDimEnabled">false</item><!--模糊-->
 * </style>
 */
public class DialogTool {
    /***
     * 去掉自定义的Dialog 的布局的最上面的蓝色的title线
     */
    public static void setNoTitleDivider(Dialog dialog) {
        try {
            int dividerID = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.getWindow().getDecorView().findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            //上面的代码，是用来去除Holo主题的蓝色线条
            e.printStackTrace();
        }
    }


    /***
     * 底部弹出的 dialog 调用一下这个
     */
    public static Dialog initBottomDialog(View contentView) {
        final Dialog dialog = new AppCompatDialog(AppTool.currActivity);//AppCompatDialog 用这个才能适配全面屏幕
        try {
            if (dialog != null) {
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(contentView);
                dialog.setCanceledOnTouchOutside(true);
                setDialogNoBg(dialog);
                dialog.getWindow().setLayout(CommonTool.getWindowSize().x, ViewGroup.LayoutParams.MATCH_PARENT);//默认的宽宥边距 不要默认宽
                contentView.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        dialog.dismiss();
                    }
                });
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return dialog;
    }

    /***
     * 生成一个普通dialog
     * @param contentView
     * @param dpJianJu  距离左边 和右边的间距 dp单位， 比如 20dp
     * @return
     */
    public static Dialog initNormalDialog(View contentView, int dpJianJu) {
        Dialog dialog = new AppCompatDialog(AppTool.currActivity);
        try {
            if (dialog != null) {
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                dialog.getWindow().setDimAmount(0.7f);
                dialog.setContentView(contentView);
                if (dpJianJu > -1) {
                    setDialogNoBg(dialog);
                    dialog.getWindow().setLayout(CommonTool.getWindowSize().x - CommonTool.dip2px(dpJianJu * 2), ViewGroup.LayoutParams.WRAP_CONTENT);//默认的宽宥边距 不要默认宽
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return dialog;
    }

    /***
     * 生成一个带确定的普通dialog
     * @param title
     * @param msg
     * @param queding
     * @param queDingOnClickListener
     * @param quxiao
     * @return
     */
    public static Dialog initNormalQueDingDialog(String title, String msg, String queding, final DialogInterface.OnClickListener queDingOnClickListener, String quxiao) {
        AlertDialog.Builder dialogBuilder = initNormalQueDingBuilder(title,msg,queding,queDingOnClickListener,quxiao);
        return dialogBuilder.create();
    }

    public static AlertDialog.Builder initNormalQueDingBuilder(String title, String msg, String queding, final DialogInterface.OnClickListener queDingOnClickListener, String quxiao) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AppTool.currActivity);
        if (StringTool.notEmpty(title)) {
            dialogBuilder.setTitle(title);
        }
        if (StringTool.notEmpty(msg)) {
            dialogBuilder.setMessage(msg);
        }
        dialogBuilder.setPositiveButton(queding, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (queDingOnClickListener != null) {
                        queDingOnClickListener.onClick(dialog, which);
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
        });
        if(StringTool.notEmpty(quxiao)){
            dialogBuilder.setNegativeButton(quxiao, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {

                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            });
        }
        return dialogBuilder;
    }
    public static interface OnDialogInputEnd{
        void onInputEnd(EditText editText);
    }

    /***
     * 弹出输入框
     * @param title
     * @param msg
     * @param queding
     * @param onDialogInputEnd
     * @param quxiao
     * @param defalutStr
     * @param hint
     * @param inputType
     * @return
     */
    public static Dialog initNormalInputDialog(String title, String msg, String queding, final OnDialogInputEnd onDialogInputEnd, String quxiao  ,String defalutStr,int inputType) {

        final EditText tv_dialog_input=new EditText(AppTool.currActivity);
        tv_dialog_input.setTag("tv_dialog_input");
        tv_dialog_input.setText(defalutStr);
        tv_dialog_input.setInputType(inputType);
        RelativeLayout relativeLayout=new RelativeLayout(AppTool.currActivity);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins( CommonTool.dip2px(20),0,CommonTool.dip2px(20),0);
        relativeLayout.addView(tv_dialog_input,lp);


        AlertDialog.Builder dialogBuilder = initNormalQueDingBuilder(title, msg, queding, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    try {
                        if(onDialogInputEnd!=null){
                            onDialogInputEnd.onInputEnd(tv_dialog_input);
                        }
                        }catch (Exception e){
                        LogTool.ex(e);
                    }
            }
        }, quxiao);


        dialogBuilder.setView(relativeLayout);

        AlertDialog dialog=dialogBuilder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                tv_dialog_input.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager inputmanger = (InputMethodManager) AppTool.currActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.showSoftInput(tv_dialog_input,0);
                    }
                },1);

            }
        });
        return dialog;
    }



    /**
     * 需要在show 之后
     *
     * @param dialog
     * @param w
     * @param h
     */
    public static void setDialogWH(Dialog dialog, int w, int h) {
        try {
            dialog.getWindow().setLayout(w, h);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }


    /**
     * 默认的背景不合适有间距，所以不用默认背景
     * @param dialog
     */
    public static void setDialogNoBg(Dialog dialog){
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    /***
     * 让dialog  靠在制定的view 旁边， 具体的看显示效果就知道了
     * 例子：

     DialogTool.showAsDropDown(dialog,titleTool.tv_title_small,-CommonTool.dip2px(100),-CommonTool.dip2px(5));


     <style name="dialog" parent="@android:style/Theme.Dialog">
     <item name="android:windowBackground">@android:color/transparent</item>
     <item name="android:windowFrame">@null</item>
     <item name="android:windowContentOverlay">@null</item>
     <item name="android:windowAnimationStyle">@null</item>
     <item name="android:backgroundDimEnabled">false</item>
     <item name="android:windowIsTranslucent">true</item>
     <item name="android:windowNoTitle">true</item>

     </style>

     */
    public static void showAsDropDown(Dialog dialog,View anchorView, int xOffDp, int yOffDp ) {
        setDialogNoBg(dialog);
        if (anchorView != null) {
            int[] location = new int[2];
            anchorView.getLocationInWindow(location);
            Window window =dialog.getWindow();

//            window.getDecorView().measure(0,0);
//            int w=window.getDecorView().getMeasuredWidth();
//            int h=window.getDecorView().getMeasuredHeight();

            WindowManager.LayoutParams lp = window.getAttributes();
            window.setGravity(Gravity.LEFT|Gravity.TOP);//左上角开始
            lp.x = location[0]+CommonTool.dip2px(xOffDp); // 新位置X坐标
            lp.y = location[1]+CommonTool.dip2px(yOffDp); // 新位置Y坐标
            window.setAttributes(lp);
        }
        dialog.show();
    }








    public static Dialog showSingleChoiceDialog(String title, final CharSequence[] data, String queding, final DialogInterface.OnClickListener onItemClickListener) {
        return showSingleChoiceDialog(title, data,0, queding, onItemClickListener);
    }
    public static Dialog showSingleChoiceDialog(String title, final CharSequence[] data,int currSelector, String queding, final DialogInterface.OnClickListener onItemClickListener) {
        return showSingleChoiceDialog(title,data,currSelector,queding,onItemClickListener,"",null);
    }
    public static AlertDialog showSingleChoiceDialog(String title,
                                                final CharSequence[] data,
                                                int currSelector,
                                                String queding, final DialogInterface.OnClickListener onItemClickListener,
                                                String neutralButton, final DialogInterface.OnClickListener onNeutralButtonItemClickListener
    ) {
        if(currSelector>data.length-1){
            currSelector=data.length-1;
        }

        AlertDialog dialog = null;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AppTool.currActivity);
            if (!StringTool.isEmpty(title)) {
                builder.setTitle(title);
            }
            final int[] chooseWhich = {0};
            dialog = builder
                    .setSingleChoiceItems(data, currSelector, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogIn, int which) {
                            chooseWhich[0] =which;
                        }
                    })
                    .setPositiveButton(queding, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onItemClickListener.onClick(dialog,chooseWhich[0]);
                        }
                    })
                    .setNeutralButton(neutralButton,onNeutralButtonItemClickListener)
                    .setNegativeButton(AppTool.getApplication().getString(R.string.quxiao), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create();
            dialog.show();
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return dialog;
    }
















    public static interface MultiChoiceListener{
        void onChoose(DialogInterface dialogInterface, List<Integer> choose,boolean[] chooseAll);
    }

    /***
     * 多选
     * @param title
     * @param data 多选数据
     * @param choose  默认是否选中， length 必须和data 一样
     * @param queding
     * @param quxiao
     * @param multiChoiceListener
     * @return
     */
    public static AlertDialog showMultiChoiceDialog(String title, CharSequence[] data, boolean[] choose, String queding, String quxiao, final MultiChoiceListener multiChoiceListener) {
        final List<Integer>chooseList=new ArrayList<>();
        AlertDialog dialog = null;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AppTool.currActivity);
            if (!StringTool.isEmpty(title)) {
                builder.setTitle(title);
            }
            if(choose==null)choose=new boolean[data.length];
            final boolean[] finalChoose = Arrays.copyOf(choose,choose.length);
            dialog=builder.setMultiChoiceItems(data, choose, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    finalChoose[which]=isChecked;
                }
            }).setPositiveButton(queding, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    chooseList.clear();
                    for(int i=0;i<finalChoose.length;i++){
                        if(finalChoose[i]){
                            chooseList.add(i);
                        }
                    }
                    if(multiChoiceListener!=null){
                        multiChoiceListener.onChoose(dialog,chooseList,finalChoose);
                    }
                }
            }).setNegativeButton(quxiao, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
            dialog.show();

        } catch (Exception var10) {
            LogTool.ex(var10);
        }

        return dialog;
    }
}
