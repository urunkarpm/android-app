package com.funcalc.app.presentation.calculator

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.funcalc.app.domain.model.Operation
import com.funcalc.app.presentation.components.CalculatorDisplay
import com.funcalc.app.presentation.components.FunFactDialog
import com.funcalc.app.presentation.components.SpecialButton
import com.funcalc.app.presentation.theme.AppColors
import com.funcalc.app.presentation.theme.AppTheme
import com.funcalc.app.presentation.theme.Themes
import kotlinx.coroutines.launch

/**
 * Main calculator screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val achievements by viewModel.achievements.collectAsState()

    var showSettings by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Sound pool for button sounds
    var soundPool: SoundPool? by remember { mutableStateOf(null) }
    var buttonSoundId: Int by remember { mutableStateOf(0) }

    // Initialize sound pool
    DisposableEffect(Unit) {
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .build()

        // We'll generate a simple beep programmatically
        // or skip sound for simplicity

        onDispose {
            soundPool?.release()
            soundPool = null
        }
    }

    // Get current theme
    val currentTheme = remember(settings.selectedTheme) {
        Themes.getTheme(settings.selectedTheme)
    }

    // Play sound if enabled
    val playSound: () -> Unit = {
        if (settings.soundEnabled) {
            // Sound would play here - simplified for now
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "FunCalc",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { showSettings = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = currentTheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(currentTheme.background)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display area
                CalculatorDisplay(
                    display = uiState.display,
                    modifier = Modifier.fillMaxWidth(),
                    isDark = currentTheme.isDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Error message
                if (uiState.errorMessage != null) {
                    Text(
                        text = uiState.errorMessage!!,
                        color = AppColors.Red,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .background(
                                Color.White.copy(alpha = 0.9f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Calculator buttons
                CalculatorButtons(
                    theme = currentTheme,
                    onNumberClick = { number ->
                        playSound()
                        viewModel.onNumberClick(number)
                    },
                    onOperationClick = { operation ->
                        playSound()
                        viewModel.onOperationClick(operation)
                    },
                    onEqualsClick = {
                        playSound()
                        viewModel.onEqualsClick()
                    },
                    onClearClick = {
                        playSound()
                        viewModel.onClearClick()
                    },
                    onAllClearClick = {
                        playSound()
                        viewModel.onAllClearClick()
                    },
                    onDecimalClick = {
                        playSound()
                        viewModel.onDecimalClick()
                    },
                    onSurpriseClick = {
                        playSound()
                        viewModel.onSurpriseClick()
                    }
                )
            }

            // Fun fact dialog
            if (uiState.showFunFact && uiState.funFact != null) {
                FunFactDialog(
                    funFact = uiState.funFact!!,
                    onDismiss = { viewModel.dismissFunFact() },
                    onContinue = { viewModel.dismissFunFact() }
                )
            }

            // Settings bottom sheet
            if (showSettings) {
                ModalBottomSheet(
                    onDismissRequest = { showSettings = false },
                    sheetState = sheetState,
                    containerColor = currentTheme.surface
                ) {
                    SettingsContent(
                        currentTheme = currentTheme,
                        soundEnabled = settings.soundEnabled,
                        totalCalculations = settings.totalCalculations,
                        achievements = achievements,
                        onThemeChange = { themeName ->
                            // Handled via viewModel
                        },
                        onSoundToggle = { enabled ->
                            // Handled via viewModel
                        }
                    )
                }
            }
        }
    }
}

/**
 * Calculator buttons grid
 */
@Composable
private fun CalculatorButtons(
    theme: AppTheme,
    onNumberClick: (String) -> Unit,
    onOperationClick: (Operation) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit,
    onAllClearClick: () -> Unit,
    onDecimalClick: () -> Unit,
    onSurpriseClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: 7, 8, 9, ÷, C
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton("7", theme, Modifier.weight(1f), onNumberClick)
            NumberButton("8", theme, Modifier.weight(1f), onNumberClick)
            NumberButton("9", theme, Modifier.weight(1f), onNumberClick)
            OperatorButton("÷", theme, Modifier.weight(1f)) { onOperationClick(Operation.DIVIDE) }
            SpecialButton("C", Modifier.weight(1f), theme.specialButton, onClick = onClearClick)
        }

        // Row 2: 4, 5, 6, ×, AC
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton("4", theme, Modifier.weight(1f), onNumberClick)
            NumberButton("5", theme, Modifier.weight(1f), onNumberClick)
            NumberButton("6", theme, Modifier.weight(1f), onNumberClick)
            OperatorButton("×", theme, Modifier.weight(1f)) { onOperationClick(Operation.MULTIPLY) }
            SpecialButton("AC", Modifier.weight(1f), theme.specialButton, onClick = onAllClearClick)
        }

        // Row 3: 1, 2, 3, -, =
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton("1", theme, Modifier.weight(1f), onNumberClick)
            NumberButton("2", theme, Modifier.weight(1f), onNumberClick)
            NumberButton("3", theme, Modifier.weight(1f), onNumberClick)
            OperatorButton("-", theme, Modifier.weight(1f)) { onOperationClick(Operation.SUBTRACT) }
            EqualsButton(theme, Modifier.weight(1f), onEqualsClick)
        }

        // Row 4: 0, ., +, ?, 🎉
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberButton("0", theme, Modifier.weight(1f), onNumberClick)
            DecimalButton(theme, Modifier.weight(1f), onDecimalClick)
            OperatorButton("+", theme, Modifier.weight(1f)) { onOperationClick(Operation.ADD) }
            SurpriseButton(theme, Modifier.weight(1f), onSurpriseClick)
            EqualsButton(theme, Modifier.weight(1f), onEqualsClick)
        }
    }
}

@Composable
private fun NumberButton(
    text: String,
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    com.funcalc.app.presentation.components.CalcButton(
        text = text,
        modifier = modifier,
        backgroundColor = theme.numberButton,
        textColor = if (theme.isDark) Color.White else theme.primary,
        fontSize = 28,
        onClick = { onClick(text) }
    )
}

@Composable
private fun OperatorButton(
    symbol: String,
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    com.funcalc.app.presentation.components.OperatorButton(
        symbol = symbol,
        modifier = modifier,
        backgroundColor = theme.operatorButton,
        onClick = onClick
    )
}

@Composable
private fun EqualsButton(
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    com.funcalc.app.presentation.components.SpecialButton(
        text = "=",
        modifier = modifier,
        backgroundColor = theme.secondary,
        onClick = onClick
    )
}

@Composable
private fun DecimalButton(
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    com.funcalc.app.presentation.components.CalcButton(
        text = ".",
        modifier = modifier,
        backgroundColor = theme.specialButton.copy(alpha = 0.8f),
        textColor = Color.White,
        fontSize = 32,
        onClick = onClick
    )
}

@Composable
private fun SurpriseButton(
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    com.funcalc.app.presentation.components.SpecialButton(
        text = "?",
        modifier = modifier,
        backgroundColor = AppColors.Pink,
        textColor = Color.White,
        onClick = onClick
    )
}
