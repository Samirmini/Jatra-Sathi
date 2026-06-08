package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val EliteColorScheme = darkColorScheme(
    primary = LiquidEmerald,
    onPrimary = VelvetNavy,
    secondary = AmbientBlue,
    onSecondary = CreamSilk,
    background = VelvetNavy,
    onBackground = CreamSilk,
    surface = VelvetNavyLight,
    onSurface = CreamSilk,
    error = AlertGold,
    onError = VelvetNavy
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Always enforce premium brand theme colors
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = EliteColorScheme,
        typography = Typography,
        content = content
    )
}
