package utils.kkutils.common;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;


public class SaoMaTool {
    public static void startSaoMa(Fragment fragment) {
        IntentIntegrator.forSupportFragment(fragment).setPrompt("请将条码置于取景框内扫描。\n\n").setOrientationLocked(false).initiateScan();
        //IntentIntegrator.forSupportFragment(fragment).setOrientationLocked(false).setCaptureActivity(CaptureActivity.class).initiateScan();
    }

    public static interface OnSaoMaSuccess{
        void onSuccess(String text);
    }
    public static void parseResult(int requestCode, int resultCode, Intent intent,OnSaoMaSuccess onSaoMaSuccess) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String result = scanResult.getContents();
            if (StringTool.notEmpty(result)) {
               if(onSaoMaSuccess!=null)onSaoMaSuccess.onSuccess(result);
            }
        }
    }
}
