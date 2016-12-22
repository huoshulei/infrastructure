package org.thor.base.view.dialog.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import org.thor.base.R;

/**
 * Created by caihong on 2016/12/15.
 */

public class LoadingDialog extends Dialog {


    private LoadingDialog(Context context) {
        super(context,R.style.loading_dialog);
    }


////    @Override
//    public View onCreateView() {
////        View view = inflate(mContext, R.layout.common_progress_view, null);
//        view.setLayoutParams(new ViewGroup.LayoutParams(100,100));
//        return view;
//    }

//    @Override
    public void setUiBeforShow() {

    }


    public static LoadingDialog instance(Activity activity) {
        LoadingDialog dialog = new LoadingDialog(activity);
        return dialog;
    }

}