package kk.kktools;

import android.app.Application;

import com.xiaomi.mipush.sdk.MiPushClient;

import utils.kkutils.AppTool;
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppTool.init(this,true);
        MiPushClient.registerPush(this,"2882303761517544733","5101754477733");
        MiPushClient.setAlias(this,"kk",null);
    }
}
