package utils.wzutils.ui.zhuanpan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.wzutils.ImgTool;
import utils.wzutils.common.CollectionsTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;

public class ZhuanPanViewWithText extends ZhuanPanView {
    public ZhuanPanViewWithText(Context context) {
        super(context);
        initNew();
    }

    public ZhuanPanViewWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNew();
    }

    public ZhuanPanViewWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNew();
    }
    Paint paint=new Paint();
    HashMap<String ,Bitmap>bitmapHashMap;

    public void initNew(){
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#333333"));
        paint.setTextSize(CommonTool.dip2px(12));
        paint.setFakeBoldText(true);

        //setData(ZhuanPanData.initTest());//设置测试数据， 调试的时候可以打开
    }


    public static class ZhuanPanData{
        public String name;
        public String imgUrl;

        public ZhuanPanData(String name, String imgUrl) {
            this.name = name;
            this.imgUrl = imgUrl;
        }

        public static   List<ZhuanPanData> initTest(){
            List<ZhuanPanData> zhuanPanDataList=new ArrayList<>();
            for(int i=0;i<10;i++){
                ZhuanPanData zhuanPanData=new ZhuanPanData("test"+i, TestData.getTestImgUrl(i));
                zhuanPanDataList.add(zhuanPanData);
            }
            return zhuanPanDataList;
        }
    }
    List<ZhuanPanData> zhuanPanDataList=new ArrayList<>();
    public void setData(List<ZhuanPanData> list){
        bitmapHashMap=new HashMap<>();
        if(CollectionsTool.isEmptyList(list))return;
        this.zhuanPanDataList=list;
        for(ZhuanPanData zhuanPanData:list){
            final String url=zhuanPanData.imgUrl;
            Glide.with(this)
                    .asBitmap()
                    .apply(RequestOptions.circleCropTransform())
                    .load(ImgTool.convertSrc(url, CommonTool.dip2px(50), CommonTool.dip2px(50)))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            bitmapHashMap.put(url,resource);

                            imageViewBg.postInvalidate();

                        }
                    });
        }


    }
    @Override
    public void initImageViewBg() {



        imageViewBg=new android.support.v7.widget.AppCompatImageView(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if(CollectionsTool.isEmptyList(zhuanPanDataList))return;


                canvas.save();
                for(int i=0;i<zhuanPanDataList.size();i++){


                    ZhuanPanData zhuanPanData = zhuanPanDataList.get(i);

                    {//画图
                        Bitmap bitmap=bitmapHashMap.get(zhuanPanData.imgUrl);
                        if(bitmap!=null){
                            int w= CommonTool.dip2px(50);
                            int x=(canvas.getWidth()-w)/2;

                            int y= CommonTool.dip2px(27);
                            Rect rect=new Rect(x,y,x+w,y+w);
                            Rect src=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
                            canvas.drawBitmap(bitmap,src,rect,paint);
                        }

                    }
                    {//画字
                        String text=zhuanPanData.name;
                        int w= UiTool.getTextWidthHeight(text, (int) paint.getTextSize()).right;
                        int x=(canvas.getWidth()-w)/2;
                        int y= CommonTool.dip2px(90);

                        canvas.drawText(text,x,y,paint);

                    }
                    canvas.rotate(360/zhuanPanDataList.size(),canvas.getWidth()/2,canvas.getHeight()/2);

                }
                canvas.restore();
            }
        };

    }



}
