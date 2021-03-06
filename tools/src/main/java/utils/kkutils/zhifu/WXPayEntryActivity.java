package utils.kkutils.zhifu;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import utils.kkutils.JsonTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;


/***
 * 必须与放在  com.tjg99.wxapi
 *
 * 主包目录下的 wxapi 目录下面
 *
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    public static PayTool.OnPayResultListener onPayResultListener;


    public static String APP_ID = "wx3e3070d549eb00f9";
    String msg = "";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(final BaseResp resp) {
        LogTool.s("onPayFinish, errCode = " + resp.errCode + "  " + JsonTool.toJsonStr(resp));
        if (resp.errCode == BaseResp.ErrCode.ERR_OK)//支付成功
        {

            msg = "微信支付成功";

        } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            msg = "取消了支付";
        } else {

            msg = "微信支付失败";
        }
        CommonTool.showToast(msg);
        doResp(resp);

        finish();


    }
    public void doResp(BaseResp resp){
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

}