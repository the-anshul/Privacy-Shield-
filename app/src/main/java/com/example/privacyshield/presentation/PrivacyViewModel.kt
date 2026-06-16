package com.example.privacyshield.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.privacyshield.domain.model.AppPrivacyInfo
import com.example.privacyshield.domain.model.RiskLevel
import com.example.privacyshield.domain.usecase.GetPrivacyAppsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppFilter {
    ALL, USER, SYSTEM
}

class PrivacyViewModel(
    private val getPrivacyAppsUseCase: GetPrivacyAppsUseCase
) : ViewModel() {

    private val _allApps = MutableStateFlow<List<AppPrivacyInfo>>(emptyList())
    
    private val _filter = MutableStateFlow(AppFilter.ALL)
    val filter: StateFlow<AppFilter> = _filter

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val displayedApps: StateFlow<List<AppPrivacyInfo>> = combine(_allApps, _filter) { apps, filter ->
        when (filter) {
            AppFilter.ALL -> apps
            AppFilter.USER -> apps.filter { !it.isSystemApp }
            AppFilter.SYSTEM -> apps.filter { it.isSystemApp }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val privacyScore: StateFlow<Int> = combine(_allApps, _filter) { apps, filter ->
        val filteredApps = when (filter) {
            AppFilter.ALL -> apps
            AppFilter.USER -> apps.filter { !it.isSystemApp }
            AppFilter.SYSTEM -> apps.filter { it.isSystemApp }
        }
        if (filteredApps.isEmpty()) return@combine 100
        val highRiskCount = filteredApps.count { it.riskLevel == RiskLevel.HIGH }
        val score = 100 - ((highRiskCount.toFloat() / filteredApps.size) * 100).toInt()
        score.coerceIn(0, 100)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 100)

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _allApps.value = getPrivacyAppsUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setFilter(appFilter: AppFilter) {
        _filter.value = appFilter
    }
}
