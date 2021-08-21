package moe.minori.tintfix.utils.screenbrightness

import androidx.appcompat.app.AppCompatActivity

interface ScreenUtils {
    suspend fun canSetBrightness(activity: AppCompatActivity): Boolean
    fun setBrightness(activity: AppCompatActivity, screenBrightness: ScreenBrightness, screenBrightnessMode: ScreenBrightnessMode)
    fun getBrightness(activity: AppCompatActivity): ScreenBrightness
    fun getBrightnessMode(activity: AppCompatActivity): ScreenBrightnessMode
}

