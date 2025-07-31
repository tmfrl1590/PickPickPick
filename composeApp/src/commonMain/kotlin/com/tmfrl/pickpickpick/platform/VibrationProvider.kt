package com.tmfrl.pickpickpick.platform

import androidx.compose.runtime.Composable

/**
 * 플랫폼별 진동 기능 제공 인터페이스
 */
interface VibrationProvider {
    /**
     * 짧은 진동을 실행합니다.
     */
    fun vibrateShort()

    /**
     * 긴 진동을 실행합니다.
     */
    fun vibrateLong()
}

/**
 * 플랫폼별 진동 제공자를 가져오는 Composable 함수
 */
@Composable
expect fun rememberVibrationProvider(): VibrationProvider