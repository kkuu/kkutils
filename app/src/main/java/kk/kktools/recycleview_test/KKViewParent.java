package kk.kktools.recycleview_test;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KKViewParent extends FrameLayout {
    static Thread mainThread;
    public KKViewParent(@NonNull Context context) {
        super(context);
        init();
    }

    public KKViewParent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KKViewParent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();


    }
    static {
        mainThread= Thread.currentThread();
    }
    public void init(){

       post(new Runnable() {
            @Override
            public void run() {
                if(postList.size()>0){
                    for (PostRunnable postRunnable : postList) {
                        postRunnable.run();
                    }
                    postList.clear();
                }
                getRootView().postDelayed(this,80);
            }
        });
    }

    List<PostRunnable> postList=new ArrayList<>();
    public void addRunnable(PostRunnable runnable){
        if(Thread.currentThread().equals(mainThread)){
            runnable.run();
            return;
        }
        if(!postList.contains(runnable)){
            postList.add(runnable);
        }
    }
    public abstract static class PostRunnable implements Runnable {
        public String name;

        public PostRunnable(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PostRunnable that = (PostRunnable) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
    @Override
    public void requestFitSystemWindows() {
        addRunnable(new PostRunnable("requestFitSystemWindows") {
            @Override
            public void run() {
                KKViewParent.super.requestFitSystemWindows();
            }
        });
    }

    @Override
    public void requestLayout() {
        addRunnable(new PostRunnable("requestLayout") {
            @Override
            public void run() {
                KKViewParent.super.requestLayout();
            }
        });
    }

    @Override
    public ViewParent invalidateChildInParent(final int[] location, final Rect dirty) {
        addRunnable(new PostRunnable("requestLayout") {
            @Override
            public void run() {
                KKViewParent.super.invalidateChildInParent(location,dirty);
            }
        });
        return getParent();
    }

    @Override
    public void requestTransparentRegion(final View child) {
        addRunnable(new PostRunnable("requestTransparentRegion") {
            @Override
            public void run() {
                KKViewParent.super.requestTransparentRegion(child);
            }
        });
    }

    @Override
    public void requestChildFocus(final View child, final View focused) {
        addRunnable(new PostRunnable("requestChildFocus") {
            @Override
            public void run() {
                KKViewParent.super.requestChildFocus(child,focused);
            }
        });
    }

    @Override
    public void focusableViewAvailable(final View v) {
        addRunnable(new PostRunnable("focusableViewAvailable") {
            @Override
            public void run() {
                KKViewParent.super.focusableViewAvailable(v);
            }
        });
    }

    @Override
    public void recomputeViewAttributes(final View child) {
        addRunnable(new PostRunnable("recomputeViewAttributes") {
            @Override
            public void run() {
                KKViewParent.super.recomputeViewAttributes(child);
            }
        });
    }

    @Override
    public void playSoundEffect(final int soundConstant) {
        addRunnable(new PostRunnable("playSoundEffect") {
            @Override
            public void run() {
                KKViewParent.super.playSoundEffect(soundConstant);
            }
        });
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), focused, direction);
    }
}
