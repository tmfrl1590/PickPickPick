package com.tmfrl.pickpickpick.platform

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Android 플랫폼의 설정 저장소 (SharedPreferences 사용)
 */
class AndroidPreferencesProvider(private val context: Context) : PreferencesProvider {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    override fun saveDarkTheme(isDark: Boolean) {
        preferences.edit()
            .putBoolean("dark_theme", isDark)
            .apply()
    }

    override fun getDarkTheme(): Boolean {
        return preferences.getBoolean("dark_theme", false) // 기본값: 라이트 테마
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferences.edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }
}

// 글로벌 인스턴스 (Application Context 필요)
private var globalPreferencesProvider: AndroidPreferencesProvider? = null

/**
 * Android에서 설정 제공자를 반환하는 actual 함수
 */
actual fun getPreferencesProvider(): PreferencesProvider {
    return globalPreferencesProvider ?: throw IllegalStateException(
        "PreferencesProvider not initialized. Call initializePreferences() first."
    )
}

/**
 * Android에서 PreferencesProvider를 초기화하는 함수
 */
fun initializePreferences(context: Context) {
    globalPreferencesProvider = AndroidPreferencesProvider(context.applicationContext)
}

@Composable
actual fun rememberPreferencesProvider(): PreferencesProvider {
    val context = LocalContext.current
    return remember { AndroidPreferencesProvider(context) }
}