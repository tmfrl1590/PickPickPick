package com.tmfrl.pickpickpick.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.tmfrl.pickpickpick.ContextFactory
import com.tmfrl.pickpickpick.design.AppColors
import com.tmfrl.pickpickpick.design.Language
import com.tmfrl.pickpickpick.design.LanguageProvider
import com.tmfrl.pickpickpick.design.ThemeColors
import com.tmfrl.pickpickpick.design.strings
import com.tmfrl.pickpickpick.presentation.games.dice.DiceGameScreen
import com.tmfrl.pickpickpick.presentation.games.lottery.LotteryGameScreen
import com.tmfrl.pickpickpick.presentation.games.rockpaperscissors.RockPaperScissorsScreen
import com.tmfrl.pickpickpick.presentation.games.spinningwheel.SpinningWheelGameScreen
import com.tmfrl.pickpickpick.presentation.settings.SettingsScreen

/**
 * 메인 화면 - Bottom Navigation 포함
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    platformContext: ContextFactory,
    isDarkTheme: Boolean = false,
    onThemeChanged: (Boolean) -> Unit = {},
    isVibrationEnabled: Boolean = true,
    onVibrationChanged: (Boolean) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var currentLanguageCode by remember { mutableStateOf(Language.KOREAN.code) }
    val currentLanguage = Language.values().first { it.code == currentLanguageCode }

    LanguageProvider(language = currentLanguage) {
        val navBarColor = ThemeColors.navigationBar(isDarkTheme)
        val indicatorColor = ThemeColors.navigationIndicator(isDarkTheme)
        val strings = strings()

        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = navBarColor
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = {
                            Text(
                                text = MainStrings.EMOJI_SPINNING_WHEEL,
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(strings.tabSpinningWheel)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AppColors.Primary,
                            selectedTextColor = AppColors.Primary,
                            unselectedIconColor = ThemeColors.textSecondary(isDarkTheme),
                            unselectedTextColor = ThemeColors.textSecondary(isDarkTheme),
                            indicatorColor = indicatorColor
                        )
                    )

                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = {
                            Text(
                                text = MainStrings.EMOJI_DICE,
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(strings.tabDice)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AppColors.Primary,
                            selectedTextColor = AppColors.Primary,
                            unselectedIconColor = ThemeColors.textSecondary(isDarkTheme),
                            unselectedTextColor = ThemeColors.textSecondary(isDarkTheme),
                            indicatorColor = indicatorColor
                        )
                    )

                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = {
                            Text(
                                text = MainStrings.EMOJI_ROCK_PAPER_SCISSORS,
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(strings.tabRockPaperScissors)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AppColors.Primary,
                            selectedTextColor = AppColors.Primary,
                            unselectedIconColor = ThemeColors.textSecondary(isDarkTheme),
                            unselectedTextColor = ThemeColors.textSecondary(isDarkTheme),
                            indicatorColor = indicatorColor
                        )
                    )


                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        icon = {
                            Text(
                                text = MainStrings.EMOJI_LOTTERY,
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(strings.tabMemoryLottery)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AppColors.Primary,
                            selectedTextColor = AppColors.Primary,
                            unselectedIconColor = ThemeColors.textSecondary(isDarkTheme),
                            unselectedTextColor = ThemeColors.textSecondary(isDarkTheme),
                            indicatorColor = indicatorColor
                        )
                    )

                    NavigationBarItem(
                        selected = selectedTab == 4,
                        onClick = { selectedTab = 4 },
                        icon = {
                            Text(
                                text = MainStrings.EMOJI_SETTINGS,
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(strings.tabSettings)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AppColors.Primary,
                            selectedTextColor = AppColors.Primary,
                            unselectedIconColor = ThemeColors.textSecondary(isDarkTheme),
                            unselectedTextColor = ThemeColors.textSecondary(isDarkTheme),
                            indicatorColor = indicatorColor
                        )
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedTab) {
                    0 -> SpinningWheelGameScreen(
                        platformContext = platformContext,
                        isDarkTheme = isDarkTheme,
                        isVibrationEnabled = isVibrationEnabled
                    )

                    1 -> DiceGameScreen(
                        isDarkTheme = isDarkTheme,
                        isVibrationEnabled = isVibrationEnabled
                    )

                    2 -> RockPaperScissorsScreen(
                        isDarkTheme = isDarkTheme,
                        isVibrationEnabled = isVibrationEnabled
                    )

                    3 -> LotteryGameScreen(
                        platformContext = platformContext,
                        isDarkTheme = isDarkTheme,
                        isVibrationEnabled = isVibrationEnabled
                    )

                    4 -> SettingsScreen(
                        isDarkTheme = isDarkTheme,
                        onThemeChanged = { newTheme ->
                            onThemeChanged(newTheme)
                        },
                        isVibrationEnabled = isVibrationEnabled,
                        onVibrationChanged = { newVibration ->
                            onVibrationChanged(newVibration)
                        },
                        currentLanguage = currentLanguage,
                        onLanguageChanged = { newLanguage ->
                            currentLanguageCode = newLanguage.code
                        }
                    )
                }
            }
        }
    }
}