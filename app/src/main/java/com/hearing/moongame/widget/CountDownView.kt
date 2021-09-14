package com.hearing.moongame.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 * 倒计时
 */
class CountDownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr), Runnable {
    private var total = DEFAULT_COUNT
    private var onFinishListener: OnFinishListener? = null

    init {
        text = "$total"
    }

    fun start(count: Int = DEFAULT_COUNT, listener: OnFinishListener) {
        total = count
        onFinishListener = listener
        run()
    }

    fun stop() {
        removeCallbacks(this)
        total = DEFAULT_COUNT
        onFinishListener = null
        adjustStyle()
        text = "$total"
    }

    override fun run() {
        adjustStyle()
        text = "${total--}"
        if (total < 0) {
            onFinishListener?.onFinish()
        } else {
            postDelayed(this, 1000)
        }
    }

    private fun adjustStyle() {
        if (total >= 10) {
            setTextColor(Color.BLACK)
            textSize = 20f
            typeface = Typeface.DEFAULT
        } else {
            setTextColor(Color.RED)
            textSize = 23f
            typeface = Typeface.DEFAULT_BOLD
        }
    }

    companion object {
        private const val DEFAULT_COUNT = 20
    }

    interface OnFinishListener {
        fun onFinish()
    }
}