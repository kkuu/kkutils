package utils.wzutils.ui.webview;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import utils.wzutils.common.StringTool;
import utils.wzutils.parent.WzParentFragment;

public class X5WebViewFragment extends WzParentFragment {
    @Override
    public int initContentViewId() {
        return 0;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
    public static void init(FragmentActivity activity, WebView webView){
        new X5WebViewFragment().initWebView(activity.getSupportFragmentManager(),webView);
    }

    public  void initWebView(FragmentManager fragmentManager, WebView webView){
        fragmentManager.beginTransaction().add(this,"web").commitAllowingStateLoss();
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onShowFileChooser(WebView webView, final ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {

                callback=valueCallback;

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                        && fileChooserParams.getAcceptTypes().length > 0) {
                    String type=fileChooserParams.getAcceptTypes()[0];
                    i.setType(type);
                }
                if(StringTool.isEmpty(i.getType())){
                    i.setType("*/*");
                }
                startActivityForResult(Intent.createChooser(i, "File Chooser"), 1);




//                Album.image(AppTool.currActivity)
//                        .singleChoice()
//                        .camera(true)
//                        .columnCount(4)
//                        .onResult(new Action<ArrayList<AlbumFile>>() {
//                            @Override
//                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
//                                List<Uri> uriList=new ArrayList<>();
//                                for(AlbumFile file:result){
//                                    uriList.add(Uri.fromFile(new File(file.getPath())));
//                                }
//                                valueCallback.onReceiveValue(uriList.toArray(new Uri[0]));
//                            }
//                        })
//                        .onCancel(new Action<String>() {
//                            @Override
//                            public void onAction(@NonNull String result) {
//                                valueCallback.onReceiveValue(null);
//                            }
//                        })
//                        .start();


                return true;
            }
        });
    }
    ValueCallback<Uri[]> callback;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(callback!=null){
            Uri[] uris = WebChromeClient.FileChooserParams.parseResult(resultCode, intent);
            callback.onReceiveValue(uris);
        }
    }
}
