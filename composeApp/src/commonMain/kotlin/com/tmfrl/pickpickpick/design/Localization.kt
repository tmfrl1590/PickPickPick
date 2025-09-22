package com.tmfrl.pickpickpick.design

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

/**
 * 지원하는 언어 목록
 */
enum class Language(val code: String, val displayName: String) {
    KOREAN("ko", "한국어"),
    ENGLISH("en", "English")
}

/**
 * 다국어 문자열 관리 클래스
 */
class LocalizedStrings(private val language: Language) {

    // 공통 UI
    val settings = when (language) {
        Language.KOREAN -> "설정"
        Language.ENGLISH -> "Settings"
    }

    val themeSettings = when (language) {
        Language.KOREAN -> "테마 설정"
        Language.ENGLISH -> "Theme Settings"
    }

    val lightTheme = when (language) {
        Language.KOREAN -> "라이트 테마"
        Language.ENGLISH -> "Light Theme"
    }

    val darkTheme = when (language) {
        Language.KOREAN -> "다크 테마"
        Language.ENGLISH -> "Dark Theme"
    }

    val languageSettings = when (language) {
        Language.KOREAN -> "언어 설정"
        Language.ENGLISH -> "Language Settings"
    }

    val vibrationSettings = when (language) {
        Language.KOREAN -> "진동 설정"
        Language.ENGLISH -> "Vibration Settings"
    }

    val gameResultVibration = when (language) {
        Language.KOREAN -> "게임 결과 진동 알림"
        Language.ENGLISH -> "Game Result Vibration"
    }

    val appInfo = when (language) {
        Language.KOREAN -> "앱 정보"
        Language.ENGLISH -> "App Information"
    }

    val appVersion = when (language) {
        Language.KOREAN -> "앱버전"
        Language.ENGLISH -> "App Version"
    }

    val appDescription = when (language) {
        Language.KOREAN -> "돌림판, 주사위, 가위바위보, 추억의뽑기를\n즐길 수 있는 재미있는 게임 앱"
        Language.ENGLISH -> "A fun game app where you can enjoy\nSpinning Wheel, Dice, Rock-Paper-Scissors, and Memory Lottery"
    }

    val developer = when (language) {
        Language.KOREAN -> "개발자"
        Language.ENGLISH -> "Developer"
    }

    // 게임 관련
    val spinningWheel = when (language) {
        Language.KOREAN -> "돌림판"
        Language.ENGLISH -> "Spinning Wheel"
    }

    val dice = when (language) {
        Language.KOREAN -> "주사위"
        Language.ENGLISH -> "Dice"
    }

    val rockPaperScissors = when (language) {
        Language.KOREAN -> "가위바위보"
        Language.ENGLISH -> "Rock Paper Scissors"
    }

    val memoryLottery = when (language) {
        Language.KOREAN -> "추억의뽑기"
        Language.ENGLISH -> "Memory Lottery"
    }

    val diceGame = when (language) {
        Language.KOREAN -> "주사위 던지기"
        Language.ENGLISH -> "Dice Rolling"
    }

    val diceSubtitle = when (language) {
        Language.KOREAN -> "주사위를 던져서 1부터 6까지의 숫자를 확인해보세요!"
        Language.ENGLISH -> "Roll the dice to see numbers from 1 to 6!"
    }

    val throwDice = when (language) {
        Language.KOREAN -> "🎲 던지기!"
        Language.ENGLISH -> "🎲 Roll!"
    }

    val throwing = when (language) {
        Language.KOREAN -> "던지는 중..."
        Language.ENGLISH -> "Rolling..."
    }

    val result = when (language) {
        Language.KOREAN -> "결과"
        Language.ENGLISH -> "Result"
    }

    // 하단 네비게이션 탭 이름들
    val tabSpinningWheel = when (language) {
        Language.KOREAN -> "돌림판"
        Language.ENGLISH -> "Wheel"
    }

    val tabDice = when (language) {
        Language.KOREAN -> "주사위"
        Language.ENGLISH -> "Dice"
    }

    val tabRockPaperScissors = when (language) {
        Language.KOREAN -> "가위바위보"
        Language.ENGLISH -> "RPS"
    }

    val tabMemoryLottery = when (language) {
        Language.KOREAN -> "추억의뽑기"
        Language.ENGLISH -> "Lottery"
    }

    val tabSettings = when (language) {
        Language.KOREAN -> "설정"
        Language.ENGLISH -> "Settings"
    }

    // 돌림판 게임 관련
    val spinningWheelTitle = when (language) {
        Language.KOREAN -> "돌려돌려 돌림판"
        Language.ENGLISH -> "Spinning Wheel"
    }

    val editOptions = when (language) {
        Language.KOREAN -> "선택지 편집"
        Language.ENGLISH -> "Edit Options"
    }

    val optionCount = when (language) {
        Language.KOREAN -> "개"
        Language.ENGLISH -> " items"
    }

    val startButton = when (language) {
        Language.KOREAN -> "시작"
        Language.ENGLISH -> "Start"
    }

    val spinningInProgress = when (language) {
        Language.KOREAN -> "돌리는 중..."
        Language.ENGLISH -> "Spinning..."
    }

    val selectedItem = when (language) {
        Language.KOREAN -> "선택된 항목"
        Language.ENGLISH -> "Selected Item"
    }

    // 돌림판 편집 관련
    val participantsEditTitle = when (language) {
        Language.KOREAN -> "선택지 편집"
        Language.ENGLISH -> "Edit Options"
    }

    val newParticipantName = when (language) {
        Language.KOREAN -> "선택지 추가"
        Language.ENGLISH -> "Add Option"
    }

    val addButton = when (language) {
        Language.KOREAN -> "추가"
        Language.ENGLISH -> "Add"
    }

    val deleteButton = when (language) {
        Language.KOREAN -> "삭제"
        Language.ENGLISH -> "Delete"
    }

    val minimumParticipantsWarning = when (language) {
        Language.KOREAN -> "최소 2개의 선택지가 필요합니다."
        Language.ENGLISH -> "At least 2 options are required."
    }

    // 가위바위보 게임 관련
    val rockPaperScissorsTitle = when (language) {
        Language.KOREAN -> "가위바위보"
        Language.ENGLISH -> "Rock Paper Scissors"
    }

    val rockPaperScissorsSubtitle = when (language) {
        Language.KOREAN -> "가위, 바위, 보 중 하나를 선택하세요!"
        Language.ENGLISH -> "Choose Rock, Paper, or Scissors!"
    }

    val user = when (language) {
        Language.KOREAN -> "나"
        Language.ENGLISH -> "You"
    }

    val computer = when (language) {
        Language.KOREAN -> "컴퓨터"
        Language.ENGLISH -> "Computer"
    }

    val vs = when (language) {
        Language.KOREAN -> "VS"
        Language.ENGLISH -> "VS"
    }

    val chooseText = when (language) {
        Language.KOREAN -> "선택하세요"
        Language.ENGLISH -> "Choose"
    }

    val win = when (language) {
        Language.KOREAN -> "승리!"
        Language.ENGLISH -> "You Win!"
    }

    val lose = when (language) {
        Language.KOREAN -> "패배!"
        Language.ENGLISH -> "You Lose!"
    }

    val draw = when (language) {
        Language.KOREAN -> "무승부!"
        Language.ENGLISH -> "Draw!"
    }

    val rock = when (language) {
        Language.KOREAN -> "바위"
        Language.ENGLISH -> "Rock"
    }

    val paper = when (language) {
        Language.KOREAN -> "보"
        Language.ENGLISH -> "Paper"
    }

    val scissors = when (language) {
        Language.KOREAN -> "가위"
        Language.ENGLISH -> "Scissors"
    }

    val resetScore = when (language) {
        Language.KOREAN -> "점수 초기화"
        Language.ENGLISH -> "Reset Score"
    }

    // 추억의 뽑기 게임 관련
    val lotteryTitle = when (language) {
        Language.KOREAN -> "추억의 뽑기"
        Language.ENGLISH -> "Memory Lottery"
    }

    val lotteryStart = when (language) {
        Language.KOREAN -> "뽑기 시작"
        Language.ENGLISH -> "Start Lottery"
    }

    val lotterySettings = when (language) {
        Language.KOREAN -> "뽑기 설정"
        Language.ENGLISH -> "Lottery Settings"
    }

    val pickLottery = when (language) {
        Language.KOREAN -> "뽑기를 선택하세요"
        Language.ENGLISH -> "Please select a lottery"
    }

    val remainingLottery = when (language) {
        Language.KOREAN -> "남은 뽑기"
        Language.ENGLISH -> "Remaining"
    }

    val resetButton = when (language) {
        Language.KOREAN -> "다시하기"
        Language.ENGLISH -> "Reset"
    }

    val firstPlace = when (language) {
        Language.KOREAN -> "1등"
        Language.ENGLISH -> "1st"
    }

    val secondPlace = when (language) {
        Language.KOREAN -> "2등"
        Language.ENGLISH -> "2nd"
    }

    val thirdPlace = when (language) {
        Language.KOREAN -> "3등"
        Language.ENGLISH -> "3rd"
    }

    val countSuffix = when (language) {
        Language.KOREAN -> "개"
        Language.ENGLISH -> ""
    }

    // Lottery Settings Screen strings
    val lotterySettingsTitle = when (language) {
        Language.KOREAN -> "뽑기 설정"
        Language.ENGLISH -> "Lottery Settings"
    }
    val totalLotteryCount = when (language) {
        Language.KOREAN -> "전체 뽑기 개수"
        Language.ENGLISH -> "Total Ticket Count"
    }
    val countHint = when (language) {
        Language.KOREAN -> "최소 10개, 최대 30개까지 설정 가능합니다."
        Language.ENGLISH -> "Min 10, Max 30 allowed."
    }
    val prizeSettings = when (language) {
        Language.KOREAN -> "상품 설정"
        Language.ENGLISH -> "Prize Settings"
    }
    val firstPrize = when (language) {
        Language.KOREAN -> "1등"
        Language.ENGLISH -> "1st"
    }
    val secondPrize = when (language) {
        Language.KOREAN -> "2등"
        Language.ENGLISH -> "2nd"
    }
    val thirdPrize = when (language) {
        Language.KOREAN -> "3등"
        Language.ENGLISH -> "3rd"
    }
    val prizeNameHint = when (language) {
        Language.KOREAN -> "상품명 입력"
        Language.ENGLISH -> "Enter prize name"
    }
    val saveButton = when (language) {
        Language.KOREAN -> "저장"
        Language.ENGLISH -> "Save"
    }
    val invalidTotalCount = when (language) {
        Language.KOREAN -> "전체 뽑기 개수는 10개 이상 30개 이하로 입력해주세요."
        Language.ENGLISH -> "Total count must be between 10 and 30."
    }
    val defaultFirstPrize = when (language) {
        Language.KOREAN -> "상품A"
        Language.ENGLISH -> "Prize A"
    }
    val defaultSecondPrize = when (language) {
        Language.KOREAN -> "상품B"
        Language.ENGLISH -> "Prize B"
    }
    val defaultThirdPrize = when (language) {
        Language.KOREAN -> "상품C"
        Language.ENGLISH -> "Prize C"
    }

    val noPrize = when (language) {
        Language.KOREAN -> "꽝"
        Language.ENGLISH -> "No Prize"
    }

}

/**
 * 현재 언어 설정을 제공하는 CompositionLocal
 */
val LocalLanguage = compositionLocalOf { Language.KOREAN }
val LocalStrings = compositionLocalOf { LocalizedStrings(Language.KOREAN) }

/**
 * 다국어 문자열에 접근하는 Composable 함수
 */
@Composable
fun strings(): LocalizedStrings {
    return LocalStrings.current
}

/**
 * 언어 설정 Provider
 */
@Composable
fun LanguageProvider(
    language: Language,
    content: @Composable () -> Unit
) {
    val localizedStrings = remember(language) { LocalizedStrings(language) }

    CompositionLocalProvider(
        LocalLanguage provides language,
        LocalStrings provides localizedStrings,
        content = content
    )
}