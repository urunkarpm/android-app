package com.funcalc.app.data.repository

import com.funcalc.app.data.local.dao.AchievementDao
import com.funcalc.app.domain.model.Achievement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for achievements
 */
@Singleton
class AchievementRepository @Inject constructor(
    private val achievementDao: AchievementDao
) {
    fun getAllAchievements(): Flow<List<Achievement>> =
        achievementDao.getAllAchievements().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun getAchievementById(id: Long): Achievement? =
        achievementDao.getAchievementById(id)?.toDomain()

    suspend fun unlockAchievement(id: Long) {
        achievementDao.unlockAchievement(id, System.currentTimeMillis())
    }

    suspend fun getAchievementsCount(): Int = achievementDao.getAchievementsCount()

    suspend fun insertAchievements(achievements: List<Achievement>) {
        achievementDao.insertAchievements(achievements.map {
            com.funcalc.app.data.local.entity.AchievementEntity.fromDomain(it)
        })
    }
}
