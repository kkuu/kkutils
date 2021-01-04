package kk.kktools.accessibilityTest;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

import kk.kktools.R;

/**
 * 辅助服务自动安装APP，该服务在单独进程中允许
 */
public class AutoQiangGouService extends AccessibilityService {
    private static final String TAG = "kk";
    private static final int DELAY_PAGE = 320; // 页面切换时间
    private final Handler mHandler = new Handler();

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "onServiceConnected: ");
        Toast.makeText(this, getString(R.string.qianggou_ceshi) + "开启了", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        Toast.makeText(this, getString(R.string.qianggou_ceshi) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
        // 服务停止，重新进入系统设置界面
        AccessibilityUtil.jumpToSetting(this);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "onAccessibilityEvent: " + event);


        /*
        模拟点击->自动安装，只验证了小米5s plus(MIUI 9.8.4.26)、小米Redmi 5A(MIUI 9.2)、华为mate 10
        其它品牌手机可能还要适配，适配最可恶的就是出现安装广告按钮，误点安装其它垃圾APP（典型就是小米安装后广告推荐按钮，华为安装开始官方安装）
        */
        AccessibilityNodeInfo rootNode = getRootInActiveWindow(); //当前窗口根节点
        if (rootNode == null)
            return;
        Log.i(TAG, "rootNode: " + rootNode);


        if(rootNode.findAccessibilityNodeInfosByText("已预约").size()>0){
            for (AccessibilityNodeInfo node : rootNode.findAccessibilityNodeInfosByText("已预约")) {
                if(node.getText().equals("已预约")){
                    performGlobalAction(GLOBAL_ACTION_BACK);
                }
            }
        }


        findAndClick(rootNode,"¥1499.00");
        findAndClick(rootNode,"立即购买");
        findAndClick(rootNode,"确定");
        findAndClick(rootNode,"提交订单");
//        event.recycle();
//        rootNode.recycle();
    }

    public boolean findAndClick(AccessibilityNodeInfo rootNode,String text){
        try {
            List<AccessibilityNodeInfo> nodeInfoList = rootNode.findAccessibilityNodeInfosByText(text);
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if(nodeInfo.getText().toString().equals(text)){
                    while (true){
                        if(nodeInfo.isClickable()){
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            return true;
                        }else {
                            nodeInfo=nodeInfo.getParent();
                        }
                        if(nodeInfo==rootNode)return false;
                    }
                }

            }
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return false;

    }
	@Override
    public void onInterrupt() {
    }
}