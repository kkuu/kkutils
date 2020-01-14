package kk.kktools.jinrong;

import android.icu.text.CaseMap;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import kk.kktools.R;
import utils.kkutils.common.MathTool;
import utils.kkutils.common.TimeTool;
import utils.kkutils.common.UiTool;
import utils.kkutils.parent.KKParentFragment;
import utils.kkutils.parent.KKViewOnclickListener;
import utils.kkutils.ui.dialog.datetimedialog.DatePickerGongLiDialog;
import utils.kkutils.ui.dialog.datetimedialog.KKDatePickerDialog;

public class JiSuanQi extends KKParentFragment {

    @Override
    public int initContentViewId() {
        return R.layout.kk_jisuanqi;
    }


    @Override
    public void initData() {

        initLiXi();
    }
    TextView btn_kaishi,btn_jieshu;
    Calendar calendarBegin,calendarEnd;
    int date;

    TextView btn_lixi_jisuan,tv_lixi_money,tv_lixi_lilv,tv_lixi_jieguo;
    TextView btn_lilv_jisuan,tv_lilv_money_begin,tv_lilv_money_end,tv_lilv_jieguo;
    public void initLiXi(){

        calendarBegin=Calendar.getInstance();
        calendarBegin.set(Calendar.YEAR,2019);
        calendarBegin.set(Calendar.MONTH,9);
        calendarBegin.set(Calendar.DATE,12);

        calendarEnd=Calendar.getInstance();
        initDate();

        btn_kaishi.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                final DatePickerGongLiDialog dialog=new DatePickerGongLiDialog(getContext());
                dialog.setJustShowDate(true);
                dialog.setOnChooseDateTimeListener(new KKDatePickerDialog.OnChooseDateTimeListener() {
                    @Override
                    public void onChoose(Calendar calendar, int nian, int yue, int ri, int shi, int fen) {
                        calendarBegin=calendar;
                        initDate();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        btn_jieshu.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                final DatePickerGongLiDialog dialog=new DatePickerGongLiDialog(getContext());
                dialog.setJustShowDate(true);
                dialog.setOnChooseDateTimeListener(new KKDatePickerDialog.OnChooseDateTimeListener() {
                    @Override
                    public void onChoose(Calendar calendar, int nian, int yue, int ri, int shi, int fen) {
                        calendarEnd=calendar;
                        initDate();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        btn_lixi_jisuan.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                double v1 = MathTool.computeNianLiXi(Double.valueOf(tv_lixi_money.getText().toString()), Double.valueOf(tv_lixi_lilv.getText().toString()),date);
                UiTool.setTextView(tv_lixi_jieguo,""+v1);
            }
        });
        btn_lilv_jisuan.setOnClickListener(new KKViewOnclickListener() {
            @Override
            public void onClickKK(View v) {
                double v1 = MathTool.computNianLiLvEnd(Double.valueOf(tv_lilv_money_begin.getText().toString()),date, Double.valueOf(tv_lilv_money_end.getText().toString()));
                UiTool.setTextView(tv_lilv_jieguo,""+v1);
            }
        });
    }
    public void initDate(){
        btn_kaishi.setText(TimeTool.getShortFormat().format(calendarBegin.getTime()));
        btn_jieshu.setText(TimeTool.getShortFormat().format(calendarEnd.getTime()));



         date = (int) TimeTool.dateAfterInt(TimeTool.getLongTimeStr(calendarBegin.getTimeInMillis()), TimeTool.getLongTimeStr(calendarEnd.getTimeInMillis()), "");
    }

    @Override
    public void initListener() {

    }
}
