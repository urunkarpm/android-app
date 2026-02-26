package com.funcalc.app.presentation.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funcalc.app.domain.model.Achievement
import com.funcalc.app.presentation.theme.AppColors
import com.funcalc.app.presentation.theme.AppTheme
import com.funcalc.app.presentation.theme.Themes

/**
 * Settings bottom sheet content
 */
@Composable
fun SettingsContent(
    currentTheme: AppTheme,
    soundEnabled: Boolean,
    totalCalculations: Int,
    achievements: List<Achievement>,
    onThemeChange: (String) -> Unit,
    onSoundToggle: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Title
        Text(
            text = "Settings",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = currentTheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sound effects toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Sound Effects",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Play sounds when buttons are pressed",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = soundEnabled,
                onCheckedChange = onSoundToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = currentTheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Theme selector
        Text(
            text = "Theme",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Themes.all) { theme ->
                ThemeChip(
                    theme = theme,
                    isSelected = theme.name == currentTheme.name,
                    onClick = { onThemeChange(theme.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = currentTheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total Calculations: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$totalCalculations",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = currentTheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Achievements section
        Text(
            text = "Achievements",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            achievements.forEach { achievement ->
                AchievementItem(
                    achievement = achievement,
                    currentTheme = currentTheme
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * Theme selection chip
 */
@Composable
private fun ThemeChip(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(theme.primary)
                .then(
                    if (isSelected) {
                        Modifier.border(3.dp, theme.secondary, CircleShape)
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = theme.name.first().uppercase(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = theme.name.replaceFirstChar { it.uppercase() },
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) theme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Achievement item display
 */
@Composable
private fun AchievementItem(
    achievement: Achievement,
    currentTheme: AppTheme
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (achievement.isUnlocked) {
                    currentTheme.primary.copy(alpha = 0.1f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Badge icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    if (achievement.isUnlocked) {
                        currentTheme.secondary
                    } else {
                        Color.Gray.copy(alpha = 0.5f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = getAchievementIcon(achievement.iconName),
                contentDescription = achievement.badgeName,
                tint = if (achievement.isUnlocked) Color.White else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = achievement.badgeName,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (achievement.isUnlocked) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    Color.Gray
                }
            )
            Text(
                text = achievement.description,
                fontSize = 12.sp,
                color = if (achievement.isUnlocked) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    Color.Gray.copy(alpha = 0.7f)
                }
            )
        }

        // Unlocked indicator
        if (achievement.isUnlocked) {
            Text(
                text = "✓",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = currentTheme.secondary
            )
        }
    }
}

private fun getAchievementIcon(iconName: String): ImageVector {
    return when (iconName) {
        "star" -> Icons.Default.Star
        "explore" -> Icons.Default.Explore
        "auto_awesome" -> Icons.Default.AutoAwesome
        "emoji_events" -> Icons.Default.EmojiEvents
        "military_tech" -> Icons.Default.MilitaryTech
        else -> Icons.Default.Star
    }
}
