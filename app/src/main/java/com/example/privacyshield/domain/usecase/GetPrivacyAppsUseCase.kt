package com.example.privacyshield.domain.usecase

import com.example.privacyshield.domain.model.AppPrivacyInfo
import com.example.privacyshield.domain.repository.AppPrivacyRepository

class GetPrivacyAppsUseCase(
    private val repository: AppPrivacyRepository
) {
    suspend operator fun invoke(): List<AppPrivacyInfo> {
        return repository.getInstalledApps().sortedBy { it.appName }
    }
}
