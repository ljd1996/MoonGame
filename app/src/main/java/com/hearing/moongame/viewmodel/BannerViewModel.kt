package com.hearing.moongame.viewmodel

import androidx.lifecycle.ViewModel
import com.hearing.moongame.model.BannerModel

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 */
class BannerViewModel : ViewModel() {
    private val model: BannerModel by lazy {
        BannerModel()
    }

    fun getBannerText(): String = model.bannerText()

    fun getBannerPanelText(): String = model.bannerPanelText()
}