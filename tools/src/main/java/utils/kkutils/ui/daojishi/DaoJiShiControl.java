package utils.kkutils.ui.daojishi;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import utils.kkutils.common.LogTool;

/**
 * Created by ishare on 2016/11/16.
 * 倒计时  控制器， 主要是 验证码可以用
 */
public class DaoJiShiControl extends CountDownTimer {
    View clickBtn;
    TextView tvShow;
    long currentTime = 0;
    String startString = "";//开始时显示的字符串， 结束后又会用它显示

    public DaoJiShiControl(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setView(View clickBtn, TextView tvShow) {
        this.clickBtn = clickBtn;
        this.tvShow = tvShow;
        this.startString = tvShow.getText().toString().trim();
        if (currentTime > 0) {
            onTick(currentTime);
        }
    }

    @Override
    public void onFinish() {
        currentTime = -1;
        if (clickBtn != null && tvShow != null) {
            clickBtn.setClickable(true);
            tvShow.setText(startString);
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        try {
            currentTime = millisUntilFinished;
            //LogTool.s(currentTime/1000);
            if (clickBtn != null && tvShow != null) {
                clickBtn.setClickable(false);
                tvShow.setText(millisUntilFinished / 1000 + "s");
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 停止倒计时
     */
    public void stop() {
        cancel();
        onFinish();
    }
}
