package com.example.smart_meeting.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingMainScreen(
    onBackPressed: () -> Unit
) {
    val settingsNavController = rememberNavController()

    NavHost(
        navController = settingsNavController,
        startDestination = SettingScreen.Main.route
    ) {
        composable(SettingScreen.Main.route) {
            SettingsMainContent(
                navController = settingsNavController,
                onBackPressed = onBackPressed
            )
        }
        composable(SettingScreen.Account.route) {
            SettingsAccountScreen(onBackPressed = { settingsNavController.navigateUp() })
        }
        composable(SettingScreen.Notification.route) {
            SettingsNotificationScreen(onBackPressed = { settingsNavController.navigateUp() })
        }
        composable(SettingScreen.Privacy.route) {
            SettingsPrivacyScreen(onBackPressed = { settingsNavController.navigateUp() })
        }
        composable(SettingScreen.Security.route) {
            SettingsSecurityScreen(onBackPressed = { settingsNavController.navigateUp() })
        }
        composable(SettingScreen.Language.route) {
            SettingsLanguageScreen(onBackPressed = { settingsNavController.navigateUp() })
        }
        composable(SettingScreen.About.route) {
            SettingsAboutScreen(onBackPressed = { settingsNavController.navigateUp() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsMainContent(
    navController: NavController,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                SettingSection(
                    title = "账户与安全",
                    items = listOf(
                        SettingItem(
                            title = "账户信息",
                            icon = Icons.Default.AccountCircle,
                            onClick = { navController.navigate(SettingScreen.Account.route) }
                        ),
                        SettingItem(
                            title = "安全设置",
                            icon = Icons.Default.Security,
                            onClick = { navController.navigate(SettingScreen.Security.route) }
                        )
                    )
                )
            }

            item {
                SettingSection(
                    title = "通知与隐私",
                    items = listOf(
                        SettingItem(
                            title = "通知设置",
                            icon = Icons.Default.Notifications,
                            onClick = { navController.navigate(SettingScreen.Notification.route) }
                        ),
                        SettingItem(
                            title = "隐私设置",
                            icon = Icons.Default.PrivacyTip,
                            onClick = { navController.navigate(SettingScreen.Privacy.route) }
                        )
                    )
                )
            }

            item {
                SettingSection(
                    title = "通用",
                    items = listOf(
                        SettingItem(
                            title = "语言设置",
                            icon = Icons.Default.Language,
                            onClick = { navController.navigate(SettingScreen.Language.route) }
                        ),
                        SettingItem(
                            title = "关于",
                            icon = Icons.Default.Info,
                            onClick = { navController.navigate(SettingScreen.About.route) }
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun SettingSection(
    title: String,
    items: List<SettingItem>
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        items.forEach { item ->
            SettingItemRow(item)
        }
    }
}

@Composable
private fun SettingItemRow(
    item: SettingItem
) {
    ListItem(
        headlineContent = { Text(item.title) },
        leadingContent = {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier.clickable { item.onClick() }
    )
}

private data class SettingItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)