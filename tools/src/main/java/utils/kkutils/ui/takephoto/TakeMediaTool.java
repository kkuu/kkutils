package utils.kkutils.ui.takephoto;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import utils.kkutils.R;
import utils.kkutils.common.CollectionsTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.ui.takephoto.tool.GlideEngine;

public class TakeMediaTool {


    /***
     * 选取图片并裁剪
     * @param fragment
     *
     *  Durban.parseResult(data);
     */
    public static void pickSingleImg(final Fragment fragment,boolean crop,OnImageChooseListener onImageChooseListener){
        pick(fragment,PickType.image,1,crop,null,onImageChooseListener);
    }

    public static void pick(final Fragment fragment,PickType pickType,int maxSelectNum,boolean crop, ArrayList<String> selectedList, TakeMediaTool.OnImageChooseListener onImageChooseListener){
        int type = pickType.getType();
        ArrayList<LocalMedia> listDefaultSelected=new ArrayList<>();
        if(selectedList!=null){//默认选中
            for(String data:selectedList){
                LocalMedia localMedia = mapChoose.get(data);
                if(localMedia!=null){
                    listDefaultSelected.add(localMedia);
                }
            }
        }

        int selectionMode;
        if(maxSelectNum<2){
            selectionMode = PictureConfig.SINGLE;
        }else {
            selectionMode=PictureConfig.MULTIPLE;
        }


        PictureSelector.create(fragment)
                .openGallery(type)
                .theme(R.style.picture_WeChat_style)
                .isWeChatStyle(true)

                .selectionMode(selectionMode)

                .loadImageEngine(GlideEngine.createGlideEngine())
                .selectionMedia(listDefaultSelected)
                .maxSelectNum(maxSelectNum)
                .enableCrop(crop)
                .compress(true)
                .compressQuality(80)
                .minimumCompressSize(100)//// 小于100kb的图片不压缩
//                .cropImageWideHigh(512,512)
                .withAspectRatio(1,1)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // onResult Callback
                        TakeMediaTool.notifyChoose(crop,result,onImageChooseListener);
                    }
                    @Override
                    public void onCancel() {
                        // onCancel Callback
                    }
                });

    }



    public static enum PickType{
        image,video,all;
        public int getType(){
            int type = PictureMimeType.ofImage();
            if(this==PickType.all){
                type= PictureMimeType.ofAll();
            }
            if(this==PickType.image){
                type= PictureMimeType.ofImage();
            }
            if(this==PickType.video){
                type= PictureMimeType.ofVideo();
            }

            return type;
        }
    }
    static Map<String,LocalMedia> mapChoose=new HashMap<>();
    private static void notifyChoose(boolean crop,List<LocalMedia> result, OnImageChooseListener onImageChooseListener){
        List<String> resultList=new ArrayList<>();
        if(onImageChooseListener!=null&& CollectionsTool.NotEmptyList(result)){
            for (LocalMedia localMedia : result) {
                String resultPath="";
                if(StringTool.notEmpty(localMedia.getCompressPath())){
                    resultPath=localMedia.getCompressPath();
                }else  if(StringTool.notEmpty(localMedia.getCutPath())){
                    resultPath=localMedia.getCutPath();
                }else  if(StringTool.notEmpty(localMedia.getAndroidQToPath())){
                    resultPath=localMedia.getAndroidQToPath();
                }else  if(StringTool.notEmpty(localMedia.getRealPath())){
                    resultPath=localMedia.getRealPath();
                }else  if(StringTool.notEmpty(localMedia.getPath())){
                    resultPath=localMedia.getPath();
                }
                resultList.add(resultPath);
                mapChoose.put(resultPath,localMedia);
            }
            onImageChooseListener.onImageChoose(resultList);
        }
    }
    public static interface OnImageChooseListener{
        public  void onImageChoose(List<String> resultList);
    }



}
