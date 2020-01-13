package utils.kkutils.ui.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


/**
 * abc on 2016/12/13.
 * 注意  这个方法 用了  setWebChromeClient, 所以外面不要用了。。。。
 */
public class WebViewTuWen extends WebView {
    public  WebViewTuWenTool webViewTuWenTool;
    public WebViewTuWen(Context context) {
        super(context);
        init();
    }
    public WebViewTuWen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public WebViewTuWen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        //初始化
        WebViewTool.initWebViewNormalSetting(this);
        webViewTuWenTool=new WebViewTuWenTool() {
            @Override
            public void loadTuWenDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String failUrl) {
                loadDataWithBaseURL(baseUrl, data, mimeType, encoding, failUrl);
            }
            @Override
            public void initShowBigImg(WebChromeClient webChromeClient) {
                setWebChromeClient(webChromeClient);
            }
        };
    }
    public void loadTuWen(String content){
        webViewTuWenTool.loadTuWen(content);
    }

}
