package com.radlance.languageapp.presentation.practice

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.presentation.ui.theme.Blue
import com.radlance.languageapp.presentation.ui.theme.GrayLight
import com.radlance.languageapp.presentation.ui.theme.Green
import com.radlance.languageapp.presentation.ui.theme.Orange
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 30.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun PracticeAnswerOptions(
    answers: List<String>,
    correctAnswer: String,
    selectedAnswer: String,
    isChosenAnswer: Boolean,
    onAnswerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        answers.forEach { answer ->
            val backgroundColor by animateColorAsState(
                when {
                    correctAnswer == answer && isChosenAnswer -> Green
                    answer == selectedAnswer && !isChosenAnswer -> Blue
                    answer == selectedAnswer && isChosenAnswer -> Orange
                    else -> GrayLight
                }
            )

            val textColor by animateColorAsState(
                if ((answer == selectedAnswer && !isChosenAnswer) || (answer == selectedAnswer && isChosenAnswer)) {
                    Color.White
                } else {
                    Color.Black
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .then(
                        if (!isChosenAnswer) {
                            Modifier.clickable { onAnswerClick(answer) }
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = answer,
                    fontSize = 20.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W500,
                    lineHeight = 24.sp,
                    color = textColor
                )
            }
        }
    }
}