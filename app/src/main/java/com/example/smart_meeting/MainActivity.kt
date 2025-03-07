package com.example.smart_meeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smart_meeting.screens.FeaturesScreen
import com.example.smart_meeting.screens.MeetingsScreen
import com.example.smart_meeting.screens.ProfileScreen
import com.example.smart_meeting.screens.SettingsDrawer
import com.example.smart_meeting.ui.theme.Smart_meetingTheme
import kotlinx.coroutines.launch
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            Smart_meetingTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            SettingsDrawer()
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen //无法通过滑动打开但可以点击空白处关闭
    ){
        Scaffold(
            topBar = {
                TopNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    onSettingsClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    onScannerClick = {

                    }
                )
            },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BottomNavigationItem.Meetings.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(BottomNavigationItem.Meetings.route) { MeetingsScreen() }
                composable(BottomNavigationItem.Features.route) { FeaturesScreen() }
                composable(BottomNavigationItem.Profile.route) { ProfileScreen() }
            }
        }
    }

}

