package utils.kkutils.ui.takephoto;

import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.durban.Durban;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import utils.kkutils.AppTool;
import utils.kkutils.ImgTool;


/**
 * abc on 2017/5/25.
 *
 *
 *     compile 'com.yanzhenjie:album:1.0.7'
 *
     new TakePhotoSimpleFragmentKK().addToParent(supportFragmentManager, R.id.vg_dongtai_fabu_tupian, R.layout.dongtai_fabu_tupian_item, maxSize,
 new TakePhotoFragmentKK.OnAddPhotoInitDataListenerImpDefault(R.id.imgv_add_photo, R.id.imgv_delete_photo, R.drawable.kk_send_picture_add, R.drawable.kk_send_picture_add));
 *
 */

public class TakePhotoSimpleFragmentKK extends TakePhotoFragmentKK {
    @Override
    public void showChooseDialog() {
//
//
//        {//使用  album//https://github.com/yanzhenjie/Album/blob/master/README-CN.md
//            Album.album(this)
//                    //.toolBarColor(toolbarColor) // Toolbar 颜色，默认蓝色。
//                    //.statusBarColor(statusBarColor) // StatusBar 颜色，默认蓝色。
//                    //.navigationBarColor(navigationBarColor) // NavigationBar 颜色，默认黑色，建议使用默认。
//                    .title("图库") // 配置title。
//                    .selectCount(maxSize) // 最多选择几张图片。
//                    .columnCount(3) // 相册展示列数，默认是2列。
//                    .camera(true) // 是否有拍照功能。
//                    .checkedList(datas) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
//                    .start(999); // 999是请求码，返回时onActivityResult()的第一个参数。
//        }
////



        ArrayList<AlbumFile> result=new ArrayList<>();
        for(String data:datas){
            AlbumFile albumFile=new AlbumFile();
            albumFile.setPath(data);
            result.add(albumFile);
        }
        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(maxSize)
                .checkedList(result)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        datas=new ArrayList<>();
                        for(AlbumFile file:result){
                            datas.add(file.getPath());
                        }
                        initListView();
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();

    }

    /***
     * 选取图片并裁剪
     * @param fragment
     *
     *  Durban.parseResult(data);
     */
    public static void pickImg(final Fragment fragment){
        int bg_qian_color= Color.parseColor("#eeeeee");
        Widget widget = Widget.newDarkBuilder(AppTool.getApplication())
                .title("Images") // Title.
                .statusBarColor(bg_qian_color)
                .toolBarColor(bg_qian_color)
                .navigationBarColor(bg_qian_color)
                .build();
        Album.image(fragment.getActivity()) // Image selection.
                .singleChoice()
                .widget(widget)
                .camera(true)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        String path=result.get(0).getPath();
                        Durban.with(fragment)
                                // 图片路径list或者数组。
                                .inputImagePaths(path)
                                // 裁剪图片输出的最大宽高。
                                .maxWidthHeight(512, 512)
                                // 裁剪时的宽高比。
                                .aspectRatio(1, 1)
                                // 图片压缩格式：JPEG、PNG。
                                .compressFormat(Durban.COMPRESS_JPEG)
                                // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                                .compressQuality(90)
                                .gesture(Durban.GESTURE_ALL)
                                .requestCode(1)
                                .start();
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


//
//        {//使用  album
//            if(requestCode == 999) {
//                if (resultCode == RESULT_OK) { // Successfully.
//                    // 不要质疑您的眼睛，就是这么简单。
//                    ArrayList<String> pathList = Album.parseResult(data);
//                    datas=pathList;
//                    initListView();
//                } else if (resultCode == RESULT_CANCELED) { // User canceled.
//                    // 用户取消了操作。
//                }
//            }
//        }


    }
    public static void init(){

        Album.initialize(AlbumConfig.newBuilder(AppTool.getApplication())
                .setAlbumLoader(new AlbumLoader() {
                    @Override
                    public void load(ImageView imageView, AlbumFile albumFile) {
                        ImgTool.loadImage(new File(albumFile.getPath()),imageView);
                    }
                    @Override
                    public void load(ImageView imageView, String url) {
                        ImgTool.loadImage(url,imageView);
                    }
                })
                .setLocale(Locale.CHINA)
                .build());
    }
}
