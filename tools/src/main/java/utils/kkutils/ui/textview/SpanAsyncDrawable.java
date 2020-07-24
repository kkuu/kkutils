package utils.kkutils.ui.textview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.gif.GifDrawable;

import java.util.ArrayList;
import java.util.List;

import utils.kkutils.common.LogTool;


/**
 *  异步专用drawable
 */
public class SpanAsyncDrawable extends Drawable implements Drawable.Callback{

    private Drawable mDrawable;

    @Override
    public void draw(Canvas canvas) {
        if (mDrawable != null) {
            mDrawable.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        if (mDrawable != null) {
            mDrawable.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (mDrawable != null) {
            mDrawable.setColorFilter(cf);
        }
    }

    @Override
    public int getOpacity() {
        if (mDrawable != null) {
            return mDrawable.getOpacity();
        }
        return 0;
    }

    public void setDrawable(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = drawable;
        drawable.setCallback(this);
        setBounds(drawable.getBounds());
    }


    @Override
    public void invalidateDrawable(Drawable who) {
        for (Callback callback : callbackList) {
            try {
                callback.invalidateDrawable(who);
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
        if (getCallback() != null) {
            getCallback().invalidateDrawable(who);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        for (Callback callback : callbackList) {
            try {
                callback.scheduleDrawable(who, what, when);
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
        if (getCallback() != null) {
            getCallback().scheduleDrawable(who, what,when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        for (Callback callback : callbackList) {
            try {
                callback.unscheduleDrawable(who, what);
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
        if (getCallback() != null) {
            getCallback().unscheduleDrawable(who, what);
        }
    }
    public List<Drawable.Callback> callbackList=new ArrayList<>();
    public void addCallBack(Drawable.Callback callback){
        if(callbackList.size()>50){
            callbackList.clear();
        }
        if(!callbackList.contains(callback)){
            callbackList.add(callback);
        }

    }
}
