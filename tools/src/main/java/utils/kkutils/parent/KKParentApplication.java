package utils.kkutils.parent;

import androidx.multidex.MultiDexApplication;

import utils.kkutils.AppTool;

public class KKParentApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppTool.init(this,true);
    }
}
