package com.example.privacyshield.domain.util

import android.Manifest
import com.example.privacyshield.domain.model.RiskLevel

object RiskClassifier {

    private val highRiskPermissions = setOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_CONTACTS
    )

    private val mediumRiskPermissions = setOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        "android.permission.READ_MEDIA_IMAGES",
        "android.permission.READ_MEDIA_VIDEO",
        Manifest.permission.READ_CALL_LOG
    )

    fun calculateRiskLevel(grantedPermissions: List<String>): RiskLevel {
        var hasMediumRisk = false
        for (permission in grantedPermissions) {
            if (highRiskPermissions.contains(permission)) {
                return RiskLevel.HIGH
            }
            if (mediumRiskPermissions.contains(permission)) {
                hasMediumRisk = true
            }
        }
        return if (hasMediumRisk) RiskLevel.MEDIUM else RiskLevel.LOW
    }
}
