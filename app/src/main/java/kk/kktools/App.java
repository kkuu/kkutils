package kk.kktools;

import android.app.Application;

import com.xiaomi.mipush.sdk.MiPushClient;

import utils.kkutils.AppTool;
import utils.kkutils.parent.KKParentApplication;

public class App extends KKParentApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MiPushClient.registerPush(this,"2882303761517544733","5101754477733");
        MiPushClient.setAlias(this,"kk",null);
    }
}
