package com.zlyandroid.mvcdemo.util;

import android.widget.Toast;

import com.zlyandroid.mvcdemo.app.ProApplication;

public class ToastUtils {
    private static Toast toast;

    public static void showToast(
            String content) {
        if (toast == null) {
            toast = Toast.makeText(ProApplication.getContext(),
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
