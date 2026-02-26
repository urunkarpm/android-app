package com.funcalc.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.funcalc.app.domain.model.Achievement

/**
 * Room entity for storing achievements
 */
@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val id: Long,
    val badgeName: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
) {
    fun toDomain(): Achievement = Achievement(
        id = id,
        badgeName = badgeName,
        description = description,
        iconName = iconName,
        isUnlocked = isUnlocked,
        unlockedAt = unlockedAt
    )

    companion object {
        fun fromDomain(achievement: Achievement): AchievementEntity = AchievementEntity(
            id = achievement.id,
            badgeName = achievement.badgeName,
            description = achievement.description,
            iconName = achievement.iconName,
            isUnlocked = achievement.isUnlocked,
            unlockedAt = achievement.unlockedAt
        )
    }
}
