package com.tmfrl.pickpickpick.platform

import androidx.compose.runtime.*
import platform.Foundation.NSBundle

/**
 * iOS 플랫폼의 앱 버전 정보 제공자
 */
class IOSAppVersionProvider : AppVersionProvider {
    override fun getVersionName(): String {
        return try {
            val bundle = NSBundle.mainBundle
            bundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "1.0.0"
        } catch (e: Exception) {
            "1.0.0"
        }
    }

    override fun getVersionCode(): String {
        return try {
            val bundle = NSBundle.mainBundle
            bundle.objectForInfoDictionaryKey("CFBundleVersion") as? String ?: "1"
        } catch (e: Exception) {
            "1"
        }
    }
}

/**
 * iOS에서 앱 버전 정보 제공자를 반환하는 actual 함수
 */
actual fun getAppVersionProvider(): AppVersionProvider {
    return IOSAppVersionProvider()
}

/**
 * iOS Composable에서 앱 버전 정보를 가져오는 actual 함수
 */
@Composable
actual fun rememberAppVersionProvider(): AppVersionProvider {
    return remember { IOSAppVersionProvider() }
}