package org.thor.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import org.thor.base.R;


/**
 * Created by caihong on 2016/12/19.
 */

public class MySearchView extends SearchView {
    public MySearchView(Context context) {
        this(context, null);
    }

    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int searchPlateId = getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        int searchEditFrame = getContext().getResources().getIdentifier("android:id/search_edit_frame", null, null);
        int searchPlated = getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        EditText searchPlate = (EditText) findViewById(searchPlateId);
        searchPlate.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        searchPlate.setGravity(Gravity.CENTER_VERTICAL);
//        searchPlate.setFocusable(false);
//        searchPlate.setFocusableInTouchMode(false);
        View viewById = findViewById(searchPlated);
        viewById.setBackgroundColor(0x00000000);
        View searchEdit = findViewById(searchEditFrame);
        searchEdit.setBackgroundResource(R.drawable.search_frame);
        setFocusableInTouchMode(true);//父布局获取焦点
        int search_mag_icon_id = getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) findViewById(search_mag_icon_id);
        search_mag_icon.setImageResource(R.drawable.search);
        int search_button_id = getContext().getResources().getIdentifier("android:id/search_button", null, null);
        ImageView search_button = (ImageView) findViewById(search_button_id);
        search_button.setImageResource(R.drawable.search);
//        searchPlate.setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Logger.d("MySearchView onFocusChange: " + hasFocus);
//            }
//        });
    }


}
