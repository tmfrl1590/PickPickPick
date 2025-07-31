package com.tmfrl.pickpickpick.design

import androidx.compose.ui.graphics.Color

/**
 * 앱 전체에서 사용하는 색상 정의
 */
object AppColors {

    // Primary Colors
    val Primary = Color(0xFF3B82F6)
    val PrimaryDark = Color(0xFF2563EB)
    val PrimaryLight = Color(0xFF60A5FA)

    // Background Colors
    val BackgroundLight = Color.White
    val BackgroundDark = Color(0xFF1E293B)

    // Surface Colors
    val SurfaceLight = Color(0xFFF8FAFC)
    val SurfaceDark = Color(0xFF334155)

    // Text Colors
    val TextPrimaryLight = Color(0xFF1E293B)
    val TextPrimaryDark = Color.White
    val TextSecondaryLight = Color(0xFF64748B)
    val TextSecondaryDark = Color(0xFFCBD5E1)
    val TextTertiaryLight = Color(0xFF94A3B8)
    val TextTertiaryDark = Color(0xFF94A3B8)

    // Navigation Colors
    val NavigationBarLight = Color(0xFFF8FAFC)
    val NavigationBarDark = Color(0xFF334155)
    val NavigationIndicatorLight = Color(0xFFE2E8F0)
    val NavigationIndicatorDark = Color(0xFF475569)

    // Button Colors
    val ButtonSuccess = Color(0xFF10B981)
    val ButtonWarning = Color(0xFFF59E0B)
    val ButtonDanger = Color(0xFFEF4444)
    val ButtonSecondary = Color(0xFF64748B)
    val ButtonDisabled = Color(0xFFE5E7EB)

    // Game Specific Colors
    val WheelColors = listOf(
        Color(0xFFEF4444), // Red
        Color(0xFF3B82F6), // Blue
        Color(0xFF10B981), // Green
        Color(0xFFF59E0B), // Yellow
        Color(0xFF8B5CF6), // Purple
        Color(0xFFEC4899), // Pink
        Color(0xFF06B6D4), // Cyan
        Color(0xFF84CC16)  // Lime
    )

    val WheelBorderLight = Color.White
    val WheelBorderDark = Color(0xFF454545)
    val WheelTextLight = Color(0xFF1E293B)
    val WheelTextDark = Color(0xFFC7C5B8)

    val ArrowColor = Color.Red
    val ArrowColorDark = Color(0xFF10B981)
    val ArrowBorderLight = Color(0xFF1E293B)
    val ArrowBorderDark = Color(0xFFC7C5B8)

    // Dice Colors
    val DiceBackgroundLight = Color.White
    val DiceBackgroundDark = Color(0xFFF8F9FA)
    val DiceDotLight = Color(0xFF374151)
    val DiceDotDark = Color(0xFF1F2937)
    val DiceBorderLight = Color(0xFF9CA3AF)
    val DiceBorderDark = Color(0xFFD1D5DB)

    // Rock Paper Scissors Colors
    val PlayerColor = Color(0xFF3B82F6)
    val ComputerColor = Color(0xFFEF4444)
    val WinColor = Color(0xFF10B981)
    val LoseColor = Color(0xFFEF4444)
    val DrawColor = Color(0xFFF59E0B)

    // Settings Colors
    val DividerLight = Color(0xFFE2E8F0)
    val DividerDark = Color(0xFF475569)

    // Status Colors
    val Success = Color(0xFF10B981)
    val Warning = Color(0xFFF59E0B)
    val Error = Color(0xFFEF4444)
    val Info = Color(0xFF3B82F6)

    // Common UI Colors
    val CardElevationLight = Color(0x1A000000)
    val CardElevationDark = Color(0x1AFFFFFF)
    val OverlayLight = Color(0x80000000)
    val OverlayDark = Color(0x80000000)

    // Game Result Colors
    val WinBackground = Color(0xFFF0FDF4)
    val WinBackgroundDark = Color(0xFF0F1B0F)
    val LoseBackground = Color(0xFFFEF2F2)
    val LoseBackgroundDark = Color(0xFF1F1515)
    val DrawBackground = Color(0xFFFEFCE8)
    val DrawBackgroundDark = Color(0xFF1F1E15)
}

/**
 * 테마에 따른 색상을 반환하는 헬퍼 함수들
 */
object ThemeColors {

    fun background(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.BackgroundDark else AppColors.BackgroundLight

    fun surface(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.SurfaceDark else AppColors.SurfaceLight

    fun textPrimary(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.TextPrimaryDark else AppColors.TextPrimaryLight

    fun textSecondary(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.TextSecondaryDark else AppColors.TextSecondaryLight

    fun textTertiary(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.TextTertiaryDark else AppColors.TextTertiaryLight

    fun navigationBar(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.NavigationBarDark else AppColors.NavigationBarLight

    fun navigationIndicator(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.NavigationIndicatorDark else AppColors.NavigationIndicatorLight

    fun wheelBorder(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.WheelBorderDark else AppColors.WheelBorderLight

    fun wheelText(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.WheelTextDark else AppColors.WheelTextLight

    fun arrowColor(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.ArrowColorDark else AppColors.ArrowColor

    fun arrowBorder(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.ArrowBorderDark else AppColors.ArrowBorderLight

    fun diceBackground(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.DiceBackgroundDark else AppColors.DiceBackgroundLight

    fun diceDot(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.DiceDotDark else AppColors.DiceDotLight

    fun diceBorder(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.DiceBorderDark else AppColors.DiceBorderLight

    fun divider(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.DividerDark else AppColors.DividerLight

    fun cardElevation(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.CardElevationDark else AppColors.CardElevationLight

    fun overlay(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.OverlayDark else AppColors.OverlayLight

    fun winBackground(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.WinBackgroundDark else AppColors.WinBackground

    fun loseBackground(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.LoseBackgroundDark else AppColors.LoseBackground

    fun drawBackground(isDarkTheme: Boolean) =
        if (isDarkTheme) AppColors.DrawBackgroundDark else AppColors.DrawBackground
}