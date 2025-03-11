package com.example.smart_meeting.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smart_meeting.ui.settings.SettingScreen
import androidx.compose.ui.res.stringResource
import com.example.smart_meeting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDrawer(navController: NavController, onDismiss: () -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.85f)  // 设置抽屉宽度为屏幕宽度的85%
    ) {
        fun onItemClick(route: String){
            navController.navigate(route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
            onDismiss()
        }
// 设置项列表
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 账号设置
            SettingsSection(
                title = "账号设置",
                items = listOf(
                    SettingsItem(
                        stringResource(id = R.string.setting_personal_name),
                        Icons.Default.Person
                    ) { onItemClick(SettingScreen.Account.route) },
                    SettingsItem(
                        stringResource(id = R.string.setting_security_name),
                        Icons.Default.Security
                    ) { onItemClick(SettingScreen.Privacy.route) }
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // 系统设置
            SettingsSection(
                title = "系统设置",
                items = listOf(
                    SettingsItem(
                        stringResource(id = R.string.setting_notification_name),
                        Icons.Default.Notifications
                    ) { onItemClick(SettingScreen.Notification.route) },
                    SettingsItem(
                        stringResource(id = R.string.setting_palette_name),
                        Icons.Default.Palette
                    ) { onItemClick(SettingScreen.Palette.route) },
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // 关于
            SettingsSection(
                title = "关于",
                items = listOf(
                    SettingsItem(
                        stringResource(id = R.string.setting_info_name),
                        Icons.Default.Info
                    ) { onItemClick(SettingScreen.About.route) },
                    SettingsItem(
                        stringResource(id = R.string.setting_help_name),
                        Icons.AutoMirrored.Filled.Help
                    ) { onItemClick(SettingScreen.Help.route) }
                )
            )
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    items: List<SettingsItem>
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        items.forEach { item ->
            SettingsItemRow(item)
        }
    }
}

@Composable
private fun SettingsItemRow(item: SettingsItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = item.onClick
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)