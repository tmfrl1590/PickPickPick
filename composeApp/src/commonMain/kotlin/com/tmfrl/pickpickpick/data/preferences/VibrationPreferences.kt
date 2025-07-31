package com.tmfrl.pickpickpick.data.preferences

import androidx.compose.runtime.*
import com.tmfrl.pickpickpick.platform.rememberPreferencesProvider

private const val VIBRATION_ENABLED_KEY = "vibration_enabled"

/**
 * 진동 설정 상태를 저장하고 불러오는 Composable 함수
 */
@Composable
fun rememberVibrationState(): MutableState<Boolean> {
    val preferencesProvider = rememberPreferencesProvider()

    // 초기값 로드 (기본값은 true)
    val initialValue = preferencesProvider.getBoolean(VIBRATION_ENABLED_KEY, true)
    val vibrationState: MutableState<Boolean> = remember { mutableStateOf(initialValue) }

    // 상태가 변경될 때마다 저장
    LaunchedEffect(vibrationState.value) {
        preferencesProvider.putBoolean(VIBRATION_ENABLED_KEY, vibrationState.value)
    }

    return vibrationState
}