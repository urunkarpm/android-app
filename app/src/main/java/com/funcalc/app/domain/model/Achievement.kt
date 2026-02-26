package com.funcalc.app.domain.model

/**
 * Represents an achievement badge that can be earned
 */
data class Achievement(
    val id: Long = 0,
    val badgeName: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

/**
 * Predefined achievement types
 */
object Achievements {
    val FIRST_CALCULATION = Achievement(
        id = 1,
        badgeName = "First Calculation",
        description = "Complete your first calculation!",
        iconName = "star"
    )
    val TEN_CALCULATIONS = Achievement(
        id = 2,
        badgeName = "Math Explorer",
        description = "Complete 10 calculations!",
        iconName = "explore"
    )
    val FIFTY_CALCULATIONS = Achievement(
        id = 3,
        badgeName = "Number Wizard",
        description = "Complete 50 calculations!",
        iconName = "auto_awesome"
    )
    val HUNDRED_CALCULATIONS = Achievement(
        id = 4,
        badgeName = "Math Champion",
        description = "Complete 100 calculations!",
        iconName = "emoji_events"
    )
    val ALL_OPERATIONS = Achievement(
        id = 5,
        badgeName = "Operation Master",
        description = "Use all four operations (+, -, ×, ÷)!",
        iconName = "military_tech"
    )

    val all = listOf(FIRST_CALCULATION, TEN_CALCULATIONS, FIFTY_CALCULATIONS, HUNDRED_CALCULATIONS, ALL_OPERATIONS)
}
