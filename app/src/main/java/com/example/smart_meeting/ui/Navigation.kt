package com.example.smart_meeting

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavigationItem(val route: String, val title: String, val icon: ImageVector) {
    object Meetings : BottomNavigationItem("meetings", "会议", Icons.Default.AddCircle)
    object Features : BottomNavigationItem("features", "功能", Icons.Default.ViewComfyAlt)
    object Profile : BottomNavigationItem("profile", "我的", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavHostController,
                     currentRoute:  String?,
                     onSettingsClick: () -> Unit,
                     onScannerClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = when(currentRoute) {
                    BottomNavigationItem.Profile.route -> ""
                    else -> "你好"
                }
            )
        },
        navigationIcon = {
            if (currentRoute != BottomNavigationItem.Profile.route) {
                IconButton(onClick = {
                    navController.navigate(BottomNavigationItem.Profile.route){
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "用户"
                    )
                }
            }
        },
        actions = {
            if (currentRoute != BottomNavigationItem.Profile.route){
                IconButton(onClick =  onSettingsClick) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "设置"
                    )
                }
            }else{
                IconButton(onClick =  onScannerClick) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = "设置"
                    )
                }
                IconButton(onClick =  onSettingsClick) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "设置"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavigationItem.Features,
        BottomNavigationItem.Meetings,
        BottomNavigationItem.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            icon = { Icon(items[0].icon, contentDescription = items[0].title) },
            label = { Text(text = items[0].title) },
            selected = currentRoute == items[0].route,
            onClick = {
                navController.navigate(items[0].route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(items[1].icon, contentDescription = items[1].title) },
            selected = currentRoute == items[1].route,
            onClick = {
                navController.navigate(items[1].route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(items[2].icon, contentDescription = items[2].title) },
            label = { Text(text = items[2].title) },
            selected = currentRoute == items[2].route,
            onClick = {
                navController.navigate(items[2].route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}