package com.tmfrl.pickpickpick.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tmfrl.pickpickpick.design.AppColors
import com.tmfrl.pickpickpick.design.ThemeColors

/**
 * 스플래시 화면
 * 앱 로고와 제목을 2초간 표시합니다.
 */
@Composable
fun SplashScreen(isDarkTheme: Boolean = false) {
    // 로고 회전 애니메이션
    val infiniteTransition = rememberInfiniteTransition(label = "splash_rotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // 텍스트 페이드인 애니메이션
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000, delayMillis = 500),
        label = "fade_in"
    )

    val backgroundColor = ThemeColors.background(isDarkTheme)
    val titleColor = ThemeColors.textPrimary(isDarkTheme)
    val subtitleColor = ThemeColors.textSecondary(isDarkTheme)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 로고 (회전하는 이모지)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        rotationZ = rotationAngle
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎲",
                    fontSize = 80.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 앱 제목
            Text(
                text = SplashStrings.APP_NAME,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor.copy(alpha = alpha),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 부제목
            Text(
                text = "무엇이든 굴리고 돌리고 뽑아보세요—\n결정이 즐거워집니다",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = subtitleColor.copy(alpha = alpha * 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}