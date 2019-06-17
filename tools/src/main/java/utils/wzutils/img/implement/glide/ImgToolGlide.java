package utils.wzutils.img.implement.glide;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.File;
import java.util.HashMap;

import utils.wzutils.AppTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.FileTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.thread.ThreadTool;
import utils.wzutils.img.InterfaceImgTool;

/**
 * Created by coder on 15/12/24.
 */
public class ImgToolGlide implements InterfaceImgTool {

    public int loadingDrawableId;
    public int failureDrawableId;
    HashMap<Object, Integer> error = new HashMap<>();
    RequestListener requestListener = new RequestListener() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, final Object model, final Target target, boolean b) {
            if (ImgTool.errorImg.equals(model)) {//这是一张错误图片  不用理他
                return false;
            }
            final int count = 3;
            Integer numInteger = error.get(model);
            if (numInteger == null) {
                error.put(model, count);
            }
            int num = error.get(model);
            if (num > 0) {
                error.put(model, --num);
                final int finalNum = num;
                AppTool.uiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogTool.s("加载图片失败重试次数： " + (count - finalNum) + "--   " + model);
                            target.getRequest().begin();
                        }catch (Exception e){
                            LogTool.ex(e);
                        }
                    }
                }, 2000);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(Object o, Object o2, Target target, DataSource dataSource, boolean b) {
            if(!LogTool.debug)return false;
            int count=0;
            String context="";
            try {
                if(o instanceof BitmapDrawable){
                    count=((BitmapDrawable) o).getBitmap().getByteCount()/1024;
                }
                if(target instanceof ViewTarget){
                    context=""+((ViewTarget) target).getView().getContext();
                }

            }catch (Exception e){
                LogTool.ex(e);
            }

            LogTool.s("glide：",o2,"图片占用内存大小：", count ,"k","        ",context,dataSource);
            return false;
        }

    };

    @Override
    public void init(Context context, int loadingDrawableId, int failureDrawableId) {
        if (context instanceof Application) {

            this.loadingDrawableId = loadingDrawableId;
            this.failureDrawableId = failureDrawableId;
        }

        //使用自定义的 请求管理器
        //SafeTool.setField( RequestManager.class,"requestTracker",Glide.with(AppTool.getApplication()),new utils.wzutils.img.implement.glide.WzRequestTracker());
    }

    private Object convertSrc(Object src) {
        if (("" + src).startsWith("assets://")) {
            src = Uri.parse("file:///android_asset/" + ("" + src).replace("assets://", ""));
        }
        if (src instanceof Integer) {//这个是读取资源 文件的， 不管

        } else if(src instanceof File){//读取文件的不管

        }else {
            src = "" + src;
        }


        return src;
    }

    @Override
    public void loadImage( Context context, Object src, final ImageView imageView, int width, int height) {
        RequestBuilder requestBuilder=initBuilder(context,src,imageView,width,height);
        requestBuilder.into(imageView);
    }
    public RequestBuilder initBuilder(Context context, Object src, final ImageView imageView, int width, int height){
        if(context==null&&imageView!=null)context=imageView.getContext();
        if(context==null)context=AppTool.currActivity;
        src = convertSrc(src);
        DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.ALL;
        if (src instanceof Integer) {
            diskCacheStrategy = DiskCacheStrategy.RESOURCE;//主要是本地资源文件只能是这个才能加载
        }
        RequestOptions options = new RequestOptions()
                .error(failureDrawableId)
                .placeholder(loadingDrawableId)
                .diskCacheStrategy(diskCacheStrategy);

        RequestBuilder requestBuilder=Glide.with(context).load(src)//如果不用ApplicationContext 会导致 glide 有生命周期， 那么后台加载的时候就加载不出图片。。。。
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade(100))
                .listener(requestListener);
        return requestBuilder;
    }
    @Override
    public void preloadImage(Context context, Object src, int width, int height) {
        RequestBuilder requestBuilder=initBuilder(context,src,null,width,height);
        requestBuilder.preload(width,height);
    }

    @Override
    public void clearCache() {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.get(AppTool.getApplication()).clearDiskCache();
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
        });
    }

    @Override
    public void getCacheSize(final ImgTool.GetCacheSizeListener getCacheSizeListener) {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final long size = FileTool.getFileOrDirSize(Glide.getPhotoCacheDir(AppTool.getApplication()));
                    LogTool.s("当前缓存大小： " + size);
                    if (getCacheSizeListener != null) {
                        AppTool.uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (getCacheSizeListener != null) {
                                        getCacheSizeListener.onGetSize(size,ImgTool.getSizeStr(size));
                                    }
                                } catch (Exception e) {
                                    LogTool.ex(e);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
        });
    }

    @Override
    public void pauseRequest() {
       // Glide.with(AppTool.getApplication()).pauseRequests();
    }

    @Override
    public void resumeRequest() {
       // Glide.with(AppTool.getApplication()).resumeRequests();
    }

}
