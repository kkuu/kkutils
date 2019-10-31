package utils.kkutils.zhifu;

import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;

import com.maning.pswedittextlibrary.MNPasswordEditText;

import utils.kkutils.R;
import utils.kkutils.common.LayoutInflaterTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.http.HttpRequest;
import utils.kkutils.http.HttpUiCallBack;
import utils.kkutils.json.JsonDataParent;
import utils.kkutils.parent.KKParentActivity;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.dialog.DialogTool;

public class KKZhiFuUiTool {


    /***
     * 接口使用这个， 全局判断需要输入密码的地方
     * @param httpRequest
     * @param showGoolge
     * @param callBack
     * @param <T>
     */
    public <T extends JsonDataParent> void sendPwd(final HttpRequest httpRequest, boolean showGoolge, final KKParentFragment xiugaiMima, final KKParentFragment xiugGaiGoole, final Class<T> clzz, final HttpUiCallBack<T> callBack) {
        KKParentActivity.hideWaitingDialogStac();
        showZhiFuMiMa(showGoolge, new KKZhiFuUiTool.ZhiFuInterface() {
            @Override
            public void zhifu(final Dialog dialogZhiFuMiMa, String password, String captcha) {
                httpRequest.addQueryParams("password",password);
                httpRequest.addQueryParams("captcha",captcha);
                httpRequest.removeParams("sign");
                KKParentActivity.showWaitingDialogStac("");
                httpRequest.send(clzz, new HttpUiCallBack<T>() {
                    @Override
                    public void onSuccess(T data) {
                        KKParentActivity.hideWaitingDialogStac();
                        try {
                            if(callBack!=null){
                                callBack.onSuccess(data);
                            }
                        }catch (Exception e){
                            LogTool.ex(e);
                        }
                        if(data.isDataOk()){
                            dialogZhiFuMiMa.dismiss();
                        }
                    }
                });
            }
        },xiugaiMima,xiugGaiGoole);
    }



    /***
     * 显示支付密码弹框
     * @param zhiFuInterface
     * @return
     */
    public  Dialog showZhiFuMiMa(final boolean showGoogle, final ZhiFuInterface zhiFuInterface, final KKParentFragment xiugaiMima, final KKParentFragment xiugGaiGoole){
        if(zhiFuInterface==null) return null;

        View parent= LayoutInflaterTool.getInflater(5, R.layout.kk_zhifu_pwd).inflate();
        final Dialog dialog = DialogTool.initBottomDialog(parent);

        if(showGoogle){
            parent.findViewById(R.id.kk_tv_zhifu_mima_label).setVisibility(View.VISIBLE);
            parent.findViewById(R.id.kk_vg_google_pwd).setVisibility(View.VISIBLE);
        }else {
            parent.findViewById(R.id.kk_tv_zhifu_mima_label).setVisibility(View.INVISIBLE);
            parent.findViewById(R.id.kk_vg_google_pwd).setVisibility(View.GONE);
        }
        //parent.findViewById(R.id.tv_zhifu_mima_label);

        parent.findViewById(R.id.kk_tv_go_zhaohui_zhifu_mima).setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
               if(xiugaiMima!=null)xiugaiMima.go();
            }
        });

        parent.findViewById(R.id.kk_tv_go_bangding_google_mima).setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                if(xiugGaiGoole!=null)xiugGaiGoole.go();
            }
        });

        final MNPasswordEditText et_zhifu_pwd=parent.findViewById(R.id.kk_et_zhifu_pwd);
        final MNPasswordEditText et_zhifu_google_pwd=parent.findViewById(R.id.kk_et_zhifu_google_pwd);

        final String [] pwds=new String[2];

        final Runnable end=new Runnable() {
            @Override
            public void run() {

                String pwd=pwds[0];
                String googlePwd=pwds[1];
                if(StringTool.notEmpty(pwd)){
                    if(showGoogle){
                        if(StringTool.notEmpty(googlePwd))
                        {
                            zhiFuInterface.zhifu(dialog,pwd,googlePwd);
                            //dialog.dismiss();

                        }
                    }else {
                        zhiFuInterface.zhifu(dialog,pwd,googlePwd);
                        //dialog.dismiss();

                    }
                }

            }
        };
        et_zhifu_pwd.setText("");
        et_zhifu_pwd.setOnTextChangeListener(new MNPasswordEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String s, boolean isComplete) {
                if(isComplete){
                    pwds[0]=s;
                    et_zhifu_google_pwd.requestFocus();
                    end.run();
                }else {
                    pwds[0]="";
                }
            }
        });

        et_zhifu_google_pwd.setText("");
        et_zhifu_google_pwd.setOnTextChangeListener(new MNPasswordEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String s, boolean isComplete) {
                if(isComplete){
                    pwds[1]=s;
                    end.run();
                }else {
                    pwds[1]="";
                }
            }
        });
        et_zhifu_pwd.requestFocus();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        dialog.show();
        return dialog;
    }





    public static interface ZhiFuInterface {
        public void zhifu(Dialog dialogZhiFuMiMa, String password, String captcha);
    }

}
