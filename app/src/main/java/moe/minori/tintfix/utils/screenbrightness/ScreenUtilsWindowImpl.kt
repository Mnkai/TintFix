package moe.minori.tintfix.utils.screenbrightness

import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

//TODO: Previous Brightness utility is not very compatible with this implementation. Define new spec.
class ScreenUtilsWindowImpl: ScreenUtils {
    override suspend fun canSetBrightness(activity: AppCompatActivity): Boolean {
        return activity.window?.let {
            true
        } ?: false
    }

    override fun setBrightness(
        activity: AppCompatActivity,
        screenBrightness: ScreenBrightness,
        screenBrightnessMode: ScreenBrightnessMode
    ) {
        when (screenBrightnessMode) {
            ScreenBrightnessMode.AUTOMATIC -> {
                activity.window?.attributes?.screenBrightness = -1f
            }
            ScreenBrightnessMode.MANUAL -> {
                activity.window?.attributes?.screenBrightness = screenBrightness.percentage / 100f
            }
            else -> {
                // NOOP
            }
        }
    }

    override fun getBrightness(activity: AppCompatActivity): ScreenBrightness {
        return ScreenBrightness(
            percentage = ((activity.window?.attributes?.screenBrightness ?: 0f * 100).roundToInt())
                .coerceAtLeast(0)
                .coerceAtMost(100)
        )
    }

    override fun getBrightnessMode(activity: AppCompatActivity): ScreenBrightnessMode {
        return when(activity.window?.attributes?.screenBrightness) {
            -1f -> ScreenBrightnessMode.AUTOMATIC
            else -> ScreenBrightnessMode.MANUAL
        }
    }
}