package moe.minori.tintfix.utils

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

object PermissionUtils {
    fun requestPermission(activity: AppCompatActivity, permission: String, failureCallback: () -> Unit, successCallback: () -> Unit) {
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                successCallback.invoke()
            } else {
                failureCallback.invoke()
            }
        }.launch(permission)
    }

}
