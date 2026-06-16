package com.example.privacyshield.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

object IntentUtils {
    fun openAppSettings(context: Context, packageName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
