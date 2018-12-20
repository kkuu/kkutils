package kk.kktools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import utils.wzutils.AppTool;
import utils.wzutils.HttpTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LanguageTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.PwdTool;
import utils.wzutils.http.HttpRequest;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzParentActivity;
import utils.wzutils.parent.WzViewOnclickListener;

public class MainActivity extends WzParentActivity {

    ImageView imgv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTool.init(getApplication(),true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgv=findViewById(R.id.imgv);

        ImgTool.clearCache();
        ImgTool.preLoadImage("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201312%2F17%2F105435eqfkekxfckcxc06c.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1741197912%2C1193459822%26fm%3D26%26gp%3D0.jpg",900,900);

        findViewById(R.id.button).setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {

                //a
                ImgTool.loadImage("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201312%2F17%2F105435eqfkekxfckcxc06c.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1741197912%2C1193459822%26fm%3D26%26gp%3D0.jpg",imgv);


            }
        });
    }
}
