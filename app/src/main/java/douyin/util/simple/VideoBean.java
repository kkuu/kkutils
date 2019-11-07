package douyin.util.simple;

/**
 * Created by Devlin_n on 2017/6/1.
 */

public class VideoBean {
    private String title;
    private String url;
    private String thumb;
    private Object data;

    public VideoBean(String title,  String thumb,String url, Object data) {
        this.title = title;
        this.url = url;
        this.thumb = thumb;
        this.data = data;
    }
    public VideoBean(String title,  String thumb,String url) {
        this.title = title;
        this.url = url;
        this.thumb = thumb;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
