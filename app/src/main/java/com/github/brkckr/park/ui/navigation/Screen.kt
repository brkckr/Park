package com.github.brkckr.park.ui.navigation

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
}