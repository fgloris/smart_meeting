package com.example.smart_meeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlin.math.absoluteValue

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
    val pages = remember {
        listOf(
            BottomNavigationItem.Features,
            BottomNavigationItem.Meetings,
            BottomNavigationItem.Profile
        )
    }
    var currentIndex = remember(currentRoute) {
        pages.indexOfFirst { it.route == currentRoute }
    }
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
                                    .pointerInput(Unit) {
                    var dragStart = 0f
                    var dragDistance = 0f

                    detectHorizontalDragGestures(
                        onDragStart = { dragStart = 0f },
                        onDragEnd = {
                            if (dragDistance.absoluteValue > 10f) { // 滑动超过30%时触发页面切换
                                if (dragDistance < 0 && currentIndex < pages.size-1) {
                                    navController.navigate(pages[currentIndex+1].route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                    currentIndex+=1
                                }else if (dragDistance > 0 && currentIndex > 0) {
                                    navController.navigate(pages[currentIndex-1].route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                    currentIndex-=1
                                }
                            }
                            dragDistance = 0f
                        },
                        onDragCancel = {
                            dragDistance = 0f
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            dragDistance += dragAmount
                        }
                    )
                }
            ) {
                composable(BottomNavigationItem.Meetings.route) { MeetingsScreen() }
                composable(BottomNavigationItem.Features.route) { FeaturesScreen() }
                composable(BottomNavigationItem.Profile.route) { ProfileScreen() }
            }
        }
    }

}

