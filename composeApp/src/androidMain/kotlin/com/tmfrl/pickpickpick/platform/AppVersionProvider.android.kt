package com.tmfrl.pickpickpick.platform

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Android 플랫폼의 앱 버전 정보 제공자
 */
class AndroidAppVersionProvider(private val context: Context) : AppVersionProvider {
    override fun getVersionName(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "1.0.0"
        } catch (e: Exception) {
            "1.0.0"
        }
    }

    override fun getVersionCode(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toString()
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toString()
            }
        } catch (e: Exception) {
            "1"
        }
    }
}

/**
 * Android에서 앱 버전 정보 제공자를 반환하는 actual 함수
 */
actual fun getAppVersionProvider(): AppVersionProvider {
    // Android에서는 Context가 필요하므로 임시로 기본값 반환
    // Composable에서 사용할 때는 rememberAppVersionProvider() 사용
    return object : AppVersionProvider {
        override fun getVersionName(): String = "1.0.0"
        override fun getVersionCode(): String = "1"
    }
}

/**
 * Android Composable에서 앱 버전 정보를 가져오는 함수
 */
@Composable
actual fun rememberAppVersionProvider(): AppVersionProvider {
    val context = LocalContext.current
    return remember { AndroidAppVersionProvider(context) }
}