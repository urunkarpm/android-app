package com.funcalc.app.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Color definitions for the app themes
 */
object AppColors {
    // Rainbow theme (default)
    val RainbowPrimary = Color(0xFF6200EE)
    val RainbowSecondary = Color(0xFF03DAC6)
    val RainbowBackground = Color(0xFFFFFBFE)
    val RainbowSurface = Color(0xFFFFFFFF)
    val RainbowNumberButton = Color(0xFFFFFFFF)
    val RainbowOperatorButton = Color(0xFF6200EE)
    val RainbowSpecialButton = Color(0xFFFF9800)

    // Ocean theme
    val OceanPrimary = Color(0xFF0288D1)
    val OceanSecondary = Color(0xFF00ACC1)
    val OceanBackground = Color(0xFFE0F7FA)
    val OceanSurface = Color(0xFFFFFFFF)
    val OceanNumberButton = Color(0xFFFFFFFF)
    val OceanOperatorButton = Color(0xFF0288D1)
    val OceanSpecialButton = Color(0xFF00BCD4)

    // Forest theme
    val ForestPrimary = Color(0xFF388E3C)
    val ForestSecondary = Color(0xFF7CB342)
    val ForestBackground = Color(0xFFF1F8E9)
    val ForestSurface = Color(0xFFFFFFFF)
    val ForestNumberButton = Color(0xFFFFFFFF)
    val ForestOperatorButton = Color(0xFF388E3C)
    val ForestSpecialButton = Color(0xFF8BC34A)

    // Space theme
    val SpacePrimary = Color(0xFF7B1FA2)
    val SpaceSecondary = Color(0xFFE040FB)
    val SpaceBackground = Color(0xFF1A1A2E)
    val SpaceSurface = Color(0xFF16213E)
    val SpaceNumberButton = Color(0xFF0F3460)
    val SpaceOperatorButton = Color(0xFF7B1FA2)
    val SpaceSpecialButton = Color(0xFFE040FB)

    // Fun colors
    val Red = Color(0xFFE53935)
    val Pink = Color(0xFFE91E63)
    val Purple = Color(0xFF9C27B0)
    val DeepPurple = Color(0xFF673AB7)
    val Indigo = Color(0xFF3F51B5)
    val Blue = Color(0xFF2196F3)
    val Teal = Color(0xFF009688)
    val Green = Color(0xFF4CAF50)
    val Yellow = Color(0xFFFFEB3B)
    val Orange = Color(0xFFFF9800)
}

/**
 * App theme data class
 */
data class AppTheme(
    val name: String,
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val numberButton: Color,
    val operatorButton: Color,
    val specialButton: Color,
    val isDark: Boolean = false
)

/**
 * Available themes
 */
object Themes {
    val rainbow = AppTheme(
        name = "rainbow",
        primary = AppColors.RainbowPrimary,
        secondary = AppColors.RainbowSecondary,
        background = AppColors.RainbowBackground,
        surface = AppColors.RainbowSurface,
        numberButton = AppColors.RainbowNumberButton,
        operatorButton = AppColors.RainbowOperatorButton,
        specialButton = AppColors.RainbowSpecialButton
    )

    val ocean = AppTheme(
        name = "ocean",
        primary = AppColors.OceanPrimary,
        secondary = AppColors.OceanSecondary,
        background = AppColors.OceanBackground,
        surface = AppColors.OceanSurface,
        numberButton = AppColors.OceanNumberButton,
        operatorButton = AppColors.OceanOperatorButton,
        specialButton = AppColors.OceanSpecialButton
    )

    val forest = AppTheme(
        name = "forest",
        primary = AppColors.ForestPrimary,
        secondary = AppColors.ForestSecondary,
        background = AppColors.ForestBackground,
        surface = AppColors.ForestSurface,
        numberButton = AppColors.ForestNumberButton,
        operatorButton = AppColors.ForestOperatorButton,
        specialButton = AppColors.ForestSpecialButton
    )

    val space = AppTheme(
        name = "space",
        primary = AppColors.SpacePrimary,
        secondary = AppColors.SpaceSecondary,
        background = AppColors.SpaceBackground,
        surface = AppColors.SpaceSurface,
        numberButton = AppColors.SpaceNumberButton,
        operatorButton = AppColors.SpaceOperatorButton,
        specialButton = AppColors.SpaceSpecialButton,
        isDark = true
    )

    val all = listOf(rainbow, ocean, forest, space)

    fun getTheme(name: String): AppTheme = all.find { it.name == name } ?: rainbow
}
