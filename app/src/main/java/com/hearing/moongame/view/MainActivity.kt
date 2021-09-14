package com.hearing.moongame.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hearing.moongame.R
import com.hearing.moongame.banner.FancyPanel
import com.hearing.moongame.banner.ScrollTextView
import com.hearing.moongame.utils.SizeUtil
import com.hearing.moongame.viewmodel.BannerViewModel
import com.hearing.moongame.viewmodel.GameViewModel
import com.hearing.moongame.widget.CountDownView
import com.hearing.moongame.widget.MoonEatView
import com.hearing.moongame.widget.MoonToast
import com.hearing.moongame.widget.MouseView
import com.hearing.moongame.widget.OperateLayout
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    private val bannerViewModel: BannerViewModel by lazy {
        ViewModelProvider(this).get(BannerViewModel::class.java)
    }

    private lateinit var operateLayout: OperateLayout
    private lateinit var moonEatLayout: MoonEatView
    private lateinit var ddlView: CountDownView
    private lateinit var startView: TextView
    private lateinit var levelView: TextView
    private lateinit var bannerView: ScrollTextView
    private lateinit var goBannerView: ImageView
    private lateinit var fancyPanel: FancyPanel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operateLayout = findViewById(R.id.operate_layout)
        moonEatLayout = findViewById(R.id.moon_eat_view)
        ddlView = findViewById(R.id.ddl_view)
        startView = findViewById(R.id.start_view)
        levelView = findViewById(R.id.level_view)
        bannerView = findViewById(R.id.banner_view)
        goBannerView = findViewById(R.id.go_banner)

        initData()
        initListener()
        initObserver()
    }

    private fun initData() {
        gameViewModel.initData(this)
        bannerView.isScrollRepeatable = true
        bannerView.highLightColor = Color.GRAY
        bannerView.setContentText(bannerViewModel.getBannerText())
        bannerView.resume(100)
    }

    private fun initListener() {
        startView.setOnClickListener {
            if (operateLayout.isRunning) {
                return@setOnClickListener
            }
            if (gameViewModel.currentLevel() == FULL_LEVEL) {
                AlertDialog.Builder(this)
                    .setMessage("是否重玩？")
                    .setPositiveButton("确认") { dialog, _ ->
                        dialog.dismiss()
                        gameViewModel.resetLevel(this)
                        setupGame(1)
                        startGame()
                    }
                    .setNegativeButton("神秘奖品") { dialog, _ ->
                        dialog.dismiss()
                        gotoGift()
                    }
                    .create()
                    .show()
            } else {
                setupGame(gameViewModel.currentLevel())
                startGame()
            }
        }

        goBannerView.setOnClickListener {
            fancyPanel = FancyPanel(
                context = this,
                parentWidth = SizeUtil.getScreenWidth(this).toFloat(),
                parentHeight = SizeUtil.getScreenHeight(this).toFloat()
            )
            fancyPanel.addToActivity(this)
            fancyPanel.setPanelContent(bannerViewModel.getBannerPanelText())
            fancyPanel.onResume()
        }
    }

    private fun initObserver() {
        gameViewModel.level.observe(
            this,
            {
                levelView.text = if (it == FULL_LEVEL) {
                    getString(R.string.full_level)
                } else {
                    String.format(getString(R.string.current_level), it)
                }
            }
        )
    }

    private fun setupGame(level: Int) {
        moonEatLayout.resetTranslateMask()
        operateLayout.countOnce = (level / 3) + 1
        operateLayout.speed = (1000L / sqrt(level.toDouble())).toLong()
    }

    private fun startGame() {
        MoonToast.show(this, getString(R.string.toast_start_game))
        ddlView.start(listener = object : CountDownView.OnFinishListener {
            override fun onFinish() {
                stopGame()
            }
        })
        operateLayout.start(object : MouseView.OnMouseListener {
            override fun onClick(size: Int) {
                moonEatLayout.translateMask(-maskTranslate(size))
            }

            override fun onDismiss(size: Int) {
                moonEatLayout.translateMask(maskTranslate(size))
            }
        })
        moonEatLayout.onMaskListener = object : MoonEatView.OnMaskListener {
            override fun onMaskAll() {
                stopGame()
            }
        }
    }

    private fun stopGame() {
        ddlView.stop()
        if (moonEatLayout.maskAll()) {
            MoonToast.show(this, getString(R.string.toast_fail_game))
        } else {
            gameViewModel.level.value?.let {
                if (it >= TOTAL_LEVEL) {
                    gameViewModel.setLevel(this, FULL_LEVEL)
                    AlertDialog.Builder(this)
                        .setMessage("是否领取神秘奖品？")
                        .setPositiveButton("确认") { dialog, _ ->
                            dialog.dismiss()
                            gotoGift()
                        }
                        .setNegativeButton("取消") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                } else {
                    MoonToast.show(this, getString(R.string.toast_pass_game))
                    gameViewModel.passLevel(this)
                }
            }
        }
        operateLayout.stop()
    }

    private fun maskTranslate(size: Int): Float {
        val level = gameViewModel.currentLevel()
        return (size * 100) / (moonEatLayout.moonViewSize() * sqrt(level.toDouble()).toFloat())
    }

    private fun gotoGift() {
        startActivity(Intent(this, GiftActivity::class.java))
    }

    companion object {
        // 总共 15 关
        private const val TOTAL_LEVEL = 15

        // 通关
        private const val FULL_LEVEL = -1
    }
}