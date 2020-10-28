package utils.kkutils.parent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.Serializable;

import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import utils.kkutils.AppTool;
import utils.kkutils.R;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ViewTool;

/**
 * Created by kk on 2017/3/23.
 */

public class KKParentActivity extends FragmentActivity implements Serializable {
    ProgressDialog progressDialog;


    /***
     * 透明状态栏
     */
    public void setTouMingStatusBar() {
        setStatusBarColor(Color.TRANSPARENT);
    }

    /***
     * 透明状态栏 可以自己设置颜色
     */
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            ViewTool.initViews(getWindow().getDecorView(), this, null);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public boolean saveIntance = false;//有些手机 fragment 的save不了，会白屏，最好不要开启，特别是首页tab 界面

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (saveIntance) super.onSaveInstanceState(outState);
    }

    /***
     * 半透明状态栏
     */
    public void setBanTouMingStatusBar(boolean bantouming) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            if (bantouming) window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /***
     * 显示一个弹出框
     *
     * @param msg
     */
    public static void showWaitingDialogStac(String msg) {
        try {
            if (AppTool.currActivity instanceof KKParentActivity) {
                ((KKParentActivity) AppTool.currActivity).showWaitingDialog("");
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 隐藏当前弹出框
     */
    public static void hideWaitingDialogStac() {
        try {
            if (AppTool.currActivity instanceof KKParentActivity) {
                ((KKParentActivity) AppTool.currActivity).hideWaitingDialog();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void initData() {

    }

    /***
     * 显示一个弹出框
     *
     * @param msg
     */
    public void showWaitingDialog(String msg) {
        try {
            if (TextUtils.isEmpty(msg)) {
                msg = getString(R.string.dengdai);
            }
            final String msgFnal = msg;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Activity activity = KKParentActivity.this;
                        if (activity.isFinishing()) return;
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.setMessage(msgFnal);
                        } else {
                            progressDialog = new ProgressDialog(activity);
                            progressDialog.setMessage(msgFnal);
                        }
                        progressDialog.show();
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            });
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 隐藏当前弹出框
     */
    public void hideWaitingDialog() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            });

        } catch (Exception e) {
            LogTool.ex(e);
        }
    }


    public static Fragment currentFragment;

    /***
     * 主页切换的，平时不用
     * @param fragment
     * @param fragment_container_id
     * @param isSingleInParent  当前界面是否只有这一个fragment ，  默认只有一个， 但是 主要有tab 页的时候就要传入false， 方便
     */
    public synchronized void setFragment(Fragment fragment, int fragment_container_id, boolean isSingleInParent) {
        try {
            if (currentFragment == fragment) return;


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!fragment.isAdded()) {
                transaction.add(fragment_container_id, fragment);
            } else {
                if (!fragment.isVisible()) transaction.show(fragment);
            }

            {
//-----------------------------操作静态currentFragment 的时候小心， 可能  currentFragment 的 FragmentManager 不是当前这个了， 下面会报错-------------------------------------------
                try {
                    if (currentFragment != null && currentFragment != fragment) {
                        if (currentFragment.getFragmentManager() == getSupportFragmentManager())
                            transaction.hide(currentFragment);
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }


            //设置当前界面是否只有一个fragment,  主要是针对tab里面的faragment 设置 resume 时机使用
            if (fragment instanceof KKParentFragment) {
                ((KKParentFragment) fragment).setSingleInParent(isSingleInParent);
            }


            currentFragment = fragment;
            transaction.commitNow();
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }
}
