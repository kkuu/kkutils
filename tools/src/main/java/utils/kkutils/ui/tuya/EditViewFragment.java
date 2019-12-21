package utils.kkutils.ui.tuya;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.ui.dialog.DialogTool;

public class EditViewFragment extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return 0;
    }
    LinearLayout parentView;
    @Override
    public View initContentView() {
        parentView=new LinearLayout(getContext());
        parentView.setOrientation(LinearLayout.VERTICAL);
        parentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        return parentView;
    }

    @Override
    public void initData() {
        initBtns();


        editViewCanvasView = new EditViewCanvasView(getContext());
        parentView.addView(editViewCanvasView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    LinearLayout btnParent;
    public EditViewCanvasView editViewCanvasView;


    public void initBtns(){
        btnParent=new LinearLayout(getContext());
        parentView.addView(btnParent,new ViewGroup.LayoutParams(-1,-2));

        {//文字
            Button btn_wenzi=new Button(getContext());
            btn_wenzi.setText("文字");
            btn_wenzi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogTool.initNormalInputDialog("请输入文字", "", "确定", new DialogTool.OnDialogInputEnd() {
                        @Override
                        public void onInputEnd(final EditText editText) {
                            editViewCanvasView.addDragTextView(editText.getText().toString().trim());
                        }
                    },"取消","",-1).show();

                }
            });
            btnParent.addView(btn_wenzi);
        }

        {//保存
            Button btn_baocun=new Button(getContext());
            btn_baocun.setText("保存");
            btn_baocun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editViewCanvasView.baocun();
                }
            });
            btnParent.addView(btn_baocun);
        }

        {//上一步
            Button btn_back=new Button(getContext());
            btn_back.setText("上一步");
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editViewCanvasView.back();
                }
            });
            btnParent.addView(btn_back);
        }
        {//橡皮檫
            CheckBox cb_xiang_pi_cha=new CheckBox(getContext());
            cb_xiang_pi_cha.setText("橡皮檫");
            cb_xiang_pi_cha.setLayoutParams(new ViewGroup.LayoutParams(-2,-2));
            cb_xiang_pi_cha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editViewCanvasView.setXiangPiMode(isChecked);
                }
            });

            btnParent.addView(cb_xiang_pi_cha);
        }
        {//上一步
            CheckBox cb_fangda=new CheckBox(getContext());
            cb_fangda.setText("放大");
            cb_fangda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                FangDaView build;
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                         build = new FangDaView.Builder(getContext())
                                .intiLT(100, 200)
                                .viewWH(320, 320)
                                .scale(2f)
                                .alpha(16)
                                .color("#ff00ff")
                                .build();

                        build.startViewToRoot();
                    }else {
                        build.closeViewToRoot();
                    }
                }
            });
            btnParent.addView(cb_fangda);


        }
    }

    @Override
    public void initListener() {

    }

}
