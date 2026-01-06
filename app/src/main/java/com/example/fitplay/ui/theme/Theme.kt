package com.example.fitplay.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueDark,
    onPrimaryContainer = Color.White,
    secondary = AccentAmber,
    onSecondary = Color.Black,
    secondaryContainer = AccentAmber.copy(alpha = 0.24f),
    onSecondaryContainer = Color(0xFF211101),
    tertiary = AccentMint,
    onTertiary = Color.Black,
    tertiaryContainer = AccentMint.copy(alpha = 0.2f),
    onTertiaryContainer = Color(0xFF0B3C21),
    background = SurfaceSoft,
    surface = SurfaceSoft,
    surfaceVariant = SurfaceMuted,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = OutlineLight
)

private val DarkColors = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueDark,
    onPrimaryContainer = Color.White,
    secondary = AccentAmber,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF4B2E05),
    onSecondaryContainer = Color(0xFFFFEAD5),
    tertiary = AccentMint,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF0F3D26),
    onTertiaryContainer = Color(0xFFB6F6D0),
    background = Color(0xFF0F172A),
    surface = Color(0xFF111827),
    surfaceVariant = Color(0xFF1F2937),
    onSurface = Color(0xFFE5E7EB),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF334155)
)

@Composable
fun FitPlayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
