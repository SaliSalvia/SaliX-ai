package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Violet = Color(0xFF7C3AED)
val VioletDeep = Color(0xFF4C1D95)
val VioletLight = Color(0xFFC4B5FD)
val LogoOrange = Color(0xFFFF8A00)
val LogoGold = Color(0xFFFFC400)
val LightGray = Color(0xFFF4F4F5)
val SoftGray = Color(0xFFE5E7EB)
val Graphite = Color(0xFF27272A)
val SkyBlue = Violet
val SkyBlueLight = VioletLight
val SkyBlueGlow = Color(0x337C3AED)
val IceBlue = LightGray
val IceCyan = Color(0xFFF5F3FF)
val LightPurple = VioletLight
val LightPurpleContainer = Color(0xFFF3E8FF)
val FrostedGlassWhite = Color(0xEFFFFFFF)
val FrostedGlassSurface = Color(0xDDF8F7FC)
val FrostedGlassContainer = Color(0xEAF6F2FF)
val PureWhite = Color(0xFFFFFFFF)
val DarkGray = Graphite
val MediumGray = Color(0xFF52525B)
val BorderLight = SoftGray
val GlassBorder = Color(0x99FFFFFF)
val GlassBorderPurple = Color(0x667C3AED)
val UserBubbleColor = Violet
val BotBubbleColor = LightPurpleContainer

val IcyBackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF8F7FC),
        Color(0xFFF3E8FF),
        Color(0xFFFFF7ED),
        PureWhite
    )
)

val GlassBorderBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xEEFFFFFF),
        Color(0x667C3AED),
        Color(0x66FF8A00),
        Color(0x99FFFFFF)
    )
)

val UserBubbleGradient = Brush.linearGradient(
    colors = listOf(VioletDeep, Violet, LogoOrange)
)

val BotBubbleGradient = Brush.linearGradient(
    colors = listOf(Color(0xF7F3E8FF), Color(0xFDF7F7FF), Color(0xFFFFFBEB))
)
