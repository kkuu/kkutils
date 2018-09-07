package utils.wzutils.common;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class SaoMaTool {
    public static void startSaoMa(Fragment fragment) {
        IntentIntegrator.forSupportFragment(fragment).setOrientationLocked(false).initiateScan();
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
