package com.funcalc.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.funcalc.app.presentation.calculator.CalculatorScreen
import com.funcalc.app.presentation.calculator.CalculatorViewModel
import com.funcalc.app.presentation.theme.FunCalcTheme
import com.funcalc.app.presentation.theme.Themes
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the FunCalc app
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: CalculatorViewModel = hiltViewModel()
            val settings by viewModel.settings.collectAsState()

            val currentTheme = Themes.getTheme(settings.selectedTheme)

            FunCalcTheme(appTheme = currentTheme) {
                CalculatorScreen(viewModel = viewModel)
            }
        }
    }
}
