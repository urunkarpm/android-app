package com.funcalc.app.domain.usecase

import com.funcalc.app.data.repository.AchievementRepository
import com.funcalc.app.data.repository.SettingsRepository
import com.funcalc.app.domain.model.Achievement
import com.funcalc.app.domain.model.Achievements
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for calculating and checking achievements
 */
class CalculateUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val achievementRepository: AchievementRepository
) {
    /**
     * Perform calculation and check for new achievements
     */
    suspend fun performCalculation(operation: String): CalculationResult {
        // Increment calculation count
        settingsRepository.incrementCalculationCount()

        // Add operation to used operations
        settingsRepository.addOperationUsed(operation)

        // Check for achievements
        val settings = settingsRepository.settings
        settings.collect { currentSettings ->
            // Check first calculation achievement
            if (currentSettings.totalCalculations == 1) {
                achievementRepository.unlockAchievement(Achievements.FIRST_CALCULATION.id)
            }

            // Check 10 calculations achievement
            if (currentSettings.totalCalculations >= 10) {
                achievementRepository.unlockAchievement(Achievements.TEN_CALCULATIONS.id)
            }

            // Check 50 calculations achievement
            if (currentSettings.totalCalculations >= 50) {
                achievementRepository.unlockAchievement(Achievements.FIFTY_CALCULATIONS.id)
            }

            // Check 100 calculations achievement
            if (currentSettings.totalCalculations >= 100) {
                achievementRepository.unlockAchievement(Achievements.HUNDRED_CALCULATIONS.id)
            }

            // Check all operations achievement
            if (currentSettings.operationsUsed.size >= 4) {
                achievementRepository.unlockAchievement(Achievements.ALL_OPERATIONS.id)
            }
        }

        return CalculationResult.Success
    }
}

sealed class CalculationResult {
    object Success : CalculationResult()
    data class Error(val message: String) : CalculationResult()
}
