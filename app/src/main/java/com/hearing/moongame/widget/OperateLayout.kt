package com.hearing.moongame.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.hearing.moongame.R

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 * 不停出现老鼠的控件
 */
class OperateLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), Runnable {
    // 同时生成的 View 数
    var countOnce = 1

    // 生成 View 的间隔速度
    var speed = 1000L

    // 游戏是否进行中
    var isRunning = false
        private set

    private var onMouseListener: MouseView.OnMouseListener? = null

    init {
        addBgView()
    }

    /**
     * 开始冒出老鼠
     */
    fun start(listener: MouseView.OnMouseListener) {
        this.onMouseListener = listener
        this.isRunning = true
        removeAllViews()
        post(this)
    }

    /**
     * 停止
     */
    fun stop() {
        this.isRunning = false
        removeAllViews()
        removeCallbacks(this)
        addBgView()
    }

    override fun run() {
        repeat(countOnce) {
            if (!isRunning) {
                return
            }
            val mouseView = MouseView(
                context,
                goneInterval = speed,
                container = this,
                listener = onMouseListener
            )
            mouseView.show()
        }

        postDelayed(this, speed)
    }

    private fun addBgView() {
        kotlin.runCatching {
            addView(LayoutInflater.from(context).inflate(R.layout.layout_operate_bg, null))
        }
    }
}