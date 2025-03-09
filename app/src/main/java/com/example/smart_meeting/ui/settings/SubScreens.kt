package com.example.smart_meeting.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(title: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text("通知设置") },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }
        },
        modifier = Modifier.height(100.dp)
    )
}

// 账户设置页面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAccountScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar("账户设置", onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("账户设置内容")
        }
    }
}

// 通知设置页面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNotificationScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar("通知设置", onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("通知设置内容")
        }
    }
}

// 隐私设置页面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPrivacyScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar("隐私安全", onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("隐私安全内容")
        }
    }
}

// 语言设置页面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPaletteScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar("主题设置", onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("主题设置内容")
        }
    }
}

// 语言设置页面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLanguageScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar("语言设置", onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("语言设置内容")
        }
    }
}

// 关于页面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAboutScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar("关于", onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("关于内容")
        }
    }
}
