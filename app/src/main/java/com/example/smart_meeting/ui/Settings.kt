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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDrawer(
) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.85f)  // 设置抽屉宽度为屏幕宽度的85%
    ) {
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
                        "个人信息",
                        Icons.Default.Person
                    ) { /* TODO: 处理个人信息点击 */ },
                    SettingsItem(
                        "隐私设置",
                        Icons.Default.Security
                    ) { /* TODO: 处理隐私设置点击 */ }
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // 系统设置
            SettingsSection(
                title = "系统设置",
                items = listOf(
                    SettingsItem(
                        "通知提醒",
                        Icons.Default.Notifications
                    ) { /* TODO: 处理通知设置点击 */ },
                    SettingsItem(
                        "主题设置",
                        Icons.Default.Palette
                    ) { /* TODO: 处理主题设置点击 */ },
                    SettingsItem(
                        "语言设置",
                        Icons.Default.Language
                    ) { /* TODO: 处理语言设置点击 */ }
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // 关于
            SettingsSection(
                title = "关于",
                items = listOf(
                    SettingsItem(
                        "版本信息",
                        Icons.Default.Info
                    ) { /* TODO: 处理版本信息点击 */ },
                    SettingsItem(
                        "帮助与反馈",
                        Icons.AutoMirrored.Filled.Help
                    ) { /* TODO: 处理帮助与反馈点击 */ }
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