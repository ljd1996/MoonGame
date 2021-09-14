package com.hearing.moongame.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author: 苍耳叔叔
 * @Date: 2021/9/12
 */
class GameViewModel : ViewModel() {
    private val _level: MutableLiveData<Int> = MutableLiveData(1)
    val level: LiveData<Int> = _level

    fun initData(context: Context) {
        _level.value = getLevel(context)
    }

    fun currentLevel(): Int = _level.value ?: 1

    /**
     * 升级
     */
    fun passLevel(context: Context) {
        val level = _level.value?.let {
            it + 1
        } ?: 1
        setLevel(context, level)
    }

    /**
     * 重玩
     */
    fun resetLevel(context: Context) {
        setLevel(context, 1)
    }

    fun setLevel(context: Context, level: Int) {
        _level.value = level
        val sp = context.getSharedPreferences(SP_MOON_GAME_LEVEL, Context.MODE_PRIVATE)
        sp.edit().putInt(KEY_CURRENT_LEVEL, level).apply()
    }

    private fun getLevel(context: Context): Int {
        return context.getSharedPreferences(SP_MOON_GAME_LEVEL, Context.MODE_PRIVATE)
            .getInt(KEY_CURRENT_LEVEL, 1)
    }

    companion object {
        private const val SP_MOON_GAME_LEVEL = "sp_moon_game_level"
        private const val KEY_CURRENT_LEVEL = "key_current_level"
    }
}