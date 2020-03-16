package utils.kkutils.ui.webview;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import utils.kkutils.AppTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.LogTool;
import utils.kkutils.common.UiTool;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/***
 * webview  通用bug
 * 1. 会导致系统语言重置
 * {@link utils.kkutils.common.LanguageTool}
 * 2.不能用 LayoutInflaterTool 加载界面， 也就是不能用 子线程加载， 部分手机会崩溃，或者白屏
 *
 *
 */
public class X5WebView extends WebView {
	TextView title;
	private WebViewClient client = new KKX5WebViewClientDefault();
	public static class KKX5WebViewClientDefault extends WebViewClient{
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			try {
				//处理intent协议
				if (url.startsWith("intent://")) {
					Intent intent;
					try {
						intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
						intent.addCategory("android.intent.category.BROWSABLE");
						intent.setComponent(null);
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
							intent.setSelector(null);
						}
						List<ResolveInfo> resolves = AppTool.currActivity.getPackageManager().queryIntentActivities(intent,0);
						if(resolves.size()>0){
							startActivity(intent);
						}
						return true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 处理自定义scheme协议
				if (!url.startsWith("http")) {
					Log.e("yxx","处理自定义scheme-->" + url);
					try {
						// 以下固定写法
						final Intent intent = new Intent(Intent.ACTION_VIEW,
								Uri.parse(url));
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
					} catch (Exception e) {
						// 防止没有安装的情况
						e.printStackTrace();
						CommonTool.showToast("您所打开的第三方App未安装！");
					}
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return super.shouldOverrideUrlLoading(view,url);
		}


	}

	/**
	 * 设置自适应高 避免加载闪烁 需要手动调用
	 */
	@Deprecated
	public void setWrapContent(){
//		UiTool.setWH(this, CommonTool.getWindowSize().x,10000);
//		this.setWebViewClient(new KKX5WebViewClientDefault(){
//			@Override
//			public void onPageFinished(WebView webView, String s) {
//				super.onPageFinished(webView, s);
//				LogTool.s("onPageFinished"+"  "+s);
//				int w=getLayoutParams()==null? CommonTool.getWindowSize().x:getLayoutParams().width;
//				UiTool.setWH(webView,w, ViewGroup.LayoutParams.WRAP_CONTENT);
//			}
//
//		});
	}
	@SuppressLint("SetJavaScriptEnabled")
	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		init();

	}

    public X5WebView(Context arg0) {
        super(arg0);
        init();
    }
    public void init(){
        setBackgroundColor(85621);
        this.setWebViewClient(client);
        initWebViewSettings();
        this.getView().setClickable(true);
    }






	private void initWebViewSettings() {
		WebSettings webSetting = this.getSettings();
		webSetting.setAllowFileAccess(true);//访问文件
		webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//混合协议，  http  https


		webSetting.setDisplayZoomControls(false);
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(true);
		// webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		// webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
		// settings 的设计
	}

	@Override
	public void loadUrl(String s) {
		LogTool.s("webview 加载了Url：  "+s);
		super.loadUrl(s);

	}

	@Override
	public void loadUrl(String s, Map<String, String> map) {
		LogTool.s("webview 加载了Url：  "+s);
		super.loadUrl(s, map);
	}
	//	@Override
//	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//		boolean ret = super.drawChild(canvas, child, drawingTime);
//		canvas.save();
//		Paint paint = new Paint();
//		paint.setColor(0x7fff0000);
//		paint.setTextSize(24.f);
//		paint.setAntiAlias(true);
//		if (getX5WebViewExtension() != null) {
//			canvas.drawText(this.getContext().getPackageName() + "-pid:"
//					+ android.os.Process.myPid(), 10, 50, paint);
//			canvas.drawText(
//					"X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
//					100, paint);
//		} else {
//			canvas.drawText(this.getContext().getPackageName() + "-pid:"
//					+ android.os.Process.myPid(), 10, 50, paint);
//			canvas.drawText("Sys Core", 10, 100, paint);
//		}
//		canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
//		canvas.drawText(Build.MODEL, 10, 200, paint);
//		canvas.restore();
//		return ret;
//	}



	public static void init(Application application){
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
				Log.d("app", " onViewInitFinished is " + arg0);
			}

			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(application,  cb);
	}

}
