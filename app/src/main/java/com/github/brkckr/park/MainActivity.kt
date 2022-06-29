package com.github.brkckr.park

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.github.brkckr.park.ui.navigation.ParkNavigation
import com.github.brkckr.park.ui.theme.ParkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkTheme {
                ParkNavigation()
            }
        }
    }
}

/*@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screen.ParkMapScreen.route) {
        composable(route = Screen.ParkMapScreen.route) {
            ParkMapScreen(navController)
        }
        composable(
            route = Screen.ParkDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            ParkDetailScreen(id = entry.arguments?.getInt("id"))
        }
    }

}

sealed class Screen(val route: String) {
    object ParkMapScreen : Screen("park_map_screen")
    object ParkDetailScreen : Screen("park_detail_screen")

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}*/