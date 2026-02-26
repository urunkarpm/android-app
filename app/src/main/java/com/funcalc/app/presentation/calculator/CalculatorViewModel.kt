package com.funcalc.app.presentation.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funcalc.app.data.repository.AchievementRepository
import com.funcalc.app.data.repository.AppSettings
import com.funcalc.app.data.repository.FunFactRepository
import com.funcalc.app.data.repository.SettingsRepository
import com.funcalc.app.domain.model.Achievement
import com.funcalc.app.domain.model.FunFact
import com.funcalc.app.domain.model.Operation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for the calculator screen
 */
data class CalculatorUiState(
    val display: String = "0",
    val currentNumber: String = "0",
    val previousNumber: String? = null,
    val currentOperation: Operation? = null,
    val isResultShown: Boolean = false,
    val funFact: FunFact? = null,
    val showFunFact: Boolean = false,
    val showCelebration: Boolean = false,
    val newAchievement: Achievement? = null,
    val errorMessage: String? = null
)

/**
 * ViewModel for the calculator screen
 */
@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val funFactRepository: FunFactRepository,
    private val achievementRepository: AchievementRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    val settings: StateFlow<AppSettings> = settingsRepository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettings())

    val achievements: StateFlow<List<Achievement>> = achievementRepository.getAllAchievements()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Calculator state
    private var currentNumber = "0"
    private var previousNumber: Double? = null
    private var currentOperation: Operation? = null
    private var shouldResetDisplay = false

    /**
     * Handle number button press
     */
    fun onNumberClick(number: String) {
        val state = _uiState.value
        if (state.isResultShown || state.showFunFact) {
            // Start fresh after result or fun fact
            currentNumber = number
            shouldResetDisplay = false
        } else if (shouldResetDisplay) {
            // Start fresh after operation button was pressed
            currentNumber = number
            shouldResetDisplay = false
        } else {
            // Append to current number
            currentNumber = if (currentNumber == "0" && number != ".") {
                number
            } else if (number == "." && currentNumber.contains(".")) {
                currentNumber
            } else {
                if (currentNumber.length < 15) currentNumber + number else currentNumber
            }
        }

        _uiState.value = state.copy(
            display = currentNumber,
            currentNumber = currentNumber,
            isResultShown = false,
            showFunFact = false,
            errorMessage = null
        )
    }

    /**
     * Handle operation button press
     */
    fun onOperationClick(operation: Operation) {
        val state = _uiState.value

        if (previousNumber != null && currentOperation != null && !shouldResetDisplay) {
            // Calculate intermediate result
            calculateResult()
        }

        previousNumber = currentNumber.toDoubleOrNull() ?: 0.0
        currentOperation = operation
        shouldResetDisplay = true

        _uiState.value = state.copy(
            previousNumber = previousNumber?.toString(),
            currentOperation = operation,
            isResultShown = false
        )
    }

    /**
     * Handle equals button press - calculate result
     */
    fun onEqualsClick() {
        val state = _uiState.value

        if (currentOperation == null || previousNumber == null) {
            return
        }

        val result = calculate(currentNumber.toDoubleOrNull() ?: 0.0)
        if (result != null) {
            currentNumber = formatResult(result)
            shouldResetDisplay = true

            _uiState.value = state.copy(
                display = currentNumber,
                currentNumber = currentNumber,
                isResultShown = true,
                showCelebration = true
            )

            // Show fun fact after calculation
            showFunFactAfterCalculation()
        }
    }

    /**
     * Handle clear button press
     */
    fun onClearClick() {
        currentNumber = "0"
        _uiState.value = CalculatorUiState()
    }

    /**
     * Handle all clear button press
     */
    fun onAllClearClick() {
        currentNumber = "0"
        previousNumber = null
        currentOperation = null
        shouldResetDisplay = false
        _uiState.value = CalculatorUiState()
    }

    /**
     * Handle decimal button press
     */
    fun onDecimalClick() {
        if (!currentNumber.contains(".")) {
            currentNumber = "$currentNumber."
            _uiState.value = _uiState.value.copy(
                display = currentNumber,
                currentNumber = currentNumber
            )
        }
    }

    /**
     * Handle surprise fact button press
     */
    fun onSurpriseClick() {
        viewModelScope.launch {
            val fact = funFactRepository.getRandomFunFact()
            _uiState.value = _uiState.value.copy(
                funFact = fact,
                showFunFact = true,
                isResultShown = false
            )
        }
    }

    /**
     * Dismiss fun fact dialog
     */
    fun dismissFunFact() {
        _uiState.value = _uiState.value.copy(
            showFunFact = false
        )
    }

    /**
     * Dismiss celebration animation
     */
    fun dismissCelebration() {
        _uiState.value = _uiState.value.copy(
            showCelebration = false
        )
    }

    /**
     * Dismiss new achievement notification
     */
    fun dismissAchievement() {
        _uiState.value = _uiState.value.copy(
            newAchievement = null
        )
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun calculateResult() {
        val result = calculate(currentNumber.toDoubleOrNull() ?: 0.0)
        if (result != null) {
            currentNumber = formatResult(result)
            shouldResetDisplay = true
        }
    }

    private fun calculate(secondNumber: Double): Double? {
        val firstNumber = previousNumber ?: return null

        return when (currentOperation) {
            Operation.ADD -> firstNumber + secondNumber
            Operation.SUBTRACT -> firstNumber - secondNumber
            Operation.MULTIPLY -> firstNumber * secondNumber
            Operation.DIVIDE -> {
                if (secondNumber == 0.0) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Cannot divide by zero!",
                        showFunFact = false
                    )
                    return null
                }
                firstNumber / secondNumber
            }
            null -> secondNumber
        }
    }

    private fun formatResult(result: Double): String {
        return if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            String.format("%.8f", result).trimEnd('0').trimEnd('.')
        }
    }

    private fun showFunFactAfterCalculation() {
        viewModelScope.launch {
            // Get a random fun fact
            val fact = funFactRepository.getRandomFunFact()
            _uiState.value = _uiState.value.copy(
                funFact = fact,
                showFunFact = true
            )

            // Check for achievements
            checkAchievements()

            // Save progress
            currentOperation?.let { op ->
                settingsRepository.incrementCalculationCount()
                settingsRepository.addOperationUsed(op.name)
            }
        }
    }

    private fun checkAchievements() {
        viewModelScope.launch {
            val currentSettings = settings.value
            val totalCalcs = currentSettings.totalCalculations

            // Check first calculation
            if (totalCalcs >= 1) {
                val firstBadge = achievementRepository.getAchievementById(1)
                if (firstBadge != null && !firstBadge.isUnlocked) {
                    achievementRepository.unlockAchievement(1)
                    _uiState.value = _uiState.value.copy(newAchievement = firstBadge)
                }
            }

            // Check 10 calculations
            if (totalCalcs >= 10) {
                val badge = achievementRepository.getAchievementById(2)
                if (badge != null && !badge.isUnlocked) {
                    achievementRepository.unlockAchievement(2)
                    _uiState.value = _uiState.value.copy(newAchievement = badge)
                }
            }

            // Check 50 calculations
            if (totalCalcs >= 50) {
                val badge = achievementRepository.getAchievementById(3)
                if (badge != null && !badge.isUnlocked) {
                    achievementRepository.unlockAchievement(3)
                    _uiState.value = _uiState.value.copy(newAchievement = badge)
                }
            }

            // Check 100 calculations
            if (totalCalcs >= 100) {
                val badge = achievementRepository.getAchievementById(4)
                if (badge != null && !badge.isUnlocked) {
                    achievementRepository.unlockAchievement(4)
                    _uiState.value = _uiState.value.copy(newAchievement = badge)
                }
            }

            // Check all operations
            if (currentSettings.operationsUsed.size >= 4) {
                val badge = achievementRepository.getAchievementById(5)
                if (badge != null && !badge.isUnlocked) {
                    achievementRepository.unlockAchievement(5)
                    _uiState.value = _uiState.value.copy(newAchievement = badge)
                }
            }
        }
    }
}
