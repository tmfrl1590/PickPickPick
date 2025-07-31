package com.tmfrl.pickpickpick

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.tmfrl.pickpickpick.data.preferences.rememberThemeState
import com.tmfrl.pickpickpick.data.preferences.rememberVibrationState
import com.tmfrl.pickpickpick.presentation.main.MainScreen
import com.tmfrl.pickpickpick.presentation.splash.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun App() {
    MaterialTheme {
        // 플랫폼별 저장소에서 테마 상태를 불러오고 관리
        var isDarkTheme by rememberThemeState()
        var isVibrationEnabled by rememberVibrationState()
        var showSplash by remember { mutableStateOf(true) }

        // 2초 후 스플래시 화면 숨기기
        LaunchedEffect(Unit) {
            delay(2000L)
            showSplash = false
        }

        if (showSplash) {
            SplashScreen(isDarkTheme = isDarkTheme)
        } else {
            MainScreen(
                isDarkTheme = isDarkTheme,
                onThemeChanged = { newTheme ->
                    isDarkTheme = newTheme
                },
                isVibrationEnabled = isVibrationEnabled,
                onVibrationChanged = { newVibration ->
                    isVibrationEnabled = newVibration
                }
            )
        }
    }
}