package com.radlance.languageapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.domain.main.Exercise
import com.radlance.languageapp.presentation.ui.theme.LanguageAppTheme
import com.radlance.languageapp.presentation.ui.theme.Orange
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun ExerciseItem(
    exercise: Exercise,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
    ) {
        Image(
            painter = painterResource(R.drawable.exercise_example),
            contentDescription = "exercise",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = exercise.name,
            fontSize = 13.sp,
            color = Color.White,
            fontFamily = fredokaFamily,
            fontWeight = FontWeight.W400,
            lineHeight = 18.sp
        )
        Spacer(Modifier.height(10.dp))
    }
}

@Preview
@Composable
private fun ExerciseItemPreview() {
    LanguageAppTheme {
        ExerciseItem(
            exercise = Exercise(id = 1, name = "Game", image = ""),
            backgroundColor = Orange,
            modifier = Modifier.size(width = 153.dp, height = 117.dp)
        )
    }
}