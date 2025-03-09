package com.example.smart_meeting.ui.settings

sealed class SettingScreen(val route: String) {
    object Main : SettingScreen("settings_main")
    object Account : SettingScreen("settings_account")
    object Notification : SettingScreen("settings_notification")
    object Privacy : SettingScreen("settings_privacy")
    object Security : SettingScreen("settings_security")
    object Language : SettingScreen("settings_language")
    object About : SettingScreen("settings_about")
}