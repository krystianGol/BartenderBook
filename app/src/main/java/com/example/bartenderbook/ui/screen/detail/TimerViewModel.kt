package com.example.bartenderbook.ui.screen.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.os.CountDownTimer

class TimerViewModel : ViewModel() {

    private val _time = MutableStateFlow(0)
    val time: StateFlow<Int> = _time

    private var initialTime: Int = 0
    private var timer: CountDownTimer? = null
    private var isStarted = false

    fun setInitialTime(seconds: Int) {
        initialTime = seconds
        _time.value = seconds
    }

    fun startTimer(timePreparation: Int = initialTime) {
        if (isStarted) return
        isStarted = true

        val currentTime = _time.value

        timer = object : CountDownTimer(currentTime * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _time.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                _time.value = 0
                isStarted = false
            }
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
        isStarted = false
    }

    fun cancelTimer() {
        stopTimer()
        _time.value = initialTime
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
