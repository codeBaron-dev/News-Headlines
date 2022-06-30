package com.codebaron.headlines.application

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codebaron.headlines.Navigation.Destinations
import com.codebaron.headlines.Utilities.*
import com.codebaron.headlines.application.ui.NewsDetails
import com.codebaron.headlines.application.ui.NewsListScreen
import com.codebaron.headlines.roomdb.NewsRoomDatabase
import com.codebaron.headlines.ui.theme.HeadlinesTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeadlinesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )

                    val navController = rememberNavController()
                    if (!isNetworkAvailable(this)) {
                        Toast.makeText(this, DISCONNECTED, Toast.LENGTH_LONG).show()
                    } else {
                        NewsRoomDatabase(this).NewsDao().deleteAll()
                    }
                    BottomNavBar(navController)
                }
            }
        }
    }


    @Composable
    fun BottomNavBar(navController: NavHostController) {
        Scaffold(
            bottomBar = { BottomNav(navController) }
        ) {
            ScreenNavigation(navController = navController)
        }
    }
    @Composable
    fun ScreenNavigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = Destinations.LIST_SCREEN) {
            composable(Destinations.LIST_SCREEN) {
                NewsListScreen(navController, this@MainActivity)
            }
            composable(
                "${Destinations.DETAILS_SCREEN}/{$HEADLINE_URL}",
                arguments = listOf(navArgument(HEADLINE_URL) {
                    defaultValue = TEST_STRING
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(HEADLINE_URL)
                    ?.let { NewsDetails(it, this@MainActivity) }
            }
        }
    }

    @Composable
    fun BottomNav(navController: NavController) {
        BottomNavigation {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
            NAVIGATION_OBJECTS.forEach { route ->
                BottomNavigationItem(
                    icon = {
                        Image(
                            painter = painterResource(id = route.icon),
                            contentDescription = route.title
                        )
                    },
                    label = {
                        Text(
                            text = route.title
                        )
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.1f),
                    alwaysShowLabel = true,
                    selected = currentRoute == route.destinations,
                    onClick = {
                        navController.navigate(route.destinations) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
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
    }
}