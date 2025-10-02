package com.tmfrl.pickpickpick.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 공통 UI 컴포넌트들
 */

/**
 * 게임 화면 공통 헤더
 */
@Composable
fun GameScreenHeader(
    title: String,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = ThemeColors.textPrimary(isDarkTheme),
            textAlign = TextAlign.Center
        )

        subtitle?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                fontSize = 16.sp,
                color = ThemeColors.textSecondary(isDarkTheme),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 공통 게임 버튼
 */
@Composable
fun GameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonType: GameButtonType = GameButtonType.PRIMARY,
    loading: Boolean = false,
    isDarkTheme: Boolean = false
) {
    val textColor = if (isDarkTheme) Color.Black else Color.White

    val colors = when (buttonType) {
        GameButtonType.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = AppColors.Primary,
            contentColor = textColor,
            disabledContainerColor = AppColors.ButtonDisabled,
            disabledContentColor = textColor
        )

        GameButtonType.SUCCESS -> ButtonDefaults.buttonColors(
            containerColor = AppColors.ButtonSuccess,
            contentColor = textColor,
            disabledContainerColor = AppColors.ButtonDisabled,
            disabledContentColor = textColor
        )

        GameButtonType.WARNING -> ButtonDefaults.buttonColors(
            containerColor = AppColors.ButtonWarning,
            contentColor = textColor,
            disabledContainerColor = AppColors.ButtonDisabled,
            disabledContentColor = textColor
        )

        GameButtonType.DANGER -> ButtonDefaults.buttonColors(
            containerColor = AppColors.ButtonDanger,
            contentColor = textColor,
            disabledContainerColor = AppColors.ButtonDisabled,
            disabledContentColor = textColor
        )

        GameButtonType.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = AppColors.ButtonSecondary,
            contentColor = textColor,
            disabledContainerColor = AppColors.ButtonDisabled,
            disabledContentColor = textColor
        )
    }

    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        colors = colors,
        modifier = modifier
            .height(50.dp)
            .defaultMinSize(minWidth = 120.dp),
        shape = RoundedCornerShape(25.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        )
    ) {
        if (loading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = textColor,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

/**
 * 공통 정보 카드
 */
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = ThemeColors.surface(isDarkTheme)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

/**
 * 게임 결과 카드
 */
@Composable
fun GameResultCard(
    result: String,
    resultType: GameResultType,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    details: String? = null
) {
    val backgroundColor = when (resultType) {
        GameResultType.WIN -> ThemeColors.winBackground(isDarkTheme)
        GameResultType.LOSE -> ThemeColors.loseBackground(isDarkTheme)
        GameResultType.DRAW -> ThemeColors.drawBackground(isDarkTheme)
    }

    val textColor = when (resultType) {
        GameResultType.WIN -> AppColors.Success
        GameResultType.LOSE -> AppColors.Error
        GameResultType.DRAW -> AppColors.Warning
    }

    val icon = when (resultType) {
        GameResultType.WIN -> "🎉"
        GameResultType.LOSE -> "😔"
        GameResultType.DRAW -> "🤝"
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = result,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center
            )

            details?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = ThemeColors.textSecondary(isDarkTheme),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * 설정 헤더 (뒤로가기 버튼 포함)
 */
@Composable
fun SettingsHeader(
    title: String,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "뒤로가기",
                tint = AppColors.Primary
            )
        }

        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = ThemeColors.textPrimary(isDarkTheme),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(48.dp))
    }
}

/**
 * 버튼 타입 열거형
 */
enum class GameButtonType {
    PRIMARY,
    SUCCESS,
    WARNING,
    DANGER,
    SECONDARY
}

/**
 * 게임 결과 타입 열거형
 */
enum class GameResultType {
    WIN,
    LOSE,
    DRAW
}