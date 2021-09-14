package com.hearing.moongame.widget

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import com.hearing.moongame.R
import com.hearing.moongame.utils.SizeUtil
import com.hearing.moongame.utils.size
import kotlin.math.max
import kotlin.random.Random

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 * 老鼠控件
 */
@SuppressLint("ViewConstructor")
class MouseView constructor(
    context: Context,
    private val goneInterval: Long,  // 超时自动消失的时间，用来控制不同关卡的难度
    private val container: OperateLayout,
    private val listener: OnMouseListener?
) : AppCompatImageView(context, null, 0), Runnable {
    init {
        setImageDrawable(
            AppCompatResources.getDrawable(
                context, when (Random.nextInt(3)) {
                    0 -> R.drawable.mouse1
                    1 -> R.drawable.mouse2
                    else -> R.drawable.mouse3
                }
            )
        )
        // 设置点击后移除自身，并移除超时自动消失的任务
        setOnClickListener {
            removeCallbacks(this)
            container.removeView(this)
            if (container.isRunning) {
                listener?.onClick(size())
            }
        }
    }

    /**
     * 超时自动消失的任务
     */
    override fun run() {
        container.removeView(this)
        if (container.isRunning) {
            listener?.onDismiss(size())
        }
    }

    /**
     * 展示自身，并发送一个超时自动消失的任务
     */
    fun show() {
        val size = mouseSize()
        val params = FrameLayout.LayoutParams(size, size)
        params.leftMargin = Random.nextInt(0, max(container.width - size, 1))
        params.topMargin = Random.nextInt(0, max(container.height - size, 1))
        container.addView(this, params)
        postDelayed(this, goneInterval)
    }

    private fun mouseSize(): Int {
        val min = SizeUtil.dp2px(30f)
        val max = SizeUtil.dp2px(100f)
        return Random.nextInt(min, max)
    }

    interface OnMouseListener {
        fun onClick(size: Int)
        fun onDismiss(size: Int)
    }
}