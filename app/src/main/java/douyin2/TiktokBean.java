package douyin2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import douyin2.imp.DouYinData;
import kk.kktools.R;

public class TiktokBean implements DouYinData {
    public String coverImgUrl;
    public String videoDownloadUrl;
    public static List<TiktokBean> arrayTiktokBeanFromData(String str) {
        Type listType = new TypeToken<ArrayList<TiktokBean>>() {
        }.getType();
        return new Gson().fromJson(str, listType);
    }

    @Override
    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    @Override
    public String getVideoDownloadUrl() {
        return videoDownloadUrl;
    }

}
