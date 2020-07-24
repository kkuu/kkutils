package utils.kkutils.ui.video.douyin2;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


import utils.kkutils.R;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.video.douyin2.imp.DouYinData;
import utils.kkutils.ui.video.douyin2.imp.DouYinViewPagerAdapter;

/***
 * 直接复制当前包到项目 根 包下面
 */
public class DouYinFragment2 extends KKParentFragment {


    @Override
    public int initContentViewId() {
        return 0 ;
    }
    RelativeLayout relativeLayout;
    @Override
    public View initContentView() {
        relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return relativeLayout;
    }
    DouYinTool douYinTool;
    @Override
    public void initData() {

        StatusBarTool.setStatusBarColor(getActivity(), Color.TRANSPARENT, false, true);

        List<TiktokBean> data = getTiktokDataFromAssets(getContext());
        TiktokBean data1=new TiktokBean();
        data1.coverImgUrl="http://p3-dy.byteimg.com/large/tos-cn-p-0015/102bc745c6984e5fb962f90e8cd4ab0f.jpeg";
        data1.videoDownloadUrl="https://aweme.snssdk.com/aweme/v1/play/?video_id=v0300f5e0000bmir1gu9taafbguk4m6g&line=0&ratio=540p&watermark=1&media_type=4&vr_type=0&improve_bitrate=0&logo_name=aweme";

        data.add(0, data1);
        douYinTool =new DouYinTool(relativeLayout,new DouYinViewPagerAdapter(data){


            @Override
            public void initItem(ViewHolder viewHolder, DouYinData douYinData, int position) {

            }

            @Override
             public int getLayoutId() {
                 return R.layout.kk_item_tik_tok;
             }

             @Override
             public int getControlViewId() {
                 return R.id.tiktok_View;
             }

             @Override
             public int getPlayerContainerId() {
                 return R.id.container;
             }
         });

        douYinTool.startPlayPosition(50);
    }


    static List<TiktokBean> tiktokData;
    public static List<TiktokBean> getTiktokDataFromAssets(Context context) {
        try {
            if (tiktokData == null) {
                InputStream is = context.getAssets().open("tiktok_data");
                int length = is.available();
                byte[] buffer = new byte[length];
                is.read(buffer);
                is.close();
                String result = new String(buffer, Charset.forName("UTF-8"));
                tiktokData = TiktokBean.arrayTiktokBeanFromData(result);
            }
            return new ArrayList<>(tiktokData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }



}
