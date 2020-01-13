package utils.kkutils.ui.webview;
import android.content.Context;
import android.util.AttributeSet;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.sdk.WebChromeClient;
/***
图文详情专用
 *
 */
public class X5WebViewTuWen extends X5WebView {
	public WebViewTuWenTool webViewTuWenTool;
	public X5WebViewTuWen(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	public X5WebViewTuWen(Context arg0) {
		super(arg0);
	}
	public void init(){
		super.init();
		 webViewTuWenTool=new WebViewTuWenTool() {
			 @Override
			 public void loadTuWenDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String failUrl) {
				 loadDataWithBaseURL(baseUrl, data, mimeType, encoding, failUrl);
			 }
			 @Override
			 public void initShowBigImg(final android.webkit.WebChromeClient webChromeClient) {
				 setWebChromeClient(new WebChromeClient() {
					 @Override
					 public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
						 return webChromeClient.onConsoleMessage(new android.webkit.ConsoleMessage(consoleMessage.message(),consoleMessage.sourceId(),consoleMessage.lineNumber(), android.webkit.ConsoleMessage.MessageLevel.LOG));
					 }
				 });
			 }
		 };

	}

	public  void loadTuWen( String content) {
		webViewTuWenTool.loadTuWen(content);
	}

}
