package com.rahim.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.messaging.FirebaseMessaging
import com.rahim.yadino.designsystem.dialog.ErrorDialog
import com.rahim.navigation.NavigationComponent
import com.rahim.yadino.navigation.component.YadinoNavigationDrawer
import com.rahim.yadino.designsystem.theme.CornflowerBlueLight
import com.rahim.yadino.designsystem.theme.YadinoTheme
import com.rahim.yadino.base.component.TopBarCenterAlign
import com.rahim.yadino.base.component.goSettingPermission
import com.rahim.yadino.base.component.requestPermissionNotification
import com.rahim.yadino.R
import com.rahim.yadino.navigation.BottomNavItem
import com.rahim.yadino.navigation.Destinations
import com.rahim.yadino.navigation.component.BottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        getTokenFirebase()
        setContent {
            val context = LocalContext.current
            (context as? Activity)?.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            YadinoApp(
                isShowWelcomeScreen = mainViewModel.isShowWelcomeScreen(),
                isDarkTheme = if (mainViewModel.isDarkTheme() == DARK) true else if (mainViewModel.isDarkTheme() == LIGHT) false else isSystemInDarkTheme(),
                changeTheme = {
                    if (it) {
                        mainViewModel.setDarkTheme(DARK)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            this@MainActivity.splashScreen.setSplashScreenTheme(R.style.Theme_dark)
                        }
                    } else {
                        mainViewModel.setDarkTheme(LIGHT)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            this@MainActivity.splashScreen.setSplashScreenTheme(R.style.Theme_Light)
                        }
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.checkedAllRoutinePastTime()
    }

    private fun getTokenFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun YadinoApp(
    isShowWelcomeScreen: Boolean,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    changeTheme: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val notificationPermissionState =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var errorClick by rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()
    var clickSearch by rememberSaveable { mutableStateOf(false) }

    val destination = navController.currentBackStackEntry?.destination?.route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destinationNavBackStackEntry = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var darkThemeState by remember {
        mutableStateOf(isDarkTheme)
    }
    YadinoTheme(darkTheme = darkThemeState) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            YadinoNavigationDrawer(modifier = Modifier.width(240.dp),
                drawerState = drawerState,
                isDarkTheme = darkThemeState,
                onItemClick = { drawerItemType ->
                    when (drawerItemType) {
                        is DrawerItemType.ShareWithFriends -> {
                            val shareLink = CAFE_BAZZAR_LINK
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareLink)
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(context, shareIntent, null)
                        }

                        is DrawerItemType.RateToApp -> {
                            if (!isPackageInstalled(
                                    CAFE_BAZAAR_PACKAGE_NAME,
                                    context.packageManager
                                )
                            ) {
                                Toast.makeText(
                                    context,
                                    context.resources.getString(R.string.install_cafeBazaar),
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@YadinoNavigationDrawer
                            }
                            val intent = Intent(Intent.ACTION_EDIT)
                            intent.setData(Uri.parse("bazaar://details?id=${context.packageName}"))
                            intent.setPackage(CAFE_BAZAAR_PACKAGE_NAME)
                            startActivity(context, intent, null)

                        }

                        is DrawerItemType.Theme -> {
                            darkThemeState = !darkThemeState
                            changeTheme(darkThemeState)
                        }

                        else -> {}
                    }

                }) {
                Scaffold(
                    topBar = {
                        AnimatedVisibility(
                            visible = destinationNavBackStackEntry != BottomNavItem.Welcome.route,
                            enter = fadeIn() + expandVertically(animationSpec = tween(800)),
                            exit = fadeOut() + shrinkVertically(animationSpec = tween(800))
                        ) {
                            com.rahim.yadino.base.component.TopBarCenterAlign(title = when (destinationNavBackStackEntry) {
                                BottomNavItem.Home.route -> stringResource(
                                    id = R.string.my_firend
                                )

                                BottomNavItem.Routine.route -> stringResource(
                                    id = R.string.list_routine
                                )

                                Destinations.HISTORY.nameScreen -> stringResource(id = R.string.historyAlarm)

                                else -> stringResource(id = R.string.notes)
                            },
                                openHistory = {
                                    navController.navigate(Destinations.HISTORY.nameScreen)
                                },
                                isShowSearchIcon = destinationNavBackStackEntry != BottomNavItem.Calender.route && destinationNavBackStackEntry != Destinations.HISTORY.nameScreen,
                                isShowBackIcon = destinationNavBackStackEntry == Destinations.HISTORY.nameScreen,
                                onClickBack = {
                                    navController.popBackStack()
                                },
                                onClickSearch = {
                                    clickSearch = !clickSearch
                                },
                                onDrawerClick = {
                                    coroutineScope.launch { drawerState.open() }
                                },
                            )
                        }
                    }, floatingActionButton = {
                        if (destinationNavBackStackEntry != BottomNavItem.Welcome.route && destinationNavBackStackEntry != Destinations.HISTORY.nameScreen) {
                            FloatingActionButton(containerColor = CornflowerBlueLight,
                                contentColor = Color.White,
                                onClick = {
                                    com.rahim.yadino.base.component.requestPermissionNotification(
                                        isGranted = {
                                            if (it) openDialog = true
                                            else errorClick = true
                                        },
                                        permissionState = {
                                            it.launchPermissionRequest()
                                        },
                                        notificationPermission = notificationPermissionState
                                    )
                                }) {
                                Icon(Icons.Filled.Add, "add item")
                            }
                        }
                    }, bottomBar = {
                        AnimatedVisibility(
                            visible = destinationNavBackStackEntry != BottomNavItem.Welcome.route && destinationNavBackStackEntry != Destinations.HISTORY.nameScreen,
                            enter = fadeIn() + expandVertically(animationSpec = tween(800)),
                            exit = fadeOut() + shrinkVertically(animationSpec = tween(800))
                        ) {
                            BottomNavigationBar(
                                navController,
                                navBackStackEntry,
                                destinationNavBackStackEntry,
                            )
                        }
                    }, containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    NavigationComponent(navController,
                        innerPadding = innerPadding,
                        startDestination = if (isShowWelcomeScreen) Screen.Home.route else Screen.Welcome.route,
                        openDialog = openDialog,
                        clickSearch = clickSearch,
                        onOpenDialog = { isOpen ->
                            openDialog = isOpen
                        })
                    if (destination == Screen.Home.route) requestPermissionNotification(
                        notificationPermission = notificationPermissionState,
                        isGranted = {},
                        permissionState = {
                            it.launchPermissionRequest()
                        })
                }
            }
        }
    }
    ErrorDialog(isOpen = errorClick,
        message = stringResource(id = R.string.better_performance_access),
        okMessage = stringResource(id = R.string.setting),
        isClickOk = {
            if (it) {
                goSettingPermission(context)
            }
            errorClick = false
        })

}

private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
    try {
        packageManager.getPackageInfo(packageName, 0)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
}