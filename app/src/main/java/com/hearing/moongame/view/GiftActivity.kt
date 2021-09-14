package com.hearing.moongame.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hearing.moongame.R
import com.hearing.moongame.widget.MoonToast

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 */
class GiftActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift)
    }

    fun gift(view: View) {
        MoonToast.show(this, R.string.gift)
    }
}