package com.example.smart_meeting.ui.settings

sealed class SettingScreen(val route: String) {
    object Main : SettingScreen("settings_main")
    object Account : SettingScreen("settings_account")
    object Notification : SettingScreen("settings_notification")
    object Privacy : SettingScreen("settings_privacy")
    object Palette : SettingScreen("settings_palette")
    object About : SettingScreen("settings_about")
    object Help : SettingScreen("settings_help")
}