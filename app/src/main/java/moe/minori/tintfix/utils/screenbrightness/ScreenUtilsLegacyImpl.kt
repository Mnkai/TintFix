package moe.minori.tintfix.utils.screenbrightness

import android.Manifest
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CompletableDeferred
import moe.minori.tintfix.utils.PermissionUtils
import kotlin.math.roundToInt

class ScreenUtilsLegacyImpl : ScreenUtils {

    override suspend fun canSetBrightness(activity: AppCompatActivity): Boolean {
        val toReturn = CompletableDeferred<Boolean>()

        val isPermissionAlreadyAvailable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.System.canWrite(activity)
        } else true

        if (isPermissionAlreadyAvailable) {
            toReturn.complete(true)
        } else {
            PermissionUtils.requestPermission(
                activity = activity,
                permission = Manifest.permission.WRITE_SETTINGS,
                failureCallback = { toReturn.complete(false) },
                successCallback = { toReturn.complete(true) }
            )
        }

        return toReturn.await()
    }

    override fun setBrightness(
        activity: AppCompatActivity,
        screenBrightness: ScreenBrightness,
        screenBrightnessMode: ScreenBrightnessMode
    ) {
        Settings.System.putInt(
            activity.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            when (screenBrightnessMode) {
                ScreenBrightnessMode.MANUAL -> Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                ScreenBrightnessMode.AUTOMATIC -> Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                // fallback, should not happen unless Android SDK updates in the future
                ScreenBrightnessMode.UNDEFINED -> Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            }
        )

        Settings.System.putInt(
            activity.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            (screenBrightness.percentage.toDouble() / 100 * 255).roundToInt()
        )
    }

    override fun getBrightness(activity: AppCompatActivity): ScreenBrightness {
        return ScreenBrightness(
            percentage = (Settings.System.getInt(
                activity.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            ).toDouble() / 255 * 100).roundToInt().coerceAtLeast(0).coerceAtMost(100)
        )
    }

    override fun getBrightnessMode(activity: AppCompatActivity): ScreenBrightnessMode {
        return when (Settings.System.getInt(
            activity.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE
        )) {
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC -> ScreenBrightnessMode.AUTOMATIC
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL -> ScreenBrightnessMode.MANUAL
            else -> ScreenBrightnessMode.UNDEFINED
        }
    }
}