package com.example.privacyshield.domain.repository

import com.example.privacyshield.domain.model.AppPrivacyInfo

interface AppPrivacyRepository {
    suspend fun getInstalledApps(): List<AppPrivacyInfo>
}
