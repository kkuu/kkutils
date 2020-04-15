package kk.kktools.tuya;

import kk.kktools.R;
import utils.kkutils.ui.tuya.EditViewFragment;

public class TestEditView extends EditViewFragment {

    @Override
    public int initContentViewId() {
        return 0;
    }



    @Override
    public void initData() {
        super.initData();
        editViewCanvasView.imageViewBg.setImageResource(R.drawable.kk_bg);


    }



}
