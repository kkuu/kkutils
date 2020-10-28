package utils.kkutils.parent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.widget.FrameLayout;

import java.util.List;

import utils.kkutils.AppTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.ViewTool;
import utils.kkutils.ui.StatusBarTool;

/**


  <?xml version="1.0" encoding="utf-8"?>
  <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:app="http://schemas.android.com/apk/res-auto"
  xmlns:tools="http://schemas.android.com/tools"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:orientation="vertical"
  android:background="@color/kk_bg"
  android:id="@+id/fragment_container"
  >
  </RelativeLayout>

 */
public  class KKNormalFragmentActivity extends KKParentActivity {

    /***
     * 重新设置返回事件
     */
    OnBackPressedListener onBackPressedListener;



    /***
     * 跳转到一个Fragment
     */
    public void go(Fragment fragment) {
        go(fragment, null);
    }

    /***
     * 跳转到一个Fragment
     */
    public void go(Fragment fragment, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(AppTool.currActivity, getClass());
        intent.putExtra("fragment", fragment.getClass());
        intent.putExtra("arguments", fragment.getArguments());
        AppTool.currActivity.startActivityForResult(intent, 1);
    }

    /***
     * @param toFragment     要去向的fragment
     * @param fromFragment 由哪个 fragment 启动的
     */
    public void goForResult(Fragment toFragment, Fragment fromFragment, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(AppTool.currActivity, getClass());
        intent.putExtra("fragment", toFragment.getClass());
        intent.putExtra("arguments", toFragment.getArguments());
        fromFragment.startActivityForResult(intent, requestCode);
    }



    public int parentId=ViewTool.initKey();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarTool.setStatusBarColor(this, Color.WHITE,true,false);
        parentId=ViewTool.initKey();
        FrameLayout frameLayout=new FrameLayout(this);
        frameLayout.setId(parentId);
        setContentView(frameLayout);
//        ViewTool.initViews(getWindow().getDecorView(), this, null);

        try {
            Class<Fragment> fragmentClass = (Class<Fragment>) getIntent().getSerializableExtra("fragment");

            if(fragmentClass!=null){
                Bundle arguments = getIntent().getBundleExtra("arguments");
                Fragment fragment = fragmentClass.newInstance();
                fragment.setArguments(arguments);
                removeAllFragment();
                setFragment(fragment,parentId,true);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void removeAllFragment(){
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            for(Fragment fragment:fragments){
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitNow();
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
//    public void setFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (currentFragment != null) {
//            transaction.hide(currentFragment);
//        }
//        if (!fragment.isAdded()) {
//            transaction.add(parentId, fragment);
//        } else {
//            transaction.show(fragment);
//        }
//        currentFragment = fragment;
//        transaction.commitNow();
//    }

    @Override
    public void onBackPressed() {
        if(currentFragment!=null&&currentFragment instanceof KKParentFragment){
            if(((KKParentFragment) currentFragment).onBackPressed()){
                return;
            }
        }
        if (getOnBackPressedListener() != null) {
            if (getOnBackPressedListener().onBackPressed()) {
                return;
            }
        }


//        if (getAddFragmentHasViewCount()<1) {
//            finish();
//        } else {
//            super.onBackPressed();
//        }
        super.onBackPressed();
    }

    public OnBackPressedListener getOnBackPressedListener() {
        return onBackPressedListener;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }


}
