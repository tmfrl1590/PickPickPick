package com.tmfrl.pickpickpick.presentation.games.spinningwheel

import androidx.compose.runtime.*

/**
 * 돌림판 게임의 ViewModel
 * 참가자 목록과 게임 상태를 관리합니다.
 */
class SpinningWheelViewModel {

    // 참가자 목록 상태 (기본값을 문자열 상수에서 가져옴)
    private val _participants = mutableStateOf(
        listOf(
            SpinningWheelStrings.DEFAULT_PARTICIPANT_1,
            SpinningWheelStrings.DEFAULT_PARTICIPANT_2
        )
    )
    val participants: State<List<String>> = _participants

    // 선택된 참가자 상태
    private val _selectedParticipant = mutableStateOf<String?>(null)
    val selectedParticipant: State<String?> = _selectedParticipant

    // 회전 상태
    private val _isSpinning = mutableStateOf(false)
    val isSpinning: State<Boolean> = _isSpinning

    /**
     * 참가자를 추가합니다
     */
    fun addParticipant(name: String) {
        if (name.isNotBlank() && _participants.value.size < 8) {
            _participants.value = _participants.value + name.trim()
            clearSelectedParticipant() // 참가자가 변경되면 결과 초기화
        }
    }

    /**
     * 참가자를 삭제합니다
     */
    fun removeParticipant(index: Int) {
        if (_participants.value.size > 2 && index < _participants.value.size) {
            _participants.value = _participants.value.filterIndexed { i, _ -> i != index }
            clearSelectedParticipant() // 참가자가 변경되면 결과 초기화
        }
    }

    /**
     * 참가자 목록을 직접 설정합니다
     */
    fun setParticipants(newParticipants: List<String>) {
        if (newParticipants.size in 2..8) {
            _participants.value = newParticipants
            clearSelectedParticipant()
        }
    }

    /**
     * 선택된 참가자를 설정합니다
     */
    fun setSelectedParticipant(participant: String?) {
        _selectedParticipant.value = participant
    }

    /**
     * 선택된 참가자를 초기화합니다
     */
    fun clearSelectedParticipant() {
        _selectedParticipant.value = null
    }

    /**
     * 회전 상태를 설정합니다
     */
    fun setSpinning(spinning: Boolean) {
        _isSpinning.value = spinning
    }

    /**
     * 최종 각도에 따라 선택된 참가자를 결정합니다
     */
    fun selectParticipantByAngle(finalAngle: Float) {
        val ps = _participants.value
        if (ps.isEmpty()) return
        val normalizedAngle = (360f - (finalAngle % 360f)) % 360f
        val sectionAngle = 360f / ps.size
        val selectedIndex = (normalizedAngle / sectionAngle).toInt() % ps.size
        _selectedParticipant.value = ps[selectedIndex]
        _isSpinning.value = false
    }

    /**
     * 게임을 시작합니다
     */
    fun startGame() {
        if (!_isSpinning.value) {
            clearSelectedParticipant()
            _isSpinning.value = true
        }
    }
}