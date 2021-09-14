package com.hearing.moongame.widget

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.hearing.moongame.R


/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 */
object MoonToast {
    private var sToast: Toast? = null

    fun show(context: Context, @StringRes msgId: Int) {
        show(context, context.getString(msgId))
    }

    fun show(context: Context, msg: String) {
        show(context, msg, Toast.LENGTH_SHORT)
    }

    private fun show(context: Context, msg: String, len: Int) {
        kotlin.runCatching {
            sToast?.cancel()
            val view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null)
            view.findViewById<TextView>(R.id.toast_tv).text = msg
            sToast = Toast(context)
            sToast?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, 70)
            sToast?.view = view
            sToast?.duration = len
            sToast?.show()
        }
    }
}