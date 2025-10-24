package com.tmfrl.pickpickpick.presentation.games.spinningwheel

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tmfrl.pickpickpick.design.AppColors
import com.tmfrl.pickpickpick.design.GameButton
import com.tmfrl.pickpickpick.design.GameButtonType
import com.tmfrl.pickpickpick.design.GameResultCard
import com.tmfrl.pickpickpick.design.GameResultType
import com.tmfrl.pickpickpick.design.GameScreenHeader
import com.tmfrl.pickpickpick.design.InfoCard
import com.tmfrl.pickpickpick.design.SettingsHeader
import com.tmfrl.pickpickpick.design.ThemeColors
import com.tmfrl.pickpickpick.platform.rememberVibrationProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform
import app.lexilabs.basic.ads.composable.ConsentPopup
import app.lexilabs.basic.ads.composable.InterstitialAd
import app.lexilabs.basic.ads.composable.RewardedAd
import app.lexilabs.basic.ads.composable.rememberConsent
import app.lexilabs.basic.ads.composable.rememberInterstitialAd
import app.lexilabs.basic.ads.composable.rememberRewardedAd
import app.lexilabs.basic.logging.Log
import com.tmfrl.pickpickpick.ContextFactory
import kotlin.random.Random
import com.tmfrl.pickpickpick.design.strings

/**
 * 돌림판 게임 화면
 * 시작 버튼을 누르면 돌림판이 회전하고 선택지를 선택합니다.
 */
@OptIn(DependsOnGoogleUserMessagingPlatform::class, DependsOnGoogleMobileAds::class)
@Composable
fun SpinningWheelGameScreen(
    platformContext: ContextFactory,
    isDarkTheme: Boolean = false,
    isVibrationEnabled: Boolean = true
) {
    val consent by rememberConsent(activity = platformContext.getActivity())
    val interstitialAd by rememberInterstitialAd(
        activity = platformContext.getActivity(),
        adUnitId = "ca-app-pub-3991873148102758/3446947769"
    )
    var showInterstitialAd by remember { mutableStateOf(false) }
    // ViewModel 인스턴스
    val viewModel = remember { SpinningWheelViewModel() }

    // 진동 제공자
    val vibrationProvider = rememberVibrationProvider()

    // ViewModel에서 상태 가져오기
    val participants by viewModel.participants
    val selectedParticipant by viewModel.selectedParticipant
    val isSpinning by viewModel.isSpinning

    var showParticipantEditor by remember { mutableStateOf(false) }

    // 이전 선택된 참가자를 추적하여 결과가 변경되었을 때 진동
    var previousSelectedParticipant by remember { mutableStateOf<String?>(null) }

    // 선택된 참가자가 변경되었을 때 진동 실행
    LaunchedEffect(selectedParticipant) {
        if (selectedParticipant != null &&
            selectedParticipant != previousSelectedParticipant &&
            isVibrationEnabled
        ) {
            vibrationProvider.vibrateShort()
        }
        previousSelectedParticipant = selectedParticipant
    }

    // Try to show a consent popup
    ConsentPopup(
        consent = consent,
        onFailure = { Log.e("App", "failure:${it.message}")}
    )

    // 광고 배너
    if (showInterstitialAd && consent.canRequestAds) {
        InterstitialAd(
            interstitialAd,
            onDismissed = {
                showInterstitialAd = false
                // 광고가 닫힌 후 편집 화면으로 이동
                showParticipantEditor = true
            }
        )
    }

    // 회전 각도 애니메이션
    val infiniteTransition = rememberInfiniteTransition(label = "spin")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // 최종 회전 각도 (감속 애니메이션용)
    var finalRotation by remember { mutableFloatStateOf(0f) }
    val finalRotationAnimated by animateFloatAsState(
        targetValue = finalRotation,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        finishedListener = { finalAngle ->
            if (isSpinning) {
                viewModel.selectParticipantByAngle(finalAngle)
            }
        },
        label = "finalRotation"
    )

    // 텍스트 측정용
    val textMeasurer = rememberTextMeasurer()

    val backgroundColor = ThemeColors.background(isDarkTheme)
    val textColor = ThemeColors.textPrimary(isDarkTheme)

    val strings = strings()

    if (showParticipantEditor) {
        ParticipantEditorScreen(
            viewModel = viewModel,
            onDismiss = { showParticipantEditor = false },
            isDarkTheme = isDarkTheme
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameScreenHeader(
                title = strings.spinningWheelTitle,
                isDarkTheme = isDarkTheme,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            GameButton(
                text = "${strings.editOptions} (${participants.size}${strings.optionCount})",
                onClick = {
                    // 편집 버튼 클릭 시 50% 확률로 광고 표시
                    val shouldShowAd = Random.nextFloat() < 0.5f // 50% 확률

                    if (shouldShowAd && interstitialAd.state == AdState.READY && consent.canRequestAds) {
                        showInterstitialAd = true
                    } else {
                        // 광고를 표시하지 않거나, 광고가 준비되지 않은 경우 바로 편집 화면으로 이동
                        showParticipantEditor = true
                    }
                },
                buttonType = GameButtonType.SECONDARY,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 돌림판과 화살표
            Box(
                modifier = Modifier.size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                // 돌림판
                Canvas(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(CircleShape)
                ) {
                    val currentRotation = if (isSpinning) {
                        if (finalRotation == 0f) rotationAngle else finalRotationAnimated
                    } else {
                        finalRotationAnimated
                    }

                    rotate(currentRotation) {
                        drawWheel(participants, textMeasurer, isDarkTheme, -currentRotation)
                    }
                }

                // 상단 화살표 (바늘)
                Canvas(
                    modifier = Modifier
                        .size(40.dp)
                        .offset(y = (-120).dp)
                ) {
                    drawArrow(isDarkTheme)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // 시작 버튼
            GameButton(
                text = if (isSpinning) strings.spinningInProgress else strings.startButton,
                onClick = {
                    if (!isSpinning) {
                        viewModel.startGame()
                        // 랜덤한 최종 회전 각도 설정 (최소 5바퀴 + 랜덤)
                        finalRotation += 360f * (5 + Random.nextFloat() * 3)
                    }
                },
                enabled = !isSpinning,
                buttonType = GameButtonType.WARNING,
                loading = isSpinning,
                modifier = Modifier
                    .padding(16.dp)
                    .width(160.dp)
            )

            selectedParticipant?.let { participant ->
                GameResultCard(
                    result = "${strings.selectedItem}: $participant",
                    resultType = GameResultType.WIN,
                    isDarkTheme = isDarkTheme,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}

/**
 * 선택지 편집 화면
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantEditorScreen(
    viewModel: SpinningWheelViewModel,
    onDismiss: () -> Unit,
    isDarkTheme: Boolean
) {
    // ViewModel에서 직접 상태 가져오기
    val participants by viewModel.participants
    var newParticipantName by remember { mutableStateOf("") }

    val backgroundColor = ThemeColors.background(isDarkTheme)
    val textColor = ThemeColors.textPrimary(isDarkTheme)
    val cardColor = ThemeColors.surface(isDarkTheme)

    val strings = strings()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        SettingsHeader(
            title = strings.participantsEditTitle,
            onBackClick = onDismiss,
            isDarkTheme = isDarkTheme,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "최소 2개, 최대 8개까지 입력 가능합니다. (현재 ${participants.size}개)",
            fontSize = 14.sp,
            color = ThemeColors.textSecondary(isDarkTheme),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 선택지 추가 입력 - InfoCard 사용
        if (participants.size < 8) {
            InfoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                isDarkTheme = isDarkTheme
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newParticipantName,
                        onValueChange = { newParticipantName = it },
                        label = { Text(strings.newParticipantName) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedLabelColor = AppColors.Primary,
                            unfocusedLabelColor = ThemeColors.textSecondary(isDarkTheme),
                            focusedBorderColor = AppColors.Primary,
                            unfocusedBorderColor = ThemeColors.textSecondary(isDarkTheme),
                            cursorColor = AppColors.Primary
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    GameButton(
                        text = strings.addButton,
                        onClick = {
                            viewModel.addParticipant(newParticipantName.trim())
                            newParticipantName = ""
                        },
                        enabled = newParticipantName.isNotBlank() && participants.size < 8,
                        buttonType = GameButtonType.SUCCESS,
                        modifier = Modifier.width(80.dp)
                    )
                }
            }
        }

        // 선택지 목록
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(participants) { index, participant ->
                InfoCard(
                    modifier = Modifier.fillMaxWidth(),
                    isDarkTheme = isDarkTheme
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = textColor,
                            modifier = Modifier.width(32.dp)
                        )
                        Text(
                            text = participant,
                            fontSize = 16.sp,
                            color = textColor,
                            modifier = Modifier.weight(1f)
                        )

                        GameButton(
                            text = strings.deleteButton,
                            onClick = {
                                viewModel.removeParticipant(index)
                            },
                            enabled = participants.size > 2,
                            buttonType = GameButtonType.DANGER,
                            modifier = Modifier.width(80.dp)
                        )
                    }
                }
            }
        }

        // 안내 메시지
        if (participants.size <= 2) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = strings.minimumParticipantsWarning,
                fontSize = 14.sp,
                color = AppColors.ButtonDanger,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// 돌림판 그리기 (텍스트 회전 보정 추가)
fun DrawScope.drawWheel(
    participants: List<String>,
    textMeasurer: TextMeasurer,
    isDarkTheme: Boolean,
    counterRotation: Float = 0f
) {
    val radius = size.minDimension / 2
    val sectionAngle = 360f / participants.size
    val center = Offset(size.width / 2, size.height / 2)

    // 색상 팔레트
    val colors = AppColors.WheelColors

    participants.forEachIndexed { index, participant ->
        val startAngle = index * sectionAngle - 90f // -90도로 12시 방향 시작
        val color = colors[index % colors.size]

        // 섹션 그리기
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sectionAngle,
            useCenter = true,
            topLeft = Offset(0f, 0f),
            size = size
        )

        // 경계선 그리기
        drawArc(
            color = ThemeColors.wheelBorder(isDarkTheme),
            startAngle = startAngle,
            sweepAngle = sectionAngle,
            useCenter = true,
            topLeft = Offset(0f, 0f),
            size = size,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
        )

        // 텍스트 위치 계산
        val textAngle = startAngle + sectionAngle / 2
        val textRadius = radius * 0.7f
        val textX =
            center.x + textRadius * kotlin.math.cos(kotlin.math.PI * textAngle / 180.0).toFloat()
        val textY =
            center.y + textRadius * kotlin.math.sin(kotlin.math.PI * textAngle / 180.0).toFloat()

        // 선택지 이름 그리기 (회전 보정 적용)
        val textLayoutResult = textMeasurer.measure(
            text = participant,
            style = TextStyle(
                color = ThemeColors.wheelText(isDarkTheme),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        // 텍스트를 항상 수평으로 유지하기 위해 반대 회전 적용
        rotate(counterRotation, pivot = Offset(textX, textY)) {
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    textX - textLayoutResult.size.width / 2,
                    textY - textLayoutResult.size.height / 2
                )
            )
        }
    }
}

// 화살표(바늘) 그리기
fun DrawScope.drawArrow(isDarkTheme: Boolean) {
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(size.width / 2, 0f)
        lineTo(size.width / 2 - 20f, size.height)
        lineTo(size.width / 2 + 20f, size.height)
        close()
    }

    drawPath(
        path = path,
        color = ThemeColors.arrowColor(isDarkTheme),
        style = androidx.compose.ui.graphics.drawscope.Fill
    )

    // 화살표 테두리
    drawPath(
        path = path,
        color = ThemeColors.arrowBorder(isDarkTheme),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
    )
}