package com.tmfrl.pickpickpick.platform

import androidx.compose.runtime.*

/**
 * 플랫폼별 앱 버전 정보를 제공하는 인터페이스
 */
interface AppVersionProvider {
    /**
     * 앱 버전 이름 반환 (예: "1.0.0")
     */
    fun getVersionName(): String

    /**
     * 앱 버전 코드 반환 (예: 1)
     */
    fun getVersionCode(): String
}

/**
 * 현재 플랫폼의 앱 버전 정보를 가져오는 expect 함수
 */
expect fun getAppVersionProvider(): AppVersionProvider

/**
 * 플랫폼별 Composable에서 앱 버전 정보를 가져오는 expect 함수
 */
@Composable
expect fun rememberAppVersionProvider(): AppVersionProvider