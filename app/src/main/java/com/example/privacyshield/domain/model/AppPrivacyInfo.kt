package com.example.privacyshield.domain.model

import android.graphics.drawable.Drawable

enum class RiskLevel {
    HIGH, MEDIUM, LOW
}

data class AppPrivacyInfo(
    val appName: String,
    val packageName: String,
    val appIcon: Drawable?,
    val isSystemApp: Boolean,
    val grantedPermissions: List<String>,
    val deniedPermissions: List<String>,
    val riskLevel: RiskLevel
)
