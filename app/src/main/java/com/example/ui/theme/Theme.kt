package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

// Brutalist Monochrome Theme - Disables dynamic color and forces specific palette regardless of system theme.
private val DropZeroColorScheme = lightColorScheme(
    primary = CtaPrimaryBg,
    onPrimary = CtaPrimaryText,
    secondary = TextSubtle,
    onSecondary = BgPrimary,
    tertiary = BadgeDiscountAccent,
    onTertiary = TextInverse,
    tertiaryContainer = BadgePromoBg,
    onTertiaryContainer = BadgePromoText,
    background = BgPrimary,
    onBackground = TextPrimary,
    surface = BgCard,
    onSurface = TextBody,
    surfaceVariant = BgSecondary,
    onSurfaceVariant = TextPrimary,
    outline = BorderCard
)

val DropZeroShapes = Shapes(
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp),
    extraSmall = RoundedCornerShape(0.dp)
)

@Composable
fun NoirTheme(
    darkTheme: Boolean = false, // Dynamic color disabled implicitly since we only provide the static Light Scheme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DropZeroColorScheme,
        typography = Typography,
        shapes = DropZeroShapes,
        content = content
    )
}
