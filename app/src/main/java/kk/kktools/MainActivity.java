package kk.kktools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppTool.init(getApplication(),true);
        CommonTool.showToast("你好");
    }
}
