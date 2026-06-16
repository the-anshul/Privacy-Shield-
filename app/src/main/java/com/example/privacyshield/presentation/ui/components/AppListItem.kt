package com.example.privacyshield.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.example.privacyshield.domain.model.AppPrivacyInfo
import com.example.privacyshield.domain.model.RiskLevel
import com.example.privacyshield.presentation.ui.theme.*

@Composable
fun AppListItem(
    appInfo: AppPrivacyInfo,
    onManageClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    val riskColor = when (appInfo.riskLevel) {
        RiskLevel.HIGH -> HighRiskColor
        RiskLevel.MEDIUM -> MediumRiskColor
        RiskLevel.LOW -> LowRiskColor
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                appInfo.appIcon?.let { icon ->
                    Image(
                        painter = rememberDrawablePainter(drawable = icon),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = appInfo.appName, style = MaterialTheme.typography.titleMedium)
                    Text(text = if (appInfo.isSystemApp) "System App" else "User App", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Box(
                    modifier = Modifier
                        .background(riskColor.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = appInfo.riskLevel.name, color = riskColor, style = MaterialTheme.typography.labelSmall)
                }
            }
            
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
                    Text("Granted Permissions:", style = MaterialTheme.typography.titleSmall)
                    if (appInfo.grantedPermissions.isEmpty()) {
                        Text("- None", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    } else {
                        appInfo.grantedPermissions.forEach { perm ->
                            Text("- $perm", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Denied Permissions:", style = MaterialTheme.typography.titleSmall)
                    if (appInfo.deniedPermissions.isEmpty()) {
                        Text("- None", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    } else {
                        appInfo.deniedPermissions.forEach { perm ->
                            Text("- $perm", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onManageClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Text("Manage Permissions")
                    }
                }
            }
        }
    }
}
