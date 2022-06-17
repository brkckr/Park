package com.github.brkckr.park.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = MapLight,
    primaryVariant = MapDark,
    secondary = Logo4,
    background = MapDark,
    surface = MapDark
)

@Composable
fun ParkTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}