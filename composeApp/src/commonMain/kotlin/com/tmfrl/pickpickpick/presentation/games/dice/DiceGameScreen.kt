package com.tmfrl.pickpickpick.presentation.games.dice

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tmfrl.pickpickpick.design.AppColors
import com.tmfrl.pickpickpick.design.GameButtonType
import com.tmfrl.pickpickpick.design.GameResultType
import com.tmfrl.pickpickpick.design.GameScreenHeader
import com.tmfrl.pickpickpick.design.GameButton
import com.tmfrl.pickpickpick.design.GameResultCard
import com.tmfrl.pickpickpick.design.ThemeColors
import com.tmfrl.pickpickpick.platform.rememberVibrationProvider
import com.tmfrl.pickpickpick.design.strings
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * 실제 주사위처럼 점으로 표현하는 주사위 뷰
 */
@Composable
private fun DiceView(
    diceNumber: Int,
    modifier: Modifier = Modifier,
    animating: Boolean = false,
    isDarkTheme: Boolean = false
) {
    val diceBackgroundColor = ThemeColors.diceBackground(isDarkTheme)
    val dotColor = ThemeColors.diceDot(isDarkTheme)
    val borderColor = ThemeColors.diceBorder(isDarkTheme)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(
                elevation = if (animating) 16.dp else 8.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(diceBackgroundColor, RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val center = size.width / 2
            val third = size.width / 3
            val dotRadius = size.width * 0.08f

            fun drawDot(x: Float, y: Float) {
                drawCircle(
                    color = dotColor,
                    radius = dotRadius,
                    center = Offset(x, y)
                )
            }

            when (diceNumber) {
                1 -> {
                    // 중앙에 하나
                    drawDot(center, center)
                }

                2 -> {
                    // 대각선 두 개
                    drawDot(third * 0.7f, third * 0.7f)
                    drawDot(third * 2.3f, third * 2.3f)
                }

                3 -> {
                    // 대각선 세 개
                    drawDot(third * 0.7f, third * 0.7f)
                    drawDot(center, center)
                    drawDot(third * 2.3f, third * 2.3f)
                }

                4 -> {
                    // 네 모서리
                    drawDot(third * 0.7f, third * 0.7f)
                    drawDot(third * 2.3f, third * 0.7f)
                    drawDot(third * 0.7f, third * 2.3f)
                    drawDot(third * 2.3f, third * 2.3f)
                }

                5 -> {
                    // 네 모서리 + 중앙
                    drawDot(third * 0.7f, third * 0.7f)
                    drawDot(third * 2.3f, third * 0.7f)
                    drawDot(center, center)
                    drawDot(third * 0.7f, third * 2.3f)
                    drawDot(third * 2.3f, third * 2.3f)
                }

                6 -> {
                    // 양쪽에 세 개씩
                    drawDot(third * 0.7f, third * 0.7f)
                    drawDot(third * 0.7f, center)
                    drawDot(third * 0.7f, third * 2.3f)
                    drawDot(third * 2.3f, third * 0.7f)
                    drawDot(third * 2.3f, center)
                    drawDot(third * 2.3f, third * 2.3f)
                }
            }
        }
    }
}

/**
 * 주사위 던지기 게임 화면
 * 버튼을 누르면 1~6 사이 숫자 랜덤으로 애니메이션과 함께 출력됩니다.
 */
@Composable
fun DiceGameScreen(
    isDarkTheme: Boolean = false,
    isVibrationEnabled: Boolean = true
) {
    var diceNumber by remember { mutableStateOf(1) } // 현재 주사위 값
    var animating by remember { mutableStateOf(false) } // 애니메이션 상태
    var hasThrown by remember { mutableStateOf(false) } // 주사위를 던진 적이 있는지 추적
    val coroutineScope = rememberCoroutineScope()

    // 진동 제공자
    val vibrationProvider = rememberVibrationProvider()

    // 이전 주사위 값을 추적하여 결과가 변경되었을 때 진동
    var previousDiceNumber by remember { mutableStateOf(1) }

    // 주사위 값이 변경되었을 때 진동 실행 (애니메이션이 끝났을 때만)
    LaunchedEffect(diceNumber, animating) {
        if (!animating && diceNumber != previousDiceNumber && isVibrationEnabled && hasThrown) {
            vibrationProvider.vibrateShort()
        }
        if (!animating) {
            previousDiceNumber = diceNumber
        }
    }

    val backgroundColor = ThemeColors.background(isDarkTheme)

    // 애니메이션 값들
    val bounceOffset by animateFloatAsState(
        targetValue = if (animating) -50f else 0f,
        animationSpec = if (animating) {
            repeatable(
                iterations = 3,
                animation = tween(400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        } else {
            tween(100)
        },
        label = "bounce"
    )

    val rotationAngleZ by animateFloatAsState(
        targetValue = if (animating) 720f else 0f,
        animationSpec = if (animating) {
            tween(2000, easing = LinearOutSlowInEasing)
        } else {
            tween(0)
        },
        label = "rotationZ"
    )

    // 추가 3D 회전을 위한 각도 애니메이션
    val rotationAngleX by animateFloatAsState(
        targetValue = if (animating) 1080f else 0f,
        animationSpec = if (animating) {
            tween(2000, easing = LinearOutSlowInEasing)
        } else {
            tween(0)
        },
        label = "rotationX"
    )

    val rotationAngleY by animateFloatAsState(
        targetValue = if (animating) 900f else 0f,
        animationSpec = if (animating) {
            tween(2000, easing = LinearOutSlowInEasing)
        } else {
            tween(0)
        },
        label = "rotationY"
    )

    val scaleValue by animateFloatAsState(
        targetValue = if (animating) 1.2f else 1f,
        animationSpec = if (animating) {
            repeatable(
                iterations = 2,
                animation = tween(600, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        } else {
            tween(200)
        },
        label = "scale"
    )

    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameScreenHeader(
                title = strings().diceGame,
                subtitle = strings().diceSubtitle,
                isDarkTheme = isDarkTheme,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .offset(y = bounceOffset.dp),
                contentAlignment = Alignment.Center
            ) {
                DiceView(
                    diceNumber = if (animating) Random.nextInt(1, 7) else diceNumber,
                    animating = animating,
                    isDarkTheme = isDarkTheme,
                    modifier = Modifier
                        .size(160.dp)
                        .scale(scaleValue)
                        .graphicsLayer {
                            rotationX = rotationAngleX
                            rotationY = rotationAngleY
                            rotationZ = rotationAngleZ
                            cameraDistance =
                                12 * density.density * 160f
                        }
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // 주사위를 던진 적이 있고 애니메이션이 끝났을 때만 결과 표시
            if (!animating && hasThrown) {
                GameResultCard(
                    result = "${strings().result}: $diceNumber",
                    resultType = GameResultType.WIN,
                    isDarkTheme = isDarkTheme,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }

            GameButton(
                text = if (animating) strings().throwing else strings().throwDice,
                onClick = {
                    if (!animating) {
                        animating = true
                        hasThrown = true // 주사위를 던졌음을 표시
                        coroutineScope.launch {
                            kotlinx.coroutines.delay(2000)
                            diceNumber = Random.nextInt(1, 7)
                            animating = false
                            // Confetti 효과 제거
                        }
                    }
                },
                enabled = !animating,
                buttonType = GameButtonType.SUCCESS,
                loading = animating,
                modifier = Modifier.width(160.dp)
            )
        }
    }
}