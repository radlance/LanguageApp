package com.radlance.languageapp.presentation.practice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.common.NoConnectionScreen
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 30.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(
    navigateToMainScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PracticeViewModel = hiltViewModel()
) {

    var selectedAnswer by rememberSaveable { mutableStateOf("") }
    var isChosenAnswer by rememberSaveable { mutableStateOf(false) }

    val loadGameResultUiState by viewModel.loadGameResultUiStat.collectAsState()
    val incrementScoreResultUiState by viewModel.incrementResultUiState.collectAsState()

    val selectedQuestion by viewModel.selectedQuestion.collectAsState()

    Box {
        loadGameResultUiState.Show(
            onSuccess = { gameData ->
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(R.string.word_practice),
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.W500,
                                    lineHeight = 28.sp,
                                    fontFamily = fredokaFamily
                                )
                            },

                            navigationIcon = {
                                IconButton(onClick = navigateToMainScreen) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow_back),
                                        contentDescription = "ic_arrow_back",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .width(17.dp)
                                            .height(27.dp)
                                    )
                                }
                            },

                            colors = TopAppBarDefaults.topAppBarColors()
                                .copy(containerColor = DeepBlue)
                        )
                    }
                ) { contentPadding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                            .padding(horizontal = 23.dp)
                    ) {
                        Spacer(Modifier.height(34.dp))
                        selectedQuestion?.let { question ->

                            Text(
                                text = question.word,
                                fontSize = 28.sp,
                                fontFamily = fredokaFamily,
                                lineHeight = 34.sp,
                                fontWeight = FontWeight.W600
                            )

                            Spacer(Modifier.height(2.dp))

                            Text(
                                text = question.transcription,
                                fontSize = 17.sp,
                                fontFamily = fredokaFamily,
                                fontWeight = FontWeight.W400,
                                lineHeight = 22.sp
                            )

                            Spacer(Modifier.height(35.dp))

                            PracticeAnswerOptions(
                                answers = question.answers,
                                correctAnswer = question.answers[question.correctAnswerNumber],
                                selectedAnswer = selectedAnswer,
                                isChosenAnswer = isChosenAnswer,
                                onAnswerClick = {
                                    selectedAnswer = it
                                }
                            )

                            Spacer(Modifier.weight(1f))
                            AppButton(
                                labelResId = if (!isChosenAnswer) R.string.check else R.string.next,
                                onClick = {
                                    if (!isChosenAnswer) {
                                        if (selectedAnswer == question.answers[question.correctAnswerNumber]) {
                                            viewModel.updateCurrentStreak()
                                            viewModel.incrementUserScore()
                                        } else {
                                            viewModel.resetStreak()
                                            isChosenAnswer = true
                                        }
                                    } else {
                                        isChosenAnswer = false
                                        selectedAnswer = ""
                                        viewModel.selectRandomWord(gameData.questions)
                                    }
                                },
                                enabled = selectedAnswer.isNotBlank()
                            )
                            Spacer(Modifier.height(27.dp))

                        } ?: run {
                            viewModel.selectRandomWord(gameData.questions)
                        }
                    }
                }
            },

            onError = {
                NoConnectionScreen(onRetryClick = viewModel::loadGame)
            },

            onLoading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        )

        incrementScoreResultUiState.Show(
            onSuccess = { isChosenAnswer = true },
            onError = {
                NoConnectionScreen(onRetryClick = viewModel::incrementUserScore)
            },
            onLoading = {}
        )
    }
}