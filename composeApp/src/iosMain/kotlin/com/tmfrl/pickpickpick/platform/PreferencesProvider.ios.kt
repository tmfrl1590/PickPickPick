package com.tmfrl.pickpickpick.platform

import platform.Foundation.NSUserDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * iOS 플랫폼의 설정 저장소 (NSUserDefaults 사용)
 */
class IOSPreferencesProvider : PreferencesProvider {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun saveDarkTheme(isDark: Boolean) {
        userDefaults.setBool(isDark, "dark_theme")
        userDefaults.synchronize()
    }

    override fun getDarkTheme(): Boolean {
        return userDefaults.boolForKey("dark_theme") // 기본값: false (라이트 테마)
    }

    override fun putBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, key)
        userDefaults.synchronize()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        // iOS NSUserDefaults는 키가 없으면 false를 반환하므로 defaultValue 처리 필요
        if (userDefaults.objectForKey(key) == null) {
            return defaultValue
        }
        return userDefaults.boolForKey(key)
    }
}

/**
 * iOS에서 설정 제공자를 반환하는 actual 함수
 */
actual fun getPreferencesProvider(): PreferencesProvider {
    return IOSPreferencesProvider()
}

@Composable
actual fun rememberPreferencesProvider(): PreferencesProvider {
    return remember { IOSPreferencesProvider() }
}