package org.thor.base.view.dialog.listener;

import android.view.View;
import android.widget.AdapterView;

@FunctionalInterface
public interface OnOperItemClickL {
		void onOperItemClick(AdapterView<?> parent, View view, int position, long id);
	}
