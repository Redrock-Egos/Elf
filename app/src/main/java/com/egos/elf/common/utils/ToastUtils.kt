package com.egos.elf.common.utils

import android.annotation.SuppressLint
import android.support.annotation.IntDef
import android.widget.Toast
import com.egos.elf.App
import io.reactivex.android.schedulers.AndroidSchedulers

object ToastUtils {
    private var mToast: Toast? = null

    /** @hide */
    @IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Duration

    fun show(text: String) {
        show(text, Toast.LENGTH_SHORT)
    }

    @SuppressLint("ShowToast")
    fun show(text: String, @Duration time: Int) {

        if (mToast == null) {
            mToast = Toast.makeText(App.context, text, time)
        } else {
            mToast?.setText(text)
        }

        mToast?.show()
    }

    fun asyncShow(text: String) {
        AndroidSchedulers.mainThread().scheduleDirect { show(text) }
    }

    fun asyncShow(text: String, @Duration time: Int) {
        AndroidSchedulers.mainThread().scheduleDirect { show(text, time) }
    }
}