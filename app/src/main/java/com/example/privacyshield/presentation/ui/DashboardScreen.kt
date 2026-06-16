package com.example.privacyshield.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.privacyshield.presentation.AppFilter
import com.example.privacyshield.presentation.PrivacyViewModel
import com.example.privacyshield.presentation.ui.components.AppListItem
import com.example.privacyshield.presentation.ui.theme.HighRiskColor
import com.example.privacyshield.presentation.ui.theme.LowRiskColor
import com.example.privacyshield.presentation.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: PrivacyViewModel,
    onManageAppClick: (String) -> Unit
) {
    val displayedApps by viewModel.displayedApps.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val privacyScore by viewModel.privacyScore.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PrivacyShield", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryBlue.copy(alpha = 0.1f))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Privacy Score", style = MaterialTheme.typography.titleMedium)
                    val scoreColor = if (privacyScore > 70) LowRiskColor else HighRiskColor
                    Text(
                        text = "$privacyScore%",
                        style = MaterialTheme.typography.displayLarge,
                        color = scoreColor
                    )
                    LinearProgressIndicator(
                        progress = privacyScore / 100f,
                        color = scoreColor,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(top = 8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    selected = filter == AppFilter.ALL,
                    onClick = { viewModel.setFilter(AppFilter.ALL) },
                    label = { Text("All Apps") }
                )
                FilterChip(
                    selected = filter == AppFilter.USER,
                    onClick = { viewModel.setFilter(AppFilter.USER) },
                    label = { Text("User Apps") }
                )
                FilterChip(
                    selected = filter == AppFilter.SYSTEM,
                    onClick = { viewModel.setFilter(AppFilter.SYSTEM) },
                    label = { Text("System Apps") }
                )
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(displayedApps) { app ->
                        AppListItem(
                            appInfo = app,
                            onManageClick = { onManageAppClick(app.packageName) }
                        )
                    }
                }
            }
        }
    }
}
