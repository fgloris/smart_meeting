package com.example.smart_meeting

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
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
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val splashScreen = installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //enableEdgeToEdge()
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
        drawerState = drawerState
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

