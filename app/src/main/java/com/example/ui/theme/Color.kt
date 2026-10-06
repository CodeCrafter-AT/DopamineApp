package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Surface & canvas
val BgPrimary = Color(0xFFFFFFFF)
val BgSecondary = Color(0xFFF8F8F8)
val BgSubtle = Color(0xFFF4F4F4)
val BgDarkTicker = Color(0xFF000000)
val BgHeaderSticky = Color(0xFFFFFFFF)
val BgCard = Color(0xFFFFFFFF)
val BackdropOverlay = Color(0xA6000000) // rgba(0, 0, 0, 0.65)

// Typography
val TextPrimary = Color(0xFF000000)
val TextBody = Color(0xFF111111)
val TextMuted = Color(0xFF666666)
val TextSubtle = Color(0xFF767676) // replaces #8E8E93 / #888888 for AA contrast
val TextInverse = Color(0xFFFFFFFF)
val TextDiscount = Color(0xFFD32F2F)
val TextTabInactive = Color(0xFF757575)

// Interactive & accent
val CtaPrimaryBg = Color(0xFF000000)
val CtaPrimaryText = Color(0xFFFFFFFF)
val CtaPrimaryHover = Color(0xFF1F1F1F)
val CtaSecondaryBorder = Color(0xFF000000)
val TabActiveIndicator = Color(0xFF000000)
val BadgePromoBg = Color(0xFF000000)
val BadgePromoText = Color(0xFFFFFFFF)
val BadgeDiscountAccent = Color(0xFFE53935)

// Structural lines
val BorderHairline = Color(0xFFEEEEEE)
val BorderCard = Color(0xFFE5E5E5)
val BorderChip = Color(0xFFE5E5E5)
val BorderFocus = Color(0xFF000000)

// Legacy compatibility
val PureWhite = BgPrimary
val NoirBlack = TextPrimary
val OffWhite = BgSecondary
val LuxuryCardBg = BgCard
val LuxuryBorder = BorderCard
val LuxuryDarkBorder = BorderFocus
val AccentPurple = BadgeDiscountAccent
val AccentPurpleGlow = BadgeDiscountAccent
val AccentPurpleBg = BgSecondary
val AccentPurpleSoft = BgSubtle
val SuccessGreen = TextPrimary
val ErrorAlert = TextDiscount

