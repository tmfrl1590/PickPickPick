package com.tmfrl.pickpickpick.presentation.games.rockpaperscissors

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import com.tmfrl.pickpickpick.design.GameButton
import com.tmfrl.pickpickpick.design.GameButtonType
import com.tmfrl.pickpickpick.design.GameResultCard
import com.tmfrl.pickpickpick.design.GameResultType
import com.tmfrl.pickpickpick.design.GameScreenHeader
import com.tmfrl.pickpickpick.design.InfoCard
import com.tmfrl.pickpickpick.design.ThemeColors
import com.tmfrl.pickpickpick.platform.rememberVibrationProvider
import com.tmfrl.pickpickpick.design.strings
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 가위바위보 게임 화면
 * 사용자와 컴퓨터가 가위바위보를 하는 게임입니다.
 */
@Composable
fun RockPaperScissorsScreen(isDarkTheme: Boolean = false, isVibrationEnabled: Boolean = true) {
    var userChoice by remember { mutableStateOf<GameChoice?>(null) }
    var computerChoice by remember { mutableStateOf<GameChoice?>(null) }
    var gameResult by remember { mutableStateOf<GameResult?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var userScore by remember { mutableStateOf(0) }
    var computerScore by remember { mutableStateOf(0) }
    var countdownText by remember { mutableStateOf("") }
    var showCountdown by remember { mutableStateOf(false) }

    val backgroundColor = ThemeColors.background(isDarkTheme)
    val textColor = ThemeColors.textPrimary(isDarkTheme)
    val cardColor = ThemeColors.surface(isDarkTheme)
    val secondaryTextColor = ThemeColors.textSecondary(isDarkTheme)
    val strings = strings()

    // 카운트다운 텍스트 스케일 애니메이션
    val countdownScale by animateFloatAsState(
        targetValue = if (showCountdown) 1f else 0.5f,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "countdown_scale"
    )

    val countdownAlpha by animateFloatAsState(
        targetValue = if (showCountdown) 1f else 0f,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "countdown_alpha"
    )

    // 진동 제공자
    val vibrationProvider = rememberVibrationProvider()

    // 이전 게임 결과를 추적하여 결과가 변경되었을 때 진동
    var previousGameResult by remember { mutableStateOf<GameResult?>(null) }

    // 게임 결과가 변경되었을 때 진동 실행
    LaunchedEffect(gameResult) {
        if (gameResult != null &&
            gameResult != previousGameResult &&
            isVibrationEnabled
        ) {
            vibrationProvider.vibrateShort()
        }
        previousGameResult = gameResult
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameScreenHeader(
                title = strings.rockPaperScissorsTitle,
                subtitle = strings.rockPaperScissorsSubtitle,
                isDarkTheme = isDarkTheme,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoCard(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    isDarkTheme = isDarkTheme
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = strings.user,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColors.PlayerColor
                        )
                        Text(
                            text = userScore.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = ThemeColors.textPrimary(isDarkTheme)
                        )
                    }
                }

                InfoCard(
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    isDarkTheme = isDarkTheme
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = strings.computer,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColors.ComputerColor
                        )
                        Text(
                            text = computerScore.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = ThemeColors.textPrimary(isDarkTheme)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            InfoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                isDarkTheme = isDarkTheme
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = strings.user,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = secondaryTextColor
                            )
                            Text(
                                text = userChoice?.emoji ?: "?",
                                fontSize = 48.sp,
                                color = textColor,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Text(
                            text = strings.vs,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = secondaryTextColor
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = strings.computer,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = secondaryTextColor
                            )
                            if (isPlaying) {
                                Text(
                                    text = countdownText,
                                    fontSize = 48.sp,
                                    color = textColor,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            } else {
                                Text(
                                    text = computerChoice?.emoji ?: "?",
                                    fontSize = 48.sp,
                                    color = textColor,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            gameResult?.let { result ->
                val resultType = when (result) {
                    GameResult.WIN -> GameResultType.WIN
                    GameResult.LOSE -> GameResultType.LOSE
                    GameResult.DRAW -> GameResultType.DRAW
                }
                val resultText = when (result) {
                    GameResult.WIN -> strings.win
                    GameResult.LOSE -> strings.lose
                    GameResult.DRAW -> strings.draw
                }

                GameResultCard(
                    result = resultText,
                    resultType = resultType,
                    isDarkTheme = isDarkTheme,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Text(
                text = strings.chooseText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GameChoice.values().forEach { choice ->
                    ChoiceButton(
                        choice = choice,
                        enabled = !isPlaying,
                        onClick = {
                            if (!isPlaying) {
                                userChoice = choice
                                isPlaying = true
                            }
                        }
                    )
                }
            }

            LaunchedEffect(userChoice, isPlaying) {
                if (isPlaying && userChoice != null) {
                    val countdownSequence = listOf("✌️", "✊", "✋")

                    for (i in countdownSequence.indices) {
                        countdownText = countdownSequence[i]
                        showCountdown = true
                        delay(500)
                        showCountdown = false
                        delay(100)
                    }

                    val computerSelection = GameChoice.values().random()
                    computerChoice = computerSelection

                    val result = calculateResult(userChoice!!, computerSelection)
                    gameResult = result

                    when (result) {
                        GameResult.WIN -> userScore++
                        GameResult.LOSE -> computerScore++
                        GameResult.DRAW -> { /* 점수 변화 없음 */
                        }
                    }

                    isPlaying = false
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            GameButton(
                text = strings.resetScore,
                onClick = {
                    userChoice = null
                    computerChoice = null
                    gameResult = null
                    userScore = 0
                    computerScore = 0
                },
                buttonType = GameButtonType.SECONDARY,
                modifier = Modifier.width(140.dp)
            )
        }

        if (showCountdown && countdownText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ThemeColors.overlay(isDarkTheme)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = countdownText,
                    fontSize = 200.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = countdownScale
                            scaleY = countdownScale
                            alpha = countdownAlpha
                        }
                )
            }
        }
    }
}

enum class GameChoice(val emoji: String, val displayNameKey: String) {
    ROCK("✊", "rock"),
    PAPER("✋", "paper"),
    SCISSORS("✌️", "scissors");

    @Composable
    fun getDisplayName(): String {
        val strings = strings()
        return when (displayNameKey) {
            "rock" -> strings.rock
            "paper" -> strings.paper
            "scissors" -> strings.scissors
            else -> displayNameKey
        }
    }
}

enum class GameResult {
    WIN, LOSE, DRAW
}

private fun calculateResult(userChoice: GameChoice, computerChoice: GameChoice): GameResult {
    return when {
        userChoice == computerChoice -> GameResult.DRAW
        (userChoice == GameChoice.ROCK && computerChoice == GameChoice.SCISSORS) ||
                (userChoice == GameChoice.PAPER && computerChoice == GameChoice.ROCK) ||
                (userChoice == GameChoice.SCISSORS && computerChoice == GameChoice.PAPER) -> GameResult.WIN

        else -> GameResult.LOSE
    }
}

@Composable
private fun ChoiceButton(
    choice: GameChoice,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.ButtonSuccess,
            contentColor = Color.White,
            disabledContainerColor = AppColors.ButtonDisabled,
            disabledContentColor = ThemeColors.textSecondary(false)
        ),
        modifier = Modifier
            .size(width = 90.dp, height = 80.dp)
            .padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = choice.emoji,
                fontSize = 24.sp
            )
            Text(
                text = choice.getDisplayName(),
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}