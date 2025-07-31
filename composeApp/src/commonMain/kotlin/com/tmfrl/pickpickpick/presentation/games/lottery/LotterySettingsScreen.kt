package com.tmfrl.pickpickpick.presentation.games.lottery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tmfrl.pickpickpick.design.AppColors
import com.tmfrl.pickpickpick.design.LocalizedStrings
import com.tmfrl.pickpickpick.design.SettingsHeader
import com.tmfrl.pickpickpick.design.ThemeColors
import com.tmfrl.pickpickpick.design.strings
import kotlinx.coroutines.launch

/**
 * 뽑기 설정 화면
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotterySettingsScreen(
    viewModel: LotteryViewModel,
    onDismiss: () -> Unit,
    isDarkTheme: Boolean
) {
    // 현재 설정값들을 로컬 상태로 관리
    var totalCount by remember { mutableStateOf(viewModel.totalCount.value.toString()) }
    var firstPrizeCount by remember { mutableStateOf(viewModel.firstPrizeCount.value.toString()) }
    var secondPrizeCount by remember { mutableStateOf(viewModel.secondPrizeCount.value.toString()) }
    var thirdPrizeCount by remember { mutableStateOf(viewModel.thirdPrizeCount.value.toString()) }
    var firstPrizeName by remember { mutableStateOf(viewModel.firstPrizeName.value) }
    var secondPrizeName by remember { mutableStateOf(viewModel.secondPrizeName.value) }
    var thirdPrizeName by remember { mutableStateOf(viewModel.thirdPrizeName.value) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val strings = strings()

    // 숫자 입력 정리 함수
    fun cleanNumberInput(input: String): String {
        val cleaned = input.replace(Regex("[^0-9]"), "") // 숫자만 허용
        return if (cleaned.isEmpty() || cleaned == "0") {
            cleaned
        } else {
            // 앞의 0 제거
            cleaned.replace(Regex("^0+"), "").ifEmpty { "0" }
        }
    }

    // 개수 유효성 검사 및 자동 조정 함수
    fun validateAndAdjustCounts(
        changedField: String,
        newValue: String,
        currentTotal: String,
        currentFirst: String,
        currentSecond: String,
        currentThird: String
    ): Triple<String, String, String> {
        // 입력값 정리
        val total = (currentTotal.toIntOrNull() ?: 20).coerceIn(10, 30)
        val first = if (changedField == "first") cleanNumberInput(newValue) else cleanNumberInput(
            currentFirst
        )
        val second =
            if (changedField == "second") cleanNumberInput(newValue) else cleanNumberInput(
                currentSecond
            )
        val third = if (changedField == "third") cleanNumberInput(newValue) else cleanNumberInput(
            currentThird
        )

        var adjustedFirst = first
        var adjustedSecond = second
        var adjustedThird = third

        // 1등이 전체보다 크거나 같으면 전체 개수로 제한하고 나머지는 0
        if (adjustedFirst.toIntOrNull() ?: 0 >= total) {
            adjustedFirst = total.toString()
            adjustedSecond = "0"
            adjustedThird = "0"
        } else {
            // 1등 + 2등이 전체보다 크거나 같으면 2등을 조정하고 3등은 0
            if ((adjustedFirst.toIntOrNull() ?: 0) + (adjustedSecond.toIntOrNull()
                    ?: 0) >= total
            ) {
                adjustedSecond =
                    (total - (adjustedFirst.toIntOrNull() ?: 0)).toString()
                adjustedThird = "0"
            } else {
                // 1등 + 2등 + 3등이 전체보다 크면 3등을 조정
                if ((adjustedFirst.toIntOrNull() ?: 0) + (adjustedSecond.toIntOrNull()
                        ?: 0) + (adjustedThird.toIntOrNull() ?: 0) > total
                ) {
                    adjustedThird = (total - (adjustedFirst.toIntOrNull()
                        ?: 0) - (adjustedSecond.toIntOrNull() ?: 0)).toString()
                }
            }
        }

        // 음수 방지
        adjustedFirst = maxOf(0, adjustedFirst.toIntOrNull() ?: 0).toString()
        adjustedSecond = maxOf(0, adjustedSecond.toIntOrNull() ?: 0).toString()
        adjustedThird = maxOf(0, adjustedThird.toIntOrNull() ?: 0).toString()

        val cleanInput = newValue

        // Preserve empty input for the field currently being edited
        val resultFirst = if (changedField == "first" && cleanInput.isEmpty()) "" else adjustedFirst
        val resultSecond =
            if (changedField == "second" && cleanInput.isEmpty()) "" else adjustedSecond
        val resultThird = if (changedField == "third" && cleanInput.isEmpty()) "" else adjustedThird

        return Triple(
            resultFirst,
            resultSecond,
            resultThird
        )
    }

    val backgroundColor = ThemeColors.background(isDarkTheme)
    val textColor = ThemeColors.textPrimary(isDarkTheme)
    val cardColor = ThemeColors.surface(isDarkTheme)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // 공통 설정 헤더 사용
            SettingsHeader(
                title = strings.lotterySettingsTitle,
                onBackClick = onDismiss,
                isDarkTheme = isDarkTheme,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 전체 제비 개수 설정
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = strings.totalLotteryCount,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = totalCount,
                        onValueChange = { newValue ->
                            // 숫자만 허용하되 빈 값도 허용
                            val cleaned = newValue.replace(Regex("[^0-9]"), "")
                            totalCount = cleaned
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedBorderColor = AppColors.Primary,
                            unfocusedBorderColor = ThemeColors.textSecondary(isDarkTheme)
                        ),
                        singleLine = true
                    )
                    Text(
                        text = strings.countHint,
                        fontSize = 12.sp,
                        color = ThemeColors.textSecondary(isDarkTheme),
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }
            }

            // 상품 설정
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = strings.prizeSettings,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // 1등 설정
                    PrizeSettingItem(
                        title = strings.firstPrize,
                        count = firstPrizeCount,
                        onCountChange = {
                            val cleanInput = cleanNumberInput(it)
                            val (newFirst, newSecond, newThird) = validateAndAdjustCounts(
                                "first",
                                cleanInput,
                                totalCount,
                                firstPrizeCount,
                                secondPrizeCount,
                                thirdPrizeCount
                            )
                            firstPrizeCount = newFirst
                            secondPrizeCount = newSecond
                            thirdPrizeCount = newThird
                        },
                        prizeName = firstPrizeName,
                        onPrizeNameChange = { firstPrizeName = it },
                        color = Color(0xFFFFD700),
                        isDarkTheme = isDarkTheme,
                        strings = strings
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 2등 설정
                    PrizeSettingItem(
                        title = strings.secondPrize,
                        count = secondPrizeCount,
                        onCountChange = {
                            val cleanInput = cleanNumberInput(it)
                            val (newFirst, newSecond, newThird) = validateAndAdjustCounts(
                                "second",
                                cleanInput,
                                totalCount,
                                firstPrizeCount,
                                secondPrizeCount,
                                thirdPrizeCount
                            )
                            firstPrizeCount = newFirst
                            secondPrizeCount = newSecond
                            thirdPrizeCount = newThird
                        },
                        prizeName = secondPrizeName,
                        onPrizeNameChange = { secondPrizeName = it },
                        color = Color(0xFFC0C0C0),
                        isDarkTheme = isDarkTheme,
                        strings = strings
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3등 설정
                    PrizeSettingItem(
                        title = strings.thirdPrize,
                        count = thirdPrizeCount,
                        onCountChange = {
                            val cleanInput = cleanNumberInput(it)
                            val (newFirst, newSecond, newThird) = validateAndAdjustCounts(
                                "third",
                                cleanInput,
                                totalCount,
                                firstPrizeCount,
                                secondPrizeCount,
                                thirdPrizeCount
                            )
                            firstPrizeCount = newFirst
                            secondPrizeCount = newSecond
                            thirdPrizeCount = newThird
                        },
                        prizeName = thirdPrizeName,
                        onPrizeNameChange = { thirdPrizeName = it },
                        color = Color(0xFFCD7F32),
                        isDarkTheme = isDarkTheme,
                        strings = strings
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 꽝 표시 (읽기 전용)
                    val noPrizeCount = kotlin.math.max(
                        0,
                        (totalCount.toIntOrNull() ?: 20) -
                                (firstPrizeCount.toIntOrNull() ?: 0) -
                                (secondPrizeCount.toIntOrNull() ?: 0) -
                                (thirdPrizeCount.toIntOrNull() ?: 0)
                    )

                    PrizeDisplayItem(
                        title = strings.noPrize,
                        count = noPrizeCount.toString(),
                        prizeName = strings.noPrize,
                        color = Color(0xFF808080),
                        isDarkTheme = isDarkTheme,
                        strings = strings
                    )
                }
            }

            // 저장 버튼
            Button(
                onClick = {
                    // 유효성 검사
                    val inputTotalCount = totalCount.toIntOrNull()

                    when {
                        totalCount.isBlank() -> {
                            // 빈 값일 때는 기본값으로 저장
                            viewModel.updateSettings(
                                totalCount = 20,
                                firstPrizeCount = firstPrizeCount.toIntOrNull() ?: 1,
                                secondPrizeCount = secondPrizeCount.toIntOrNull() ?: 2,
                                thirdPrizeCount = thirdPrizeCount.toIntOrNull() ?: 3,
                                firstPrizeName = firstPrizeName.ifBlank { strings.defaultFirstPrize },
                                secondPrizeName = secondPrizeName.ifBlank { strings.defaultSecondPrize },
                                thirdPrizeName = thirdPrizeName.ifBlank { strings.defaultThirdPrize }
                            )
                            onDismiss()
                        }

                        inputTotalCount == null || inputTotalCount < 10 || inputTotalCount > 30 -> {
                            // 유효하지 않은 값일 때 스낵바 표시
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = strings.invalidTotalCount,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                        else -> {
                            // 유효한 값일 때 저장
                            viewModel.updateSettings(
                                totalCount = inputTotalCount,
                                firstPrizeCount = firstPrizeCount.toIntOrNull() ?: 1,
                                secondPrizeCount = secondPrizeCount.toIntOrNull() ?: 2,
                                thirdPrizeCount = thirdPrizeCount.toIntOrNull() ?: 3,
                                firstPrizeName = firstPrizeName.ifBlank { strings.defaultFirstPrize },
                                secondPrizeName = secondPrizeName.ifBlank { strings.defaultSecondPrize },
                                thirdPrizeName = thirdPrizeName.ifBlank { strings.defaultThirdPrize }
                            )
                            onDismiss()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.ButtonSuccess,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = strings.saveButton,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 스낵바 호스트 추가
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * 상품 설정 아이템 컴포넌트
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrizeSettingItem(
    title: String,
    count: String,
    onCountChange: (String) -> Unit,
    prizeName: String,
    onPrizeNameChange: (String) -> Unit,
    color: Color,
    isDarkTheme: Boolean,
    strings: LocalizedStrings
) {
    val textColor = ThemeColors.textPrimary(isDarkTheme)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 상품 등수 표시
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color, androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 개수 입력
        OutlinedTextField(
            value = count,
            onValueChange = onCountChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(80.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedBorderColor = AppColors.Primary,
                unfocusedBorderColor = ThemeColors.textSecondary(isDarkTheme)
            ),
            singleLine = true,
            suffix = {
                Text(
                    text = strings.countSuffix,
                    fontSize = 12.sp,
                    color = ThemeColors.textSecondary(isDarkTheme)
                )
            }
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 상품명 입력
        OutlinedTextField(
            value = prizeName,
            onValueChange = onPrizeNameChange,
            placeholder = {
                Text(
                    text = strings.prizeNameHint,
                    fontSize = 12.sp
                )
            },
            modifier = Modifier.weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedBorderColor = AppColors.Primary,
                unfocusedBorderColor = ThemeColors.textSecondary(isDarkTheme)
            ),
            singleLine = true
        )
    }
}

/**
 * 상품 표시 아이템 컴포넌트 (읽기 전용)
 */
@Composable
private fun PrizeDisplayItem(
    title: String,
    count: String,
    prizeName: String,
    color: Color,
    isDarkTheme: Boolean,
    strings: LocalizedStrings
) {
    val textColor = ThemeColors.textPrimary(isDarkTheme)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 상품 등수 표시
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color, androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = if (title.length > 3) 10.sp else 12.sp, // 긴 텍스트는 폰트 크기 축소
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 개수 표시 (읽기 전용)
        Card(
            modifier = Modifier.width(80.dp),
            colors = CardDefaults.cardColors(
                containerColor = ThemeColors.surface(isDarkTheme).copy(alpha = 0.5f)
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                ThemeColors.textSecondary(isDarkTheme).copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = "$count${strings.countSuffix}",
                fontSize = 14.sp,
                color = ThemeColors.textSecondary(isDarkTheme),
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 상품명 표시 (읽기 전용)
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = ThemeColors.surface(isDarkTheme).copy(alpha = 0.5f)
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                ThemeColors.textSecondary(isDarkTheme).copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = prizeName,
                fontSize = 14.sp,
                color = ThemeColors.textSecondary(isDarkTheme),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}