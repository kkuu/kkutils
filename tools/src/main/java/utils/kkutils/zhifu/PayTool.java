package utils.kkutils.zhifu;

import android.app.Activity;
import android.content.Context;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import utils.kkutils.AppTool;
import utils.kkutils.JsonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.thread.ThreadTool;

/**
 * 支付文档
 *
 * https://docs.open.alipay.com/54/104509
 * https://docs.open.alipay.com/204/105296/
 *
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419319167&token=&lang=zh_CN
 *
 *
 */
public class PayTool {

    /***
     * 需要把 alipaySdk_15.5.9_20181123210601.aar 添加到实际工程libs 下面
     *
     * 增加 implementation fileTree(include: ['*.jar','*.aar'], dir: 'libs')
     *
     * @param activity
     * @param payInfo
     * @param onPayResultListener
     */
    public static void pay_zhifubao(final Activity activity, final String payInfo, final OnPayResultListener onPayResultListener) {
        LogTool.s("支付宝参数： " + payInfo);
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                PayResult payResult = new PayResult(result);
                /**
                 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                 * docType=1) 建议商户依赖异步通知
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                LogTool.s("支付宝支付结果： " + result + "   " + resultInfo + "  " + resultStatus);

                if ("9000".equals(resultStatus) || "8000".equals(resultStatus)) {
                    LogTool.s("支付宝支付成功");
                    try {
                        if (onPayResultListener != null) onPayResultListener.onSuccess("支付宝支付成功");
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }

                } else {
                    if ("6001".equals(resultStatus)) {
                        LogTool.s("支付宝支付取消");
                    } else {
                        LogTool.s("支付宝支付失败");
                        try {
                            if (onPayResultListener != null) onPayResultListener.onFailure("支付宝支付失败");
                        } catch (Exception e) {
                            LogTool.ex(e);
                        }
                    }

                }
            }
        });
    }

    /****
     * 微信支付
     */
    public static IWXAPI pay_weixin(Context context, String appid, String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String sign, final OnPayResultListener onPayResultListener) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, null);
        if (!iwxapi.isWXAppInstalled()) {
            new android.app.AlertDialog.Builder(context).setTitle("提示!")
                    .setMessage("未安装微信客户端，请下载最新版微信再来选择微信支付").show();
            return iwxapi;
        }
        {//用于支付结果通知的
            WXPayEntryActivity.APP_ID = appid;
            WXPayEntryActivity.iwxapiEventHandler = new IWXAPIEventHandler() {
                @Override
                public void onReq(BaseReq baseReq) {
                }

                @Override
                public void onResp(BaseResp resp) {
                    String msg = "";
                    LogTool.s("微信支付结果 :" + JsonTool.toJsonStr(resp));
                    if (resp.errCode == BaseResp.ErrCode.ERR_OK)//支付成功
                    {
                        msg = "微信支付成功";
                        try {
                            if (onPayResultListener != null) onPayResultListener.onSuccess(msg);
                        } catch (Exception e) {
                            LogTool.ex(e);
                        }
                    } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                        msg = "取消了支付";
                    } else {
                        msg = "微信支付失败";
                        try {
                            if (onPayResultListener != null) onPayResultListener.onFailure(msg);
                        } catch (Exception e) {
                            LogTool.ex(e);
                        }
                    }
                }
            };
        }

//        {//1.9的测试数据
//            timeStamp="1469261112";
//            sign="0276230B8CC4C4523AD1EBBE6509D355";
//            partnerId="1301192501";
//            nonceStr="819e3d6c1381eac87c17617e5165f38c";
//            prepayId="wx201607231605128c73e4b9140294938974";
//            packageValue="Sign=WXPay";
//            appid="wxc5c9176d6241e00d";
//        }
//
//        {//2.0的测试数据
//
//            timeStamp="146926198724";
//            sign="5A425F8A0F9A141BD63FEE77A44934D2";
//            partnerId="1301192501";
//            nonceStr="dBaop0clLVce7tEH";
//            prepayId="wx20160723161946191764e8ef0904955236";
//            packageValue="Sign=WXPay";
//            appid="wxc5c9176d6241e00d";
//
//        }


        iwxapi.registerApp(appid);
        PayReq request = new PayReq();
        request.appId = appid;
        request.partnerId = partnerId;
        request.prepayId = prepayId;
        request.packageValue = packageValue;
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;
        request.sign = sign;
        boolean ok=iwxapi.sendReq(request);
        LogTool.s("微信参数:" + JsonTool.toJsonStr(request));
        return iwxapi;
    }



    public static final int resultCode_success=1999;
    public static final int resultCode_failure=1998;


    public static abstract class OnPayResultListener {
        void onSuccess(final String msg){
            AppTool.uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        onSuccessUi(msg);
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }
            });
        }

        void onFailure(final String msg){
            AppTool.uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        onFailureUi(msg);
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }
            });
        };

        public abstract void onSuccessUi(String msg);

        public abstract void onFailureUi(String msg);
    }

}
