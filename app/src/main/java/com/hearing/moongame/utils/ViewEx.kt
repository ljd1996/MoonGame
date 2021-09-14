package com.hearing.moongame.utils

import android.view.View

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 */

/**
 * 由于都是正方形，目前用宽度表示 View 的尺寸
 */
fun View.size() = this.width

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}
