package moe.minori.tintfix.activity

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.minori.tintfix.utils.*
import moe.minori.tintfix.utils.screenbrightness.*

class MainActivity : AppCompatActivity() {
    private val screenUtils: ScreenUtils = ScreenUtilsLegacyImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)

        lifecycleScope.launch {
            when (screenUtils.canSetBrightness(activity = this@MainActivity)) {
                true -> {
                    doTheWork()
                }
                false -> {
                    // TODO: Could not complete the task...
                }
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(0, 0)
    }

    @MainThread
    private suspend fun doTheWork() {
        val currentBrightnessMode = screenUtils.getBrightnessMode(activity = this@MainActivity)
        val currentBrightness = screenUtils.getBrightness(activity = this@MainActivity)

        screenUtils.setBrightness(activity = this@MainActivity,
            screenBrightness = maxBrightness,
            screenBrightnessMode = ScreenBrightnessMode.MANUAL
        )

        delay(500)

        ScreenBoosterUtils.launchScreenBoosterWhitelistedApp(activity = this@MainActivity)

        delay(1000)

        screenUtils.setBrightness(
            activity = this@MainActivity,
            screenBrightness = currentBrightness,
            screenBrightnessMode = currentBrightnessMode
        )

        delay(1300)

        finishAndRemoveTask()
    }
}