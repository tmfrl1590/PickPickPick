package com.tmfrl.pickpickpick.data.preferences

import androidx.compose.runtime.*
import com.tmfrl.pickpickpick.platform.getPreferencesProvider

/**
 * 테마 설정을 관리하는 클래스
 * 플랫폼별 저장소를 사용하여 테마 설정을 지속적으로 저장합니다.
 */
@Composable
fun rememberThemeState(): MutableState<Boolean> {
    val preferencesProvider = remember { getPreferencesProvider() }

    // 저장된 테마 설정을 초기값으로 사용
    val initialTheme = remember { preferencesProvider.getDarkTheme() }
    val themeState = remember { mutableStateOf(initialTheme) }

    // 테마가 변경될 때마다 저장
    LaunchedEffect(themeState.value) {
        preferencesProvider.saveDarkTheme(themeState.value)
    }

    return themeState
}

/**
 * 테마 관련 상수
 */
object ThemeConstants {
    const val THEME_PREFERENCE_KEY = "app_theme_dark_mode"
}