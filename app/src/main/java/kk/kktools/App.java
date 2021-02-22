package kk.kktools;

import android.app.Application;
import android.content.Context;

import com.xiaomi.mipush.sdk.MiPushClient;

import utils.kkutils.AppTool;
import utils.kkutils.db.MapDB;
import utils.kkutils.parent.KKParentApplication;

public class App extends KKParentApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MiPushClient.registerPush(this,"2882303761517544733","5101754477733");
        setAlias(this,getAliasLocal());
    }
    public static void setAlias(Context context,String alias){
        MiPushClient.setAlias(context,alias,null);
        MapDB.saveObj(true,"alias",alias);
    }
    public static String getAliasLocal(){
        return MapDB.loadObjByDefault("alias",String.class,"kk");
    }
}
