package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable

private val SalviaColorScheme = lightColorScheme(
    primary = Violet,
    onPrimary = PureWhite,
    primaryContainer = LightPurpleContainer,
    onPrimaryContainer = VioletDeep,
    secondary = LogoOrange,
    onSecondary = Graphite,
    secondaryContainer = Color(0xFFFFEDD5),
    onSecondaryContainer = Graphite,
    tertiary = LogoGold,
    onTertiary = Graphite,
    background = PureWhite,
    onBackground = Graphite,
    surface = PureWhite,
    onSurface = Graphite,
    surfaceVariant = LightGray,
    onSurfaceVariant = MediumGray,
    outline = VioletLight,
    outlineVariant = SoftGray
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SalviaColorScheme,
        typography = Typography,
        content = content
    )
}
