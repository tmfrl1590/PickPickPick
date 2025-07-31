package com.tmfrl.pickpickpick.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tmfrl.pickpickpick.design.AppColors
import com.tmfrl.pickpickpick.design.ThemeColors
import com.tmfrl.pickpickpick.design.GameScreenHeader
import com.tmfrl.pickpickpick.design.InfoCard
import com.tmfrl.pickpickpick.design.Language
import com.tmfrl.pickpickpick.design.strings
import com.tmfrl.pickpickpick.platform.rememberAppVersionProvider

/**
 * 설정 화면
 * 테마 선택 및 앱 정보를 표시합니다.
 */
@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    isVibrationEnabled: Boolean = true,
    onVibrationChanged: (Boolean) -> Unit = {},
    currentLanguage: Language = Language.KOREAN,
    onLanguageChanged: (Language) -> Unit = {}
) {
    val backgroundColor = ThemeColors.background(isDarkTheme)
    val textColor = ThemeColors.textPrimary(isDarkTheme)
    val scrollState = rememberScrollState()
    val strings = strings()

    // 플랫폼별 앱 버전 정보 가져오기
    val appVersionProvider = rememberAppVersionProvider()
    val versionName = remember { appVersionProvider.getVersionName() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 공통 헤더 사용
        GameScreenHeader(
            title = strings.settings,
            isDarkTheme = isDarkTheme,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 테마 설정 섹션 - InfoCard 사용
        InfoCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            isDarkTheme = isDarkTheme
        ) {
            Text(
                text = strings.themeSettings,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 라이트 테마 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = !isDarkTheme,
                        onClick = { onThemeChanged(false) }
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = !isDarkTheme,
                    onClick = { onThemeChanged(false) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.Primary,
                        unselectedColor = ThemeColors.textSecondary(isDarkTheme)
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = strings.lightTheme,
                    fontSize = 16.sp,
                    color = textColor
                )
            }

            // 다크 테마 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = isDarkTheme,
                        onClick = { onThemeChanged(true) }
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isDarkTheme,
                    onClick = { onThemeChanged(true) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.Primary,
                        unselectedColor = ThemeColors.textSecondary(isDarkTheme)
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = strings.darkTheme,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
        }

        // 언어 설정 섹션 - InfoCard 사용
        InfoCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            isDarkTheme = isDarkTheme
        ) {
            Text(
                text = strings.languageSettings,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 한국어 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = currentLanguage == Language.KOREAN,
                        onClick = { onLanguageChanged(Language.KOREAN) }
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentLanguage == Language.KOREAN,
                    onClick = { onLanguageChanged(Language.KOREAN) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.Primary,
                        unselectedColor = ThemeColors.textSecondary(isDarkTheme)
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = Language.KOREAN.displayName,
                    fontSize = 16.sp,
                    color = textColor
                )
            }

            // 영어 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = currentLanguage == Language.ENGLISH,
                        onClick = { onLanguageChanged(Language.ENGLISH) }
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentLanguage == Language.ENGLISH,
                    onClick = { onLanguageChanged(Language.ENGLISH) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.Primary,
                        unselectedColor = ThemeColors.textSecondary(isDarkTheme)
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = Language.ENGLISH.displayName,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
        }

        // 진동 설정 섹션 - InfoCard 사용
        InfoCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            isDarkTheme = isDarkTheme
        ) {
            Text(
                text = strings.vibrationSettings,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = strings.gameResultVibration,
                    fontSize = 16.sp,
                    color = textColor
                )
                Switch(
                    checked = isVibrationEnabled,
                    onCheckedChange = { onVibrationChanged(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppColors.Primary,
                        uncheckedThumbColor = ThemeColors.textSecondary(isDarkTheme),
                        checkedTrackColor = AppColors.Primary.copy(alpha = 0.5f),
                        uncheckedTrackColor = ThemeColors.textSecondary(isDarkTheme)
                            .copy(alpha = 0.5f)
                    )
                )
            }
        }

        // 앱 정보 섹션 - InfoCard 사용
        InfoCard(
            modifier = Modifier.fillMaxWidth(),
            isDarkTheme = isDarkTheme
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = strings.appInfo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 앱 로고
                Text(
                    text = "🎲",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 앱 이름
                Text(
                    text = SettingsStrings.APP_NAME,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 버전 정보
                Text(
                    text = "${strings.appVersion} $versionName",
                    fontSize = 16.sp,
                    color = ThemeColors.textSecondary(isDarkTheme),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 앱 설명
                Text(
                    text = strings.appDescription,
                    fontSize = 14.sp,
                    color = ThemeColors.textSecondary(isDarkTheme),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 개발자 정보
                Divider(
                    color = ThemeColors.divider(isDarkTheme),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = strings.developer,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = ThemeColors.textSecondary(isDarkTheme)
                )

                Text(
                    text = SettingsStrings.DEVELOPER_NAME,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = SettingsStrings.COPYRIGHT,
                    fontSize = 12.sp,
                    color = if (isDarkTheme) Color(0xFF94A3B8) else Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}