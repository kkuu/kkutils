package utils.kkutils.ui.webview;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.Map;

import utils.kkutils.AppTool;
import utils.kkutils.ImgTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.StringTool;
import utils.kkutils.ui.bigimage.BigImgTool;
import utils.kkutils.ui.bigimage.KKBigImgListFragment;
import utils.kkutils.ui.bigimage.PicViewActivity;

/***
图文详情专用
 *
 */
public class X5WebViewTuWen extends X5WebView {

	public X5WebViewTuWen(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	public X5WebViewTuWen(Context arg0) {
		super(arg0);
	}
	public void init(){
		super.init();
	}
	public static final String html_start="<html><head> "
			+"<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no,minimal-ui\" />"
			+"<style>img{width:100% !important ;height:auto !important;}</style>"
			+"<style>p{font-size :17px !important;line-height:32px !important;} </sty1e>"
			+"<style>body(max- width:100% !important;}</style>"
			+"</head><body>";

	public static final String html_end="</body></html>";


	public  void loadTuWen( String content) {
		content=StringTool.unicode2String(content);
		initShowBigImg();
		try {
			content =html_start+ content+html_end;
			if (!StringTool.isEmpty(content)) {
				content=content.replace("&#x3D;","=").replace("\\&quot;","").replace("&quot;","");
				handData(content);
				this.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
			}
		}catch (Exception e){
			LogTool.ex(e);
		}
	}

	public void initShowBigImg() {
		setWebChromeClient(new WebChromeClient(){
			@Override
			public boolean onJsAlert(WebView webView, String s, String message, JsResult result) {
				try {
					String src = message.replace(tuWenPreStr, "");
					// KKParentFragment.showBigImage(tuwenSrcList.indexOf(src),tuwenSrcList);
					new KKBigImgListFragment().go(getTuwenSrcList().indexOf(src),  getTuwenSrcList());
//					ImageView imageView = new ImageView(AppTool.currActivity);
//					BigImgTool.bindShowBigImgs(imageView,);
//					ImgTool.setUrlTag(src, imageView);
//					PicViewActivity.go(imageView);
				} catch (Exception e) {
					LogTool.ex(e);
				}
				result.cancel();
				return true;
			}

			@Override
			public boolean onJsConfirm(WebView webView, String s, String s1, JsResult result) {
				result.cancel();
				return true;
			}
		});

	}
	public String handData(String data) {
		data = "" + data;
		data = data.replace("<img", "<img onclick=\"alert('" + tuWenPreStr + "'+this.src)\"");
		data = data.replace("<IMG", "<img onclick=\"alert('" + tuWenPreStr + "'+this.src)\"");
		tuwenSrcList = StringTool.getImgSrc(data);
		return data;
	}
	public ArrayList<String> getTuwenSrcList() {
		return tuwenSrcList;
	}
	ArrayList<String> tuwenSrcList = new ArrayList<>();
	static final String tuWenPreStr = "932847187yewqoiufaiuas8327riueh";
}
