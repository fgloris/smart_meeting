package com.example.smart_meeting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

sealed class BottomNavigationItem(val route: String, val label: String, val icon: ImageVector) {
    object Meetings : BottomNavigationItem("meetings", "会议", Icons.Default.AddCircle)
    object Features : BottomNavigationItem("features", "功能", Icons.Default.ViewComfyAlt)
    object Profile : BottomNavigationItem("profile", "我的", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavHostController,
                     currentIndex: Int,
                     onSettingsClick: () -> Unit,
                     onScannerClick: () -> Unit
) {
    TopAppBar(
        title = {
            if (currentIndex != 2){
                Text(text = "你好")
            }
        },
        navigationIcon = {
            if (currentIndex == 2) {
                IconButton(onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "用户"
                    )
                }
            } else {
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
            if (currentIndex == 2){
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
            }else{
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
fun BottomNavigationBar(selectedIndex: Int,
                        onPageSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavigationItem.Features,
        BottomNavigationItem.Meetings,
        BottomNavigationItem.Profile
    )

    val backgroundColor = MaterialTheme.colorScheme.surface
    NavigationBar (
        modifier = Modifier.height(80.dp),
        containerColor = backgroundColor,
    )
    {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onPageSelected(index)
                    }
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    modifier = Modifier
                        .scale(if (index == 1) 1.5f else 1.2f)
                        .size(if (index == 1) 32.dp else 24.dp),
                    tint = if (selected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (index != 1){
                    Text(
                        text = item.label,
                        color = if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}