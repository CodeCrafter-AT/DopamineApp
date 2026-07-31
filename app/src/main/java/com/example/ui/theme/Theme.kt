package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val PureWhiteColorScheme = lightColorScheme(
    primary = NoirBlack,
    onPrimary = PureWhite,
    secondary = TextSecondary,
    onSecondary = PureWhite,
    tertiary = AccentPurple,
    onTertiary = PureWhite,
    tertiaryContainer = AccentPurpleBg,
    onTertiaryContainer = AccentPurple,
    background = PureWhite,
    onBackground = TextPrimary,
    surface = PureWhite,
    onSurface = TextPrimary,
    surfaceVariant = LuxuryCardBg,
    onSurfaceVariant = TextPrimary,
    outline = LuxuryBorder
)

@Composable
fun NoirTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PureWhiteColorScheme,
        typography = Typography,
        content = content
    )
}

