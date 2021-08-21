package moe.minori.tintfix.utils

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import moe.minori.tintfix.activity.MainActivity

//TODO: Implement accessibility service related features, and allow user to choose apps
//TODO: Figure out a way to dynamically pass internal video file to the destination app
object ScreenBoosterUtils {
    fun launchScreenBoosterWhitelistedApp(activity: MainActivity) {
        try {
            Intent().apply {
                setClassName(
                    "com.sonyericsson.album",
                    "com.sonyericsson.album.video.player.PlayerActivity"
                )
                action = Intent.ACTION_VIEW
                data =
                    Uri.parse("${Environment.getExternalStorageDirectory().path}/tintfix/vid.mp4")
                        .apply {
                            Log.v("MainActivity", "Uri is $this")
                        }
                flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or Intent.FLAG_ACTIVITY_NEW_TASK
                activity.startActivity(this)
                activity.overridePendingTransition(0, 0)
            }
        } catch (_: Exception) {
            Toast.makeText(
                activity,
                "Target application is not available. Is com.sonyericsson.album installed?",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}