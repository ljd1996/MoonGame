package com.hearing.moongame.model

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 */
class BannerModel {

    fun bannerText(): String = BANNER_TEXT

    fun bannerPanelText(): String = BANNER_PANEL_TEXT

    companion object {
        private const val BANNER_TEXT = "此处是广告\n通关后有神秘奖品哦~\n此处是广告" +
                "\n通关后有神秘奖品哦~\n通关后有神秘奖品哦~\n通关后有神秘奖品哦~\n通关后有神秘奖品哦~" +
                "\n此处是广告\n通关后有神秘奖品哦~\n此处是广告\n通关后有神秘奖品哦~"

        private const val BANNER_PANEL_TEXT = "定风波·湖村晚\n苍耳叔叔\n" +
                "湖面蒹葭荡影重，黄昏渐映水寒清。远处人家声影乱，亲唤，小童归去老村惊。\n" +
                "月上枝头双戏景，微冷，农家秋月夜燃灯。灯影幢幢人影瘦，浊酒，菜花香入梦回轻。\n\n" +
                "渔歌子·重九\n苍耳叔叔\n" +
                "陌上闲枝半过秋，重阳当饮酒难休。\n杯入曲，晚归悠。炊烟袅袅正秋收。\n\n" +
                "潇湘梦·如仙\n苍耳叔叔\n" +
                "风如烟，卿画颜。\n小楼帘下舞蹁跹。\n浊酒几杯还故里，\n青丝如梦九天仙。"
    }
}