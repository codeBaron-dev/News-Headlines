package com.codebaron.headlines.application

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codebaron.headlines.Utilities.DISCONNECTED
import com.codebaron.headlines.Utilities.Destinations.DETAILS_SCREEN
import com.codebaron.headlines.Utilities.Destinations.LIST_SCREEN
import com.codebaron.headlines.Utilities.HEADLINE_URL
import com.codebaron.headlines.Utilities.TEST_STRING
import com.codebaron.headlines.Utilities.isNetworkAvailable
import com.codebaron.headlines.application.ui.NewsDetails
import com.codebaron.headlines.application.ui.NewsListScreen
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
                    }
                    NavHost(navController = navController, startDestination = LIST_SCREEN) {
                        composable(LIST_SCREEN) {
                            NewsListScreen(navController, this@MainActivity)
                        }
                        composable(
                            "${DETAILS_SCREEN}/{$HEADLINE_URL}",
                            arguments = listOf(navArgument(HEADLINE_URL) {
                                defaultValue = TEST_STRING
                            })
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getString(HEADLINE_URL)
                                ?.let { NewsDetails(it, this@MainActivity) }
                        }
                    }
                }
            }
        }
    }
}