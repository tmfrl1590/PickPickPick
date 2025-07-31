package com.tmfrl.pickpickpick.platform

import androidx.compose.runtime.Composable

/**
 * 플랫폼별 설정 저장/불러오기를 위한 인터페이스
 */
interface PreferencesProvider {
    /**
     * 다크 테마 설정을 저장합니다
     */
    fun saveDarkTheme(isDark: Boolean)

    /**
     * 저장된 다크 테마 설정을 불러옵니다
     */
    fun getDarkTheme(): Boolean

    /**
     * Boolean 값을 저장합니다
     */
    fun putBoolean(key: String, value: Boolean)

    /**
     * Boolean 값을 불러옵니다
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
}

/**
 * 현재 플랫폼의 설정 제공자를 반환하는 expect 함수
 */
expect fun getPreferencesProvider(): PreferencesProvider

/**
 * Composable에서 사용할 수 있는 설정 제공자
 */
@Composable
expect fun rememberPreferencesProvider(): PreferencesProvider