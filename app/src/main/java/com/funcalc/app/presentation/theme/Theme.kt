package com.funcalc.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FunCalcTheme(
    appTheme: AppTheme = Themes.rainbow,
    content: @Composable () -> Unit
) {
    val colorScheme = if (appTheme.isDark) {
        darkColorScheme(
            primary = appTheme.primary,
            secondary = appTheme.secondary,
            background = appTheme.background,
            surface = appTheme.surface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = appTheme.primary,
            secondary = appTheme.secondary,
            background = appTheme.background,
            surface = appTheme.surface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
