package com.radlance.languageapp.presentation.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.ui.theme.GrayLight
import com.radlance.languageapp.presentation.ui.theme.Green
import com.radlance.languageapp.presentation.ui.theme.Orange
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

@Composable
fun GameAnswerOptions(
    answers: List<String>,
    correctAnswer: String,
    firstPlayerAnswer: String?,
    secondPlayerAnswer: String?,
    onAnswerClick: (Int) -> Unit,
    isAnswered: Boolean,
    isCreator: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        answers.forEach { answer ->
            val backgroundColor by animateColorAsState(
                when {
                    correctAnswer == answer && (firstPlayerAnswer == answer || secondPlayerAnswer == answer) -> Green
                    correctAnswer != answer && (firstPlayerAnswer == answer || secondPlayerAnswer == answer) -> Orange
                    else -> GrayLight
                }
            )

            val textColor by animateColorAsState(
                if (firstPlayerAnswer == answer || secondPlayerAnswer == answer) {
                    Color.White
                } else {
                    Color.Black
                }
            )

            val additionalText = when {
                firstPlayerAnswer == answer && isCreator -> R.string.you
                secondPlayerAnswer == answer && isCreator -> R.string.p2
                firstPlayerAnswer == answer && !isCreator -> R.string.p2
                secondPlayerAnswer == answer && !isCreator -> R.string.you
                else -> R.string.empty
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .then(
                        if (!isAnswered) {
                            Modifier.clickable { onAnswerClick(answers.indexOf(answer)) }
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.align(Alignment.CenterStart)) {
                    Spacer(Modifier.width(13.dp))
                    Text(
                        text = stringResource(additionalText),
                        fontSize = 20.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W500,
                        lineHeight = 24.sp,
                        color = textColor
                    )
                }
                Text(
                    text = answer,
                    fontSize = 20.sp,
                    fontFamily = fredokaFamily,
                    fontWeight = FontWeight.W500,
                    lineHeight = 24.sp
                )
            }
        }
    }
}