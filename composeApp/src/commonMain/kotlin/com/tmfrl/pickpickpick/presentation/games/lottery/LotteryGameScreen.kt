package com.tmfrl.pickpickpick.presentation.games.lottery

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tmfrl.pickpickpick.design.AppColors
import com.tmfrl.pickpickpick.design.ThemeColors
import com.tmfrl.pickpickpick.design.GameScreenHeader
import com.tmfrl.pickpickpick.design.GameButton
import com.tmfrl.pickpickpick.design.GameButtonType
import com.tmfrl.pickpickpick.design.InfoCard
import com.tmfrl.pickpickpick.design.strings
import com.tmfrl.pickpickpick.platform.rememberVibrationProvider
import androidx.compose.runtime.LaunchedEffect
import com.tmfrl.pickpickpick.design.LocalizedStrings
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.DependsOnGoogleUserMessagingPlatform
import app.lexilabs.basic.ads.composable.ConsentPopup
import app.lexilabs.basic.ads.composable.InterstitialAd
import app.lexilabs.basic.ads.composable.rememberConsent
import app.lexilabs.basic.ads.composable.rememberInterstitialAd
import app.lexilabs.basic.logging.Log
import com.tmfrl.pickpickpick.ContextFactory
import kotlin.random.Random

/**
 * 뽑기 게임 화면
 */
@OptIn(DependsOnGoogleUserMessagingPlatform::class, DependsOnGoogleMobileAds::class)
@Composable
fun LotteryGameScreen(
    platformContext: ContextFactory,
    isDarkTheme: Boolean = false,
    isVibrationEnabled: Boolean = true
) {
    val consent by rememberConsent(activity = platformContext.getActivity())
    val interstitialAd by rememberInterstitialAd(activity = platformContext.getActivity())
    var showInterstitialAd by remember { mutableStateOf(false) }

    val viewModel = remember { LotteryViewModel() }
    val vibrationProvider = rememberVibrationProvider()

    var showSettings by remember { mutableStateOf(false) }
    val strings = strings()

    // ViewModel 상태들
    val lotteries by viewModel.lotteries
    val isGameStarted by viewModel.isGameStarted

    // 진동 효과 - 뽑기가 열렸을 때
    var lastOpenedCount by remember { mutableIntStateOf(0) }
    LaunchedEffect(lotteries) {
        val currentOpenedCount = lotteries.count { it.isOpened }
        if (currentOpenedCount > lastOpenedCount && isVibrationEnabled) {
            vibrationProvider.vibrateShort()
        }
        lastOpenedCount = currentOpenedCount
    }

    // Try to show a consent popup
    ConsentPopup(
        consent = consent,
        onFailure = { Log.e("App", "failure:${it.message}") }
    )

    // 광고 배너
    if (showInterstitialAd && consent.canRequestAds) {
        InterstitialAd(
            interstitialAd,
            onDismissed = {
                showInterstitialAd = false
                // 광고가 닫힌 후 설정 화면으로 이동
                showSettings = true
            }
        )
    }

    val backgroundColor = ThemeColors.background(isDarkTheme)

    if (showSettings) {
        LotterySettingsScreen(
            viewModel = viewModel,
            onDismiss = { showSettings = false },
            isDarkTheme = isDarkTheme
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 공통 헤더 사용
            GameScreenHeader(
                title = strings.lotteryTitle,
                isDarkTheme = isDarkTheme,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 상품 목록 표시 - InfoCard 사용
            InfoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                isDarkTheme = isDarkTheme
            ) {
                PrizeRow(
                    strings.firstPlace,
                    viewModel.firstPrizeName.value,
                    viewModel.firstPrizeCount.value,
                    isDarkTheme,
                    strings
                )
                Spacer(modifier = Modifier.height(4.dp))
                PrizeRow(
                    strings.secondPlace,
                    viewModel.secondPrizeName.value,
                    viewModel.secondPrizeCount.value,
                    isDarkTheme,
                    strings
                )
                Spacer(modifier = Modifier.height(4.dp))
                PrizeRow(
                    strings.thirdPlace,
                    viewModel.thirdPrizeName.value,
                    viewModel.thirdPrizeCount.value,
                    isDarkTheme,
                    strings
                )
            }

            // 설정 버튼 - 공통 버튼 사용
            GameButton(
                text = strings.lotterySettings,
                onClick = {
                    // 설정 버튼 클릭 시 50% 확률로 광고 표시
                    val shouldShowAd = Random.nextFloat() < 0.5f // 50% 확률

                    if (shouldShowAd && interstitialAd.state == AdState.READY && consent.canRequestAds) {
                        showInterstitialAd = true
                    } else {
                        // 광고를 표시하지 않거나, 광고가 준비되지 않은 경우 바로 설정 화면으로 이동
                        showSettings = true
                    }
                },
                buttonType = GameButtonType.SECONDARY,
                enabled = !isGameStarted,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (!isGameStarted) {
                // 게임 시작 전
                Spacer(modifier = Modifier.weight(1f))

                GameButton(
                    text = strings.lotteryStart,
                    onClick = { viewModel.startGame() },
                    buttonType = GameButtonType.SUCCESS,
                    modifier = Modifier.width(200.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            } else {
                Text(
                    text = strings.pickLottery,
                    fontSize = 18.sp,
                    color = ThemeColors.textPrimary(isDarkTheme),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "${strings.remainingLottery}: ${viewModel.getRemainingCount()}${strings.countSuffix}",
                    fontSize = 14.sp,
                    color = ThemeColors.textSecondary(isDarkTheme),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 뽑기 그리드
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color(0xFF0D47A1), RoundedCornerShape(8.dp)) // 진한 파란색 배경
                        .padding(4.dp)
                ) {
                    val columns = when (lotteries.size) {
                        in 1..12 -> 4
                        in 13..20 -> 5
                        else -> 6
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(lotteries) { lottery ->
                            LotteryCard(
                                lottery = lottery,
                                onClick = { viewModel.selectLottery(lottery.id) },
                                isDarkTheme = isDarkTheme,
                                totalLotteryCount = lotteries.size // 총 뽑기 개수 전달
                            )
                        }
                    }
                }

                // 리셋 버튼 - 공통 버튼 사용
                GameButton(
                    text = strings.resetButton,
                    onClick = { viewModel.resetGame() },
                    buttonType = GameButtonType.DANGER,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

/**
 * 뽑기 카드 컴포넌트
 */
@Composable
fun LotteryCard(
    lottery: LotteryItem,
    onClick: () -> Unit,
    isDarkTheme: Boolean,
    totalLotteryCount: Int = 20 // 총 뽑기 개수를 매개변수로 추가
) {
    // 애니메이션 상태
    val scale by animateFloatAsState(
        targetValue = if (lottery.isOpened) 1.1f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    // 뽑기 개수에 따른 텍스트 크기 조정
    val textSize = when {
        totalLotteryCount <= 15 -> 14.sp  // 적은 개수: 큰 텍스트
        totalLotteryCount <= 20 -> 12.sp  // 중간 개수: 보통 텍스트
        totalLotteryCount <= 25 -> 11.sp  // 많은 개수: 작은 텍스트
        else -> 10.sp                     // 매우 많은 개수: 최소 텍스트 (하지만 최소 10sp 보장)
    }

    val backgroundColor = if (lottery.isOpened) {
        when (lottery.prizeType) {
            PrizeType.FIRST -> Color(0xFFFFD700) // 금색
            PrizeType.SECOND -> Color(0xFFC0C0C0) // 은색
            PrizeType.THIRD -> Color(0xFFCD7F32) // 동색
            PrizeType.NO_PRIZE -> Color.White
        }
    } else {
        Color(0xFFFFEB3B) // 노란색
    }

    val textColor = Color.Black // 텍스트는 모두 검은색으로

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .scale(scale)
            .clickable(enabled = !lottery.isOpened) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (lottery.isOpened) 4.dp else 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (lottery.isOpened) {
                // 열린 뽑기 - 결과 표시
                Text(
                    text = when (lottery.prizeType) {
                        PrizeType.FIRST -> "🥇"
                        PrizeType.SECOND -> "🥈"
                        PrizeType.THIRD -> "🥉"
                        PrizeType.NO_PRIZE -> "❌"
                    },
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = lottery.prizeName,
                    fontSize = textSize, // 동적으로 조정된 텍스트 크기 사용
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            } else {
                // 닫힌 뽑기 - 별 모양
                Text(
                    text = "⭐",
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
private fun PrizeRow(
    rank: String,
    prize: String,
    count: Int,
    isDarkTheme: Boolean,
    strings: LocalizedStrings
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$rank:",
            fontWeight = FontWeight.Bold,
            color = ThemeColors.textPrimary(isDarkTheme),
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "$prize ($count${strings.countSuffix})",
            color = ThemeColors.textSecondary(isDarkTheme)
        )
    }
}