package utils.kkutils.ui.dialog.datetimedialog;

import android.content.Context;
import android.util.AttributeSet;

/**
 */
public class DatePickerGongLiDialog extends KKDatePickerDialog {
    public DatePickerGongLiDialog(Context context) {
        super(context);
    }

    public DatePickerGongLiDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePickerGongLiDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void reshreTitle(){
//        {//可显示农历
//            String title=NongLiTool.getWenDao(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY));
//            tvTitle.setText(""+title);
//        }
    }
}