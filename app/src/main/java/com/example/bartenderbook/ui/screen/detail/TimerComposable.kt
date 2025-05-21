package com.example.bartenderbook.ui.screen.detail

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("DefaultLocale", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TimerScreen(viewModel: TimerViewModel, timePreparation: Int) {
    val time by viewModel.time.collectAsState()

    val windowSize = calculateWindowSizeClass(LocalContext.current as Activity)

    val timerFontSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 50.sp
        WindowWidthSizeClass.Medium -> 64.sp
        WindowWidthSizeClass.Expanded -> 72.sp
        else -> 50.sp
    }

    val buttonFontSize = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 18.sp
        WindowWidthSizeClass.Medium -> 20.sp
        WindowWidthSizeClass.Expanded -> 22.sp
        else -> 18.sp
    }

    LaunchedEffect(Unit) {
        if (time == 0) {
            viewModel.setInitialTime(timePreparation)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = String.format("%02d:%02d", time / 60, time % 60),
            fontSize = timerFontSize,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.startTimer(timePreparation) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Start", fontSize = buttonFontSize)
            }
            Button(
                onClick = { viewModel.stopTimer() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop", fontSize = buttonFontSize)
            }
            Button(
                onClick = { viewModel.cancelTimer() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Reset", fontSize = buttonFontSize)
            }
        }
    }
}
