package com.tmfrl.pickpickpick.design

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/**
 * 간단한 Confetti 애니메이션 (외부 라이브러리 없이 구현)
 * @param visible true 일 때 2초간 Confetti 가 떨어졌다 사라짐
 */
@Composable
fun ConfettiOverlay(
    visible: Boolean,
    isDarkTheme: Boolean
) {
    if (!visible) return

    // 파티클 데이터 클래스 정의
    data class Particle(
        val startX: Float,
        val color: Color,
        val size: Float,
        val velocityY: Float,
        val animY: Animatable<Float, *> = Animatable(0f)
    )

    val particles = remember {
        List(80) {
            val colors = AppColors.WheelColors
            Particle(
                startX = Random.nextFloat(), // 0f~1f, 나중에 화면 폭과 곱셈
                color = colors[Random.nextInt(colors.size)],
                size = Random.nextDouble(4.0, 8.0).toFloat(),
                velocityY = Random.nextDouble(300.0, 700.0).toFloat()
            )
        }
    }

    // 애니메이션 실행
    LaunchedEffect(visible) {
        if (visible) {
            particles.forEach { particle ->
                // y축 0 -> 1 로 1500ms 동안 이동 후 멈춤
                particle.animY.snapTo(0f)
                particle.animY.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1500)
                )
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        particles.forEach { particle ->
            val yPos = particle.animY.value * canvasHeight
            drawCircle(
                color = particle.color,
                radius = particle.size.dp.toPx(),
                center = Offset(particle.startX * canvasWidth, yPos)
            )
        }
    }
}