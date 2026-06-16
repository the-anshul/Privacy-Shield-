package com.example.privacyshield.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.privacyshield.domain.model.AppPrivacyInfo
import com.example.privacyshield.domain.repository.AppPrivacyRepository
import com.example.privacyshield.domain.util.RiskClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppPrivacyRepositoryImpl(private val context: Context) : AppPrivacyRepository {

    override suspend fun getInstalledApps(): List<AppPrivacyInfo> = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val appPrivacyList = mutableListOf<AppPrivacyInfo>()

        for (packageInfo in packages) {
            val appInfo = packageInfo.applicationInfo ?: continue
            val appName = packageManager.getApplicationLabel(appInfo).toString()
            val packageName = packageInfo.packageName
            val isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

            val granted = mutableListOf<String>()
            val denied = mutableListOf<String>()

            packageInfo.requestedPermissions?.let { reqPermissions ->
                val flags = packageInfo.requestedPermissionsFlags
                if (flags != null) {
                    for (i in reqPermissions.indices) {
                        if ((flags[i] and android.content.pm.PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                            granted.add(reqPermissions[i])
                        } else {
                            denied.add(reqPermissions[i])
                        }
                    }
                }
            }

            val riskLevel = RiskClassifier.calculateRiskLevel(granted)
            val icon = packageManager.getApplicationIcon(appInfo)

            appPrivacyList.add(
                AppPrivacyInfo(
                    appName = appName,
                    packageName = packageName,
                    appIcon = icon,
                    isSystemApp = isSystemApp,
                    grantedPermissions = granted,
                    deniedPermissions = denied,
                    riskLevel = riskLevel
                )
            )
        }
        appPrivacyList
    }
}
