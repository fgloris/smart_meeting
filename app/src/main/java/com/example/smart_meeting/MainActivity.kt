package com.example.smart_meeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smart_meeting.screens.NotificationDrawer
import com.example.smart_meeting.screens.ScannerScreen
import com.example.smart_meeting.screens.ScannerViewModel
import kotlin.math.absoluteValue

data class Ref<T>(var value: T)

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
    val scannerViewModel: ScannerViewModel = viewModel()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // 创建共享的页面索引状态
    var currentPageIndex by remember { mutableStateOf(0) }
    var drawerPageIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // 页面列表
    val pages = remember {
        listOf(
            BottomNavigationItem.Features,
            BottomNavigationItem.Meetings,
            BottomNavigationItem.Profile
        )
    }

    // 监听导航变化
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            val route = entry.destination.route
            val index = pages.indexOfFirst { it.route == route }
            if (index != -1) {
                currentPageIndex = index
            }
        }
    }

    // 页面切换函数
    fun navigateToPage(index: Int) {
        if (index in pages.indices && index != currentPageIndex) {
            currentPageIndex = index
            navController.navigate(pages[index].route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    ModalNavigationDrawer(
        drawerContent = {
            when (drawerPageIndex){
                0 -> SettingsDrawer()
                1 -> NotificationDrawer()
            }
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            topBar = {
                TopNavigationBar(
                    navController = navController,
                    currentIndex = currentPageIndex,
                    onSettingsClick = {
                        drawerPageIndex = 0
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    onNotificationClick = {
                        drawerPageIndex = 1
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    onScannerClick = {
                        navController.navigate(BottomNavigationItem.Scanner.route){
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    selectedIndex = currentPageIndex,
                    onPageSelected = { index -> navigateToPage(index) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .pointerInput(Unit) {
                        var dragStart = 0f
                        var dragDistance = 0f

                        detectHorizontalDragGestures(
                            onDragStart = { dragStart = 0f },
                            onDragEnd = {
                                if (dragDistance.absoluteValue > 10f) {
                                    val nextIndex = if (dragDistance > 0) {
                                        (currentPageIndex - 1).coerceAtLeast(0)
                                    } else {
                                        (currentPageIndex + 1).coerceAtMost(pages.size - 1)
                                    }
                                    navigateToPage(nextIndex)
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
                NavHost(
                    navController = navController,
                    startDestination = pages[1].route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(BottomNavigationItem.Meetings.route) { MeetingsScreen() }
                    composable(BottomNavigationItem.Features.route) { FeaturesScreen() }
                    composable(BottomNavigationItem.Profile.route) { ProfileScreen() }
                    composable(BottomNavigationItem.Scanner.route) { ScannerScreen(
                        onCodeScanned = { result ->

                        },
                        viewModel = scannerViewModel
                    ) }
                }
            }
        }
    }
}

