package kk.kktools.camera;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Color;
import android.view.SurfaceView;
import android.view.View;

import com.shixia.colorpickerview.ColorPickerView;
import com.shixia.colorpickerview.OnColorChangeListener;

import java.util.List;

import kk.kktools.R;
import utils.kkutils.common.CommonTool;
import utils.kkutils.common.MediaRecoderTool;
import utils.kkutils.common.PermissionTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.StatusBarTool;
import utils.kkutils.ui.dialog.DialogTool;

public class TestCameraFragmentKK extends KKParentFragment {
    @Override
    public int initContentViewId() {
        return R.layout.kk_test_camera;
    }

    SurfaceView surface;
    @Override
    public void initData() {

        PermissionTool.checkPermissionMust(new PermissionTool.PermissionListener() {
            @Override
            public void onGranted(List<String> granted) {
                MediaRecoderTool mediaRecoderTool=new MediaRecoderTool();
                mediaRecoderTool.init(surface);
//                mediaRecoderTool.initCamera();
            }
        }, Manifest.permission.CAMERA);


        surface.setScaleX(0.5f);
        surface.setScaleY(0.5f);

    }


}
