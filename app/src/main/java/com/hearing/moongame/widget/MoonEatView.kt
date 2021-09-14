package com.hearing.moongame.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.hearing.moongame.R
import com.hearing.moongame.utils.size

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 * 吃月饼效果的控件
 */
class MoonEatView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // 正常月饼
    private var moonView: MoonView

    // 用来遮挡正常月饼的 mask 月饼
    private var maskView: MoonView

    // mask 月饼的移动量
    private var maskTranX: Float = 0f

    var onMaskListener: OnMaskListener? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_moon_eat, this, true)
        moonView = view.findViewById(R.id.moon_view)
        maskView = view.findViewById(R.id.mask_view)
        modifyMoonColor()
    }

    fun moonViewSize() = moonView.size()

    /**
     * 用来移动 mask 月饼，遮挡正常月饼，实现被吃效果
     */
    fun translateMask(tranX: Float) {
        maskTranX += tranX
        if (maskTranX < 0) {
            maskTranX = 0f
        }
        if (maskTranX > moonView.width) {
            maskTranX = moonView.width.toFloat()
        }
        maskView.animate().translationX(maskTranX).setDuration(100).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if (maskAll()) {
                    onMaskListener?.onMaskAll()
                }
            }
        }).start()
        modifyMoonColor()
    }

    /**
     * 重置 mask 月饼的偏移量
     */
    fun resetTranslateMask() {
        maskTranX = 0f
        maskView.translationX = maskTranX
        modifyMoonColor()
    }

    /**
     * 是否全部遮挡
     */
    fun maskAll(): Boolean = maskTranX >= moonView.width

    /**
     * 随着被吃的大小，月饼展示不同颜色
     */
    private fun modifyMoonColor() {
        if (maskTranX <= 0f) {
            moonView.setColor(ContextCompat.getColor(context, R.color.moon_color_1))
            return
        }
        val state = moonView.width / maskTranX
        moonView.setColor(
            ContextCompat.getColor(
                context,
                if (state >= 4) {
                    R.color.moon_color_1
                } else if (state < 4 && state >= 3) {
                    R.color.moon_color_2
                } else if (state < 3 && state >= 2) {
                    R.color.moon_color_3
                } else {
                    R.color.moon_color_4
                }
            )
        )
    }

    interface OnMaskListener {
        fun onMaskAll()
    }
}