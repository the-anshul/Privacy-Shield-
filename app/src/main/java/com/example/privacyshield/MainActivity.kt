package com.example.privacyshield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.privacyshield.data.AppPrivacyRepositoryImpl
import com.example.privacyshield.domain.usecase.GetPrivacyAppsUseCase
import com.example.privacyshield.presentation.PrivacyViewModel
import com.example.privacyshield.presentation.ui.DashboardScreen
import com.example.privacyshield.presentation.ui.theme.PrivacyShieldTheme
import com.example.privacyshield.presentation.util.IntentUtils

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = AppPrivacyRepositoryImpl(applicationContext)
        val useCase = GetPrivacyAppsUseCase(repository)

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PrivacyViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return PrivacyViewModel(useCase) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        setContent {
            PrivacyShieldTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: PrivacyViewModel = viewModel(factory = factory)
                    DashboardScreen(
                        viewModel = viewModel,
                        onManageAppClick = { packageName ->
                            IntentUtils.openAppSettings(this, packageName)
                        }
                    )
                }
            }
        }
    }
}
