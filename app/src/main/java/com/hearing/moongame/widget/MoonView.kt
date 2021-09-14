package com.hearing.moongame.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.hearing.moongame.R
import com.hearing.moongame.utils.SizeUtil

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 * 月饼控件
 */
class MoonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // 半径
    private var radius = SizeUtil.dp2px(30f)

    // 颜色
    private var color = Color.BLUE

    // 绘制的文本
    private var text = ""

    // 圆的画笔
    private val moonPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 文本画笔
    private val textPaint = TextPaint()

    init {
        context.obtainStyledAttributes(attrs, R.styleable.MoonView).apply {
            radius = getDimensionPixelSize(R.styleable.MoonView_radius, radius)
            color = getColor(R.styleable.MoonView_color, color)
            text = getString(R.styleable.MoonView_text) ?: text
            recycle()
        }
        moonPaint.style = Paint.Style.FILL
        moonPaint.color = color

        if (text.isNotEmpty()) {
            textPaint.color = Color.WHITE
            textPaint.textSize = (radius * 1.5f) / text.length
            textPaint.isAntiAlias = true
            textPaint.textAlign = Paint.Align.CENTER
        }
    }

    fun setColor(color: Int) {
        this.color = color
        moonPaint.color = color
        invalidate()
    }

    /**
     * 通过设置的半径来确定控件的测量尺寸
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(radius * 2 + paddingStart + paddingEnd, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(radius * 2 + paddingTop + paddingBottom, MeasureSpec.EXACTLY)
        )
    }

    /**
     * 画圆和文本
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val padding = (measuredWidth - radius * 2) / 2f
        canvas?.drawCircle(padding + radius, padding + radius, radius.toFloat(), moonPaint)
        if (text.isNotEmpty()) {
            val fontMetrics = textPaint.fontMetricsInt
            canvas?.drawText(
                text,
                radius + padding,
                radius + padding - (fontMetrics.bottom + fontMetrics.top) / 2,
                textPaint
            )
        }
    }
}