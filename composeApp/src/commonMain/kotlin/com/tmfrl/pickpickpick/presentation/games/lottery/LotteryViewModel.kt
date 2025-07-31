package com.tmfrl.pickpickpick.presentation.games.lottery

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlin.random.Random

/**
 * 뽑기 게임 ViewModel
 */
class LotteryViewModel {
    // 게임 설정
    private val _totalCount = mutableStateOf(20)
    val totalCount: State<Int> = _totalCount

    private val _firstPrizeCount = mutableStateOf(1)
    val firstPrizeCount: State<Int> = _firstPrizeCount

    private val _secondPrizeCount = mutableStateOf(2)
    val secondPrizeCount: State<Int> = _secondPrizeCount

    private val _thirdPrizeCount = mutableStateOf(3)
    val thirdPrizeCount: State<Int> = _thirdPrizeCount

    // 상품명
    private val _firstPrizeName = mutableStateOf(LotteryStrings.DEFAULT_FIRST_PRIZE)
    val firstPrizeName: State<String> = _firstPrizeName

    private val _secondPrizeName = mutableStateOf(LotteryStrings.DEFAULT_SECOND_PRIZE)
    val secondPrizeName: State<String> = _secondPrizeName

    private val _thirdPrizeName = mutableStateOf(LotteryStrings.DEFAULT_THIRD_PRIZE)
    val thirdPrizeName: State<String> = _thirdPrizeName

    // 게임 상태
    private val _lotteries = mutableStateOf<List<LotteryItem>>(emptyList())
    val lotteries: State<List<LotteryItem>> = _lotteries

    private val _isGameStarted = mutableStateOf(false)
    val isGameStarted: State<Boolean> = _isGameStarted

    init {
        generateLotteries()
    }

    /**
     * 뽑기 생성
     */
    private fun generateLotteries() {
        val lotteriesList = mutableListOf<LotteryItem>()
        val total = _totalCount.value
        var currentId = 0

        // 1등 뽑기 추가
        repeat(_firstPrizeCount.value) {
            lotteriesList.add(
                LotteryItem(
                    id = currentId++,
                    prizeType = PrizeType.FIRST,
                    prizeName = _firstPrizeName.value,
                    isOpened = false
                )
            )
        }

        // 2등 뽑기 추가
        repeat(_secondPrizeCount.value) {
            lotteriesList.add(
                LotteryItem(
                    id = currentId++,
                    prizeType = PrizeType.SECOND,
                    prizeName = _secondPrizeName.value,
                    isOpened = false
                )
            )
        }

        // 3등 뽑기 추가
        repeat(_thirdPrizeCount.value) {
            lotteriesList.add(
                LotteryItem(
                    id = currentId++,
                    prizeType = PrizeType.THIRD,
                    prizeName = _thirdPrizeName.value,
                    isOpened = false
                )
            )
        }

        // 나머지는 꽝으로 채움
        val noPrizeCount =
            total - _firstPrizeCount.value - _secondPrizeCount.value - _thirdPrizeCount.value
        repeat(noPrizeCount) {
            lotteriesList.add(
                LotteryItem(
                    id = currentId++,
                    prizeType = PrizeType.NO_PRIZE,
                    prizeName = LotteryStrings.DEFAULT_NO_PRIZE,
                    isOpened = false
                )
            )
        }

        // 랜덤 섞기
        _lotteries.value = lotteriesList.shuffled()
    }

    /**
     * 게임 시작
     */
    fun startGame() {
        _isGameStarted.value = true
        generateLotteries()
    }

    /**
     * 게임 리셋
     */
    fun resetGame() {
        _isGameStarted.value = false
        generateLotteries()
    }

    /**
     * 뽑기 선택
     */
    fun selectLottery(lotteryId: Int) {
        val updatedLotteries = _lotteries.value.map { lottery ->
            if (lottery.id == lotteryId && !lottery.isOpened) {
                lottery.copy(isOpened = true)
            } else {
                lottery
            }
        }
        _lotteries.value = updatedLotteries
    }

    /**
     * 설정 업데이트
     */
    fun updateSettings(
        totalCount: Int,
        firstPrizeCount: Int,
        secondPrizeCount: Int,
        thirdPrizeCount: Int,
        firstPrizeName: String,
        secondPrizeName: String,
        thirdPrizeName: String
    ) {
        _totalCount.value = totalCount
        _firstPrizeCount.value = firstPrizeCount
        _secondPrizeCount.value = secondPrizeCount
        _thirdPrizeCount.value = thirdPrizeCount
        _firstPrizeName.value = firstPrizeName
        _secondPrizeName.value = secondPrizeName
        _thirdPrizeName.value = thirdPrizeName

        generateLotteries()
    }

    /**
     * 남은 뽑기 개수
     */
    fun getRemainingCount(): Int {
        return _lotteries.value.count { !it.isOpened }
    }
}

/**
 * 뽑기 아이템 데이터 클래스
 */
data class LotteryItem(
    val id: Int,
    val prizeType: PrizeType,
    val prizeName: String,
    val isOpened: Boolean = false
)

/**
 * 상품 타입 열거형
 */
enum class PrizeType {
    FIRST, SECOND, THIRD, NO_PRIZE
}