package utils.kkutils.ui.webview;


import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

import java.util.ArrayList;

import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.ui.bigimage.KKBigImgListFragment;

/***
图文详情专用
 *
 */
public abstract class WebViewTuWenTool  {
	public abstract void loadTuWenDataWithBaseURL(String baseUrl, String data,
											 String mimeType, String encoding, String failUrl);
	public abstract void initShowBigImg(WebChromeClient webChromeClient);

	public   String html_start="<html><head> "
			+"<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no,minimal-ui\" />"
			+"<style>img{width:100% !important ;height:auto !important;}</style>"
			+"<style>p{font-size :17px !important;line-height:32px !important;} </sty1e>"
			+"<style>body(max- width:100% !important;}</style>"
			+"</head><body>";

	public   String html_end="</body></html>";

	public  void loadTuWen( String content) {
		content=StringTool.unicode2String(content);
		initShowBigImg(getBigImgWebChromeClient());
		try {
			content =html_start+ content+html_end;
			if (!StringTool.isEmpty(content)) {
				content=content.replace("&#x3D;","=").replace("\\&quot;","").replace("&quot;","");
				content=handData(content);
				loadTuWenDataWithBaseURL(null, content, "text/html", "utf-8", null);
			}
		}catch (Exception e){
			LogTool.ex(e);
		}
	}

	public WebChromeClient getBigImgWebChromeClient() {
		return new WebChromeClient(){
			@Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				LogTool.s(consoleMessage.message());
				try {
					String src = consoleMessage.message().replace(tuWenPreStr, "");
					new KKBigImgListFragment().go(getTuwenSrcList().indexOf(src),  getTuwenSrcList());
				} catch (Exception e) {
					LogTool.ex(e);
				}
				return true;
			}
		};
	}
	public String handData(String data) {
		data = "" + data;
		String tem="<img onclick=console.log('"+tuWenPreStr+"'+this.src)";
		data = data.replace("<img", tem);
		data = data.replace("<IMG", tem);
		tuwenSrcList = StringTool.getImgSrc(data);
		return data;
	}
	public ArrayList<String> getTuwenSrcList() {
		return tuwenSrcList;
	}
	ArrayList<String> tuwenSrcList = new ArrayList<>();
	static final String tuWenPreStr = "932847187yewqoiufaiuas8327riueh";
}
