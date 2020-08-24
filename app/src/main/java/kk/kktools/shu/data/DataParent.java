package kk.kktools.shu.data;

import utils.kkutils.json.JsonDataParent;

public class DataParent extends JsonDataParent {
    @Override
    public boolean isDataOk() {
        return true;
    }

    @Override
    public boolean isDataOkAndToast() {
        return true;
    }
}
