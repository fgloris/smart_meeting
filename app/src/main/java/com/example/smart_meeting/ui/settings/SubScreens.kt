package com.example.smart_meeting.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smart_meeting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(title: String, onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
                .padding(start = 5.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "返回",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAccountScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar(stringResource(id = R.string.setting_personal_name), onBackPressed) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNotificationScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar(stringResource(id = R.string.setting_notification_name), onBackPressed) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPrivacyScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar(stringResource(id = R.string.setting_security_name), onBackPressed) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPaletteScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar(stringResource(id = R.string.setting_palette_name), onBackPressed) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAboutScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar(stringResource(id = R.string.setting_info_name), onBackPressed) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsHelpScreen(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { SettingsTopBar(stringResource(id = R.string.setting_help_name), onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("帮助内容")
        }
    }
}
