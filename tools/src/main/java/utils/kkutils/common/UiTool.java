package utils.kkutils.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import utils.kkutils.AppTool;
import utils.kkutils.ImgTool;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.KKToast;
import utils.kkutils.ui.dialog.DialogSimple;

/**
 * Created by kk on 2016/5/10.
 */
public class UiTool {
    static long preBackToQuitTime = 0;

    /***
     * 检测空并弹框
     * @param textView
     * @return
     */
    public static String checkNullAndToast(TextView textView){
        return checkNullAndToast(textView,"");
    }
    public static String checkNullAndToast(TextView textView,String defaultHint){
        if(StringTool.isEmpty(textView.getText().toString())){
            if(StringTool.notEmpty(defaultHint)){
                CommonTool.showToast(defaultHint);
            }else {
                CommonTool.showToast(textView.getHint());
            }
            throw new RuntimeException("参数为空：");
        }
        return textView.getText().toString().trim();
    }

    /**
     * ***打电话*
     */
    public static void tellPhone(final String phoneNum, final Activity activity) {
        try {
            if (phoneNum == null || phoneNum.length() < 1) return;
            if (activity == null) return;

            initSimpleDilog("是否拨打 " + phoneNum, "拨打", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        String phone = phoneNum.replace("-", "").replace(" ", "");
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri//进入拨号界面不拨打   ACTION_CALL 这个是直接拨打，但是可能没权限
                                .parse("tel:" + phone));
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            }, "取消", null).show();

        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 启动浏览器 浏览Url
     * @param url
     */
    public static void startUrlView(String url){
        startUrlView(url,null);
    }
    public static void startUrlView(String url,String packageStr){
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(packageStr);
        AppTool.getApplication().startActivity(intent);
    }

