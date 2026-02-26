package com.funcalc.app.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.funcalc.app.domain.model.FunFact
import com.funcalc.app.presentation.theme.AppColors
import kotlinx.coroutines.delay

/**
 * Calculator display component showing current calculation
 */
@Composable
fun CalculatorDisplay(
    display: String,
    modifier: Modifier = Modifier,
    isDark: Boolean = false
) {
    var isAnimating by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    LaunchedEffect(display) {
        isAnimating = true
        delay(150)
        isAnimating = false
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 24.dp, vertical = 32.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = display,
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDark) Color.White else MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            textAlign = TextAlign.End
        )
    }
}

/**
 * Dialog showing a fun fact
 */
@Composable
fun FunFactDialog(
    funFact: FunFact,
    onDismiss: () -> Unit,
    onContinue: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Did you know?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Fun fact
                Text(
                    text = funFact.fact,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                // Category chip
                Box(
                    modifier = Modifier
                        .background(
                            color = getCategoryColor(funFact.category.name),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = funFact.category.name.replace("_", " "),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                // Continue button
                androidx.compose.material3.Button(
                    onClick = onContinue,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Cool!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Surprise fact dialog (without calculation)
 */
@Composable
fun SurpriseFactDialog(
    funFact: FunFact?,
    onDismiss: () -> Unit
) {
    if (funFact == null) return

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Surprise title
                Text(
                    text = "Surprise!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Pink
                )

                // Fun fact
                Text(
                    text = funFact.fact,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                // Close button
                androidx.compose.material3.Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Awesome!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Celebration overlay with sparkles
 */
@Composable
fun CelebrationOverlay(
    visible: Boolean,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        LaunchedEffect(visible) {
            if (visible) {
                kotlinx.coroutines.delay(2000)
                onDismiss()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Great Job!",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

private fun getCategoryColor(category: String): Color {
    return when (category) {
        "SPACE" -> AppColors.Purple
        "ANIMALS" -> AppColors.Green
        "NATURE" -> AppColors.Teal
        "SCIENCE" -> AppColors.Blue
        "HUMAN_BODY" -> AppColors.Pink
        else -> AppColors.Orange
    }
}
