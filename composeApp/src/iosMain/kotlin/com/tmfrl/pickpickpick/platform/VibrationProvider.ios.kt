package com.tmfrl.pickpickpick.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AudioToolbox.AudioServicesPlaySystemSound
import platform.AudioToolbox.kSystemSoundID_Vibrate

/**
 * iOS 플랫폼용 진동 제공자 구현
 */
class IOSVibrationProvider : VibrationProvider {

    @OptIn(ExperimentalForeignApi::class)
    override fun vibrateShort() {
        try {
            AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
        } catch (e: Exception) {
            // 진동 실행 오류 시 무시
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun vibrateLong() {
        try {
            // iOS에서는 시스템 진동을 사용하므로 길이 조절이 어렵습니다.
            // 짧은 진동과 동일하게 처리합니다.
            AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
        } catch (e: Exception) {
            // 진동 실행 오류 시 무시
        }
    }
}

@Composable
actual fun rememberVibrationProvider(): VibrationProvider {
    return remember { IOSVibrationProvider() }
}