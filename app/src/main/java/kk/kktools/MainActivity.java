package kk.kktools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import utils.wzutils.AppTool;
import utils.wzutils.HttpTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.PwdTool;
import utils.wzutils.http.HttpRequest;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppTool.init(getApplication(),true);

        findViewById(R.id.button).setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {

                EditText text=findViewById(R.id.editText);

                if(!PwdTool.isPwdOk(text.getText().toString())){
                    CommonTool.showToast("密码过于简单");
                }

            }
        });
    }
}
