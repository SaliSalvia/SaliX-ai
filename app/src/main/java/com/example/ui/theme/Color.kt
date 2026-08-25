package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val SkyBlue = Color(0xFF4A90E2)
val SkyBlueLight = Color(0xFF68A5EC)
val SkyBlueGlow = Color(0x334A90E2)
val IceBlue = Color(0xFFE8F3FF)
val IceCyan = Color(0xFFD8EEFE)
val LightPurple = Color(0xFFB39DDB)
val LightPurpleContainer = Color(0xFFF3E8FF)
val FrostedGlassWhite = Color(0xE6FFFFFF)
val FrostedGlassSurface = Color(0xCCF7FAFC)
val FrostedGlassContainer = Color(0xDDF3E8FF)
val PureWhite = Color(0xFFFFFFFF)
val DarkGray = Color(0xFF1A1A2E)
val MediumGray = Color(0xFF4A4A4A)
val BorderLight = Color(0xFFE2E8F0)
val GlassBorder = Color(0x80FFFFFF)
val GlassBorderPurple = Color(0x66B39DDB)
val UserBubbleColor = Color(0xFF4A90E2)
val BotBubbleColor = Color(0xFFF3E8FF)

// Glass & Ice Gradients
val IcyBackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF4F9FF),
        Color(0xFFF8F5FE),
        Color(0xFFFFFFFF)
    )
)

val GlassBorderBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xCCFFFFFF),
        Color(0x4D4A90E2),
        Color(0x66B39DDB),
        Color(0x99FFFFFF)
    )
)

val UserBubbleGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF4A90E2),
        Color(0xFF5CA2EE)
    )
)

val BotBubbleGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xF2F3E8FF),
        Color(0xE6FFFFFF)
    )
)

