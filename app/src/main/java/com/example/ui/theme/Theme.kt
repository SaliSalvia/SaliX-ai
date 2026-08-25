package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SalviaColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = PureWhite,
    primaryContainer = LightPurpleContainer,
    onPrimaryContainer = DarkGray,
    secondary = LightPurple,
    onSecondary = DarkGray,
    secondaryContainer = LightPurpleContainer,
    onSecondaryContainer = DarkGray,
    tertiary = SkyBlue,
    onTertiary = PureWhite,
    background = PureWhite,
    onBackground = DarkGray,
    surface = PureWhite,
    onSurface = DarkGray,
    surfaceVariant = LightPurpleContainer,
    onSurfaceVariant = MediumGray,
    outline = LightPurple,
    outlineVariant = BorderLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Strictly Light, bright and airy
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SalviaColorScheme,
        typography = Typography,
        content = content
    )
}

