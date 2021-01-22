package kk.kktools.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import kk.kktools.R;
import utils.kkutils.AppTool;
import utils.kkutils.parent.KKViewOnclickListener;

/***
 * 转场动画
 */
public class ActivityAnimation1 extends Activity {
    public static void go() {
        Intent intent=new Intent();
        intent.setClass(AppTool.currActivity,ActivityAnimation1.class);
        AppTool.currActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kk_test_anim1);

        View image=findViewById(R.id.image);
        View tv_title=findViewById(R.id.tv_title);
        image.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityAnimation1.this, Pair.create(image, "img")).toBundle();

//                bundle=ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityAnimation1.this).toBundle();

                Intent intent=new Intent();
                intent.setClass(AppTool.currActivity,ActivityAnimation2.class);
                AppTool.currActivity.startActivity(intent,bundle);
            }
        });


    }
}