    /***
     * 一定弹出选框
     * @param url
     */
    public static void startUrlViewWithChoose(String url){
        try {
            final Intent intent = new Intent();
            intent.setData(Uri.parse(url));//Url 就是你要打开的网址
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);

            final List<ResolveInfo> list = AppTool.getApplication().getPackageManager()
                    .queryIntentActivities(intent, 0);
            String[] items=new String[list.size()];
            for(int i=0;i<list.size();i++){
                ResolveInfo resolveInfo = list.get(i);
                String name=""+resolveInfo.activityInfo.loadLabel(AppTool.getApplication().getPackageManager());
                items[i]=name;
            }

            DialogSimple.showBottomChooseDialog("打开链接", new DialogSimple.OnBottomChooseDialogClick() {
                @Override
                public void onClick(Dialog dialog, String[] items, int index, String item) {
                    ActivityInfo activityInfo = list.get(index).activityInfo;
                    intent.setClassName(activityInfo.packageName,activityInfo.name);
                    AppTool.getApplication().startActivity(intent);
                }
            },items).show();
        }catch (Exception e){
            LogTool.ex(e);
        }

    }



    /***
     * 双击退出程序
     */
    public static void backToQuit(String textToast) {
        try {
            long timeNow = System.currentTimeMillis();
            if (timeNow - preBackToQuitTime < 2000) {
                //AppTool.currActivity.moveTaskToBack(true);

                AppTool.backToHome();
            } else {
                CommonTool.showToast(textToast);
            }
            preBackToQuitTime = timeNow;
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }



    /***
     * 生成一个dialog
     *
     * @param msg
     * @param queding
     * @param quedingListener
     * @param quxiao
     * @param quxiaoListener
     * @return
     */
    public static Dialog initSimpleDilog(String msg, String queding, DialogInterface.OnClickListener quedingListener, String quxiao, DialogInterface.OnClickListener quxiaoListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(AppTool.currActivity)
                .setMessage(msg)
                .setPositiveButton(queding, quedingListener)
                .setNegativeButton(quxiao, quxiaoListener)
                .create();
        return alertDialog;
    }

    public static void showToast(int resId) {
        try {
            showToast(AppTool.getApplication().getResources().getText(resId));
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 主要是  华为部分手机  禁用 通知权限后 toast 没法弹出
     *
     * @param toastText
     */
    public static void showToast(final Object toastText) {
        try {
            KKToast KKToast = new KKToast();
            KKToast.showWzToast(toastText);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void showToastLong(final Object toastText) {
        try {
            KKToast KKToast = new KKToast();
            KKToast.showWzToastLong(toastText);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 设置输入 钱。。。
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
        editText.setInputType(8194);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void setCompoundDrawables(Activity activity, TextView textView, int resIdLeft, int resIdTop, int resIdRight, int resIdBottom) {
        try {
            if(activity==null||textView==null)return;
            Drawable drawableLeft = null;
            Drawable drawableTop = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            if (resIdLeft > 0) {
                drawableLeft = activity.getResources().getDrawable(resIdLeft);
                drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
            }
            if (resIdTop > 0) {
                drawableTop = activity.getResources().getDrawable(resIdTop);
                drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
            }
            if (resIdRight > 0) {
                drawableRight = activity.getResources().getDrawable(resIdRight);
                drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
            }
            if (resIdBottom > 0) {
                drawableBottom = activity.getResources().getDrawable(resIdBottom);
                drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(), drawableBottom.getMinimumHeight());
            }
            textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    /***
     * 给一个文本设置字符串
     *
     * @param parent 文本所在容器
     * @param resId  文本 资源id
     * @param text   要设置的值
     */
    public static void setTextView(View parent, int resId, Object text) {
        try {
            TextView textView = (TextView) parent.findViewById(resId);
            setTextView(textView, text);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void setTextView(TextView textView, Object text) {
        try {
            if(textView!=null){
                if(text instanceof Integer){
                    textView.setText((Integer) text);
                }else {
                    textView.setText(StringTool.getNotNullText(text));
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void setTextViewGoneIfNull(View parent, int resId, Object text) {
        try {
            TextView textView = (TextView) parent.findViewById(resId);
            setTextViewGoneIfNull(textView, text);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void setTextViewGoneIfNull(TextView textView, Object text) {
        try {
            if(textView!=null){
               setTextView(textView,text);
                if(StringTool.isEmpty(""+text)){
                    textView.setVisibility(View.GONE);
                }else {
                    textView.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void setTextColor(TextView textView, int colorResId) {
        try {
            textView.setTextColor(getColorByResId(colorResId));
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void setTextColor(View parent, int resId, int colorResId) {
        try {
            TextView textView = (TextView) parent.findViewById(resId);
            setTextColor(textView, colorResId);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void setBoldText(View textView){
        try {
           if(textView !=null&&textView instanceof TextView){
               ((TextView) textView).getPaint().setFakeBoldText(true);
           }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static int getColorByResId(int colorResId){
        return ResourcesTool.getColor(colorResId);
    }

    public static void setWHEqual(View view){
        if(view==null)return;
        view.post(new Runnable() {
            @Override
            public void run() {
                UiTool.setWH(view, -100, view.getWidth());//设置正方形
            }
        });
    }
    /***
     * 给控件设置w h
     * @param view
     * @param w
     * @param h
     */
    public static void setWH(View view, int w, int h) {
        if (view == null) return;
        try {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(0, 0);
            }
            if(w>-9)lp.width = w;
            if(h>-9)lp.height = h;
            view.setLayoutParams(lp);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void setWHDp(View view, int w, int h) {
        if(w>0){
            w=CommonTool.dip2px(w);
        }
        if(h>0){
            h=CommonTool.dip2px(h);
        }
        setWH(view,w,h);
    }


    /***
     *
     * @param view
     * @param srcW  设计图宽
     * @param srcH  设计图高
     * @param widthPx  手机上面的实际宽度
     */
    public static void setWHBi(View view, double srcW,double srcH, double widthPx) {
        double h1 =widthPx/(srcW/srcH) ;
        UiTool.setWH(view, ViewGroup.LayoutParams.MATCH_PARENT, (int) h1);
    }




    public static void setMargin(View view, int left,int top,int right,int bottom) {
        if (view == null) return;
        try {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.MarginLayoutParams(0, 0);
            }
            lp.leftMargin=left;
            lp.rightMargin=right;
            lp.topMargin=top;
            lp.bottomMargin=bottom;
            view.setLayoutParams(lp);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }










    /***
     * text Span color
     * @param textView
     * @param colorResId
     * @param begin
     * @param end
     */
    public static void setTextSpanColor(TextView textView,int colorResId,int begin,int end){
        int color=getColorByResId(colorResId);
        CharSequence text=textView.getText();
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(color);
        SpannableString s=new SpannableString(text);
        s.setSpan(colorSpan,begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(s);
    }

    /***
     * 设置字体大小
     * @param textView
     * @param textSizeDp
     * @param begin
     * @param end
     */
    public static void setTextSpanSize(TextView textView,int textSizeDp,int begin,int end){
        CharSequence text=textView.getText();
        AbsoluteSizeSpan absoluteSizeSpan=new AbsoluteSizeSpan(textSizeDp,true);
        SpannableString s=new SpannableString(text);
        s.setSpan(absoluteSizeSpan,begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(s);
    }
    /***
     * text Span color
     * @param textView
     * @param begin
     * @param end
     */
    public static void setTextSpanClick(TextView textView, final KKViewOnclickListener wzViewOnclickListener, int begin, int end){
        CharSequence text=textView.getText();
        ClickableSpan clickableSpan=new ClickableSpan(){
            @Override
            public void onClick(View widget) {
                try {
                    wzViewOnclickListener.onClickKK(widget);
                }catch (Exception e){
                    LogTool.ex(e);
                }
            }
        };
        SpannableString s=new SpannableString(text);
        s.setSpan(clickableSpan,begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(s);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    /***
     * 设置不要下划线
     *
     * @param tv_huangye_phone
     */
    public static void setTextSpanNotUnderLine(final TextView tv_huangye_phone) {
        UnderlineSpan mNoUnderlineSpan = new UnderlineSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
               // ds.setColor(tv_huangye_phone.getTextColors().getDefaultColor());
            }
        };
        if (tv_huangye_phone.getText() instanceof Spannable) {
            Spannable s = (Spannable) tv_huangye_phone.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }
    }

    /***
     * 获取textview  是否有省略号
     * tv_zhuye_jianjie.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { 在这个里面用
     */
    public static boolean getTextViewIsEllipsis(TextView tv) {
        try {
            return getTextViewIsEllipsisImp(tv,0);
        }catch (Exception e){
            LogTool.ex(e);
        }
        return  false;
    }
    /***
     * 获取textview  是否有省略号 用于item里面，需要传入textview 的宽度
     */
    public static boolean getTextViewIsEllipsis(TextView tv,int width) {
        try {
            return getTextViewIsEllipsisImp(tv,View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST));
        }catch (Exception e){
            LogTool.ex(e);
        }
        return  false;
    }
    /***
     * 获取textview  是否有省略号
     */
    public static boolean getTextViewIsEllipsisImp(TextView tv,int widthMeasure) {
        try {
            Layout l = tv.getLayout();
            if(l==null){
                tv.measure( widthMeasure,0);
                l = tv.getLayout();
            }
            if (l != null) {
                int lines = l.getLineCount();
                if (lines > 0) {
                    if (l.getEllipsisCount(lines - 1) > 0) {
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
        return  false;
    }

    public static ViewGroup getParentView(View child) {
        if(child!=null){
            return (ViewGroup) child.getParent();
        }
        return new RelativeLayout(AppTool.currActivity);
    }
    public static Rect getTextWidthHeight(String text,int textSize){
        text=StringTool.getNotNullText(text);
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
    /***
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight = -1;
//获取status_bar_height资源的ID
        int resourceId = AppTool.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = AppTool.getApplication().getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;

    }

    public static void setSoftInputModeSpan(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    public static void setSoftInputModeReSize(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    /***
     * 绑定 复制按钮
     * @param btnFuZhi
     * @param textView
     */
    public static void bindFuZhi(View btnFuZhi, final TextView textView){
        if(btnFuZhi==null||textView==null)return;
        bindFuZhi(btnFuZhi,textView.getText().toString());
    }
    /***
     * 绑定 复制按钮
     * @param btnFuZhi
     */
    public static void bindFuZhi(View btnFuZhi, final String text){
        if(btnFuZhi==null||StringTool.isEmpty(text))return;
        btnFuZhi.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                ClipboardManager cm = (ClipboardManager) AppTool.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(text);
                CommonTool.showToast("复制成功！");
            }
        });
    }

    /**
     * 设置状态栏透明
     * @param activity
     */
    public static void setStarusBarTransparent(Activity activity){
        try {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().getDecorView().setFitsSystemWindows(true);
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    /***
     * 设置密码显示隐藏
     * @param et_pwd  密码控件
     * @param imgv_eye 显示隐藏Imageview
     * @param drawableHideId  隐藏图标
     * @param drawableShowId
     */
    public static void togglePwdVisible(EditText et_pwd, ImageView imgv_eye, int drawableHideId, int drawableShowId){
        if (PasswordTransformationMethod.getInstance().equals(et_pwd.getTransformationMethod())) {
            //设置EditText的密码为可见的
            et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ImgTool.loadImage(drawableShowId,imgv_eye);
            et_pwd.setSelection(et_pwd.getText().length());
        } else {
            //设置密码为隐藏的
            et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ImgTool.loadImage(drawableHideId,imgv_eye);
            et_pwd.setSelection(et_pwd.getText().length());
        }
    }

}
