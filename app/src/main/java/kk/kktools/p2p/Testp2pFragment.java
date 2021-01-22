package kk.kktools.p2p;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Socket;
import java.util.List;

import kk.kktools.R;
import utils.kkutils.AppTool;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.common.thread.ThreadTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.takephoto.TakeMediaTool;
import utils.kkutils.ui.video.douyin2.views.KVideoViewDouble;

public class Testp2pFragment extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_p2p;
    }

    TextView tv_ip_my,tv_msg_recived,tv_remote_ip_port;
    View btn_copy,btn_connect;
    P2PTool p2PTool;
    StringBuffer msgReceived=new StringBuffer();
    @Override
    public void initData() {
        UiTool.bindFuZhi(btn_copy,tv_ip_my);
        p2PTool=new P2PTool(10000,new P2PTool.P2PListener() {
            @Override
            public void onReadMsg(Socket client, String text) {
                msgReceived.append(text);
                setText(tv_msg_recived,msgReceived.toString());
            }

            @Override
            public void onIpGet(String ip, int port) {
                setText(tv_ip_my,ip+":"+port);
            }
        });





        btn_connect.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                String text=tv_remote_ip_port.getText().toString();
                String[] split = text.split(":");
                if(split.length==2){
                    p2PTool.startP2P(split[0], Integer.valueOf("" + split[1]));
                }

            }
        });
    }
    public void setText(TextView text,String str){
        AppTool.uiHandler.post(new Runnable() {
            @Override
            public void run() {
                text.setText(str);
            }
        });
    }


}
