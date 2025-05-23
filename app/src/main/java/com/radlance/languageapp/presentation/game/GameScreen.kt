package com.radlance.languageapp.presentation.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    isCreator: Boolean,
    navigateUp: () -> Unit,
    navigateToMainScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    var isAnswered by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.game),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 28.sp,
                        fontFamily = fredokaFamily
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = DeepBlue)
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            gameState?.let { game ->
                BackHandler {
                    if (game.status == "NEW") {
                        viewModel.cancelGame(game.id)
                    }
                    navigateUp()
                }

                LaunchedEffect(game.currentQuestion) {
                    isAnswered = false
                }

                if (game.firstPlayer == null || game.secondPlayer == null) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(60.dp))
                        Spacer(Modifier.height(20.dp))
                        val stringResId = if (isCreator) {
                            R.string.waiting_for_the_second_player
                        } else {
                            R.string.searching_for_a_game
                        }
                        Text(
                            text = stringResource(stringResId),
                            fontSize = 30.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                        Spacer(Modifier.height(20.dp))
                        AppButton(
                            labelResId = R.string.cancel,
                            onClick = {
                                viewModel.cancelGame(game.id)
                                navigateUp()
                            },
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                } else if (game.status == "IN_PROGRESS") {
                    val currentQuestion = game.gameData.questions[game.currentQuestion]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(horizontal = 23.dp)
                    ) {
                        Spacer(Modifier.height(34.dp))

                        val firstPlayer = game.firstPlayer
                        val secondPlayer = game.secondPlayer

                        LaunchedEffect(game) {
                            val firstPlayerAnswerNumber = firstPlayer.selectedAnswer
                            val secondPlayerAnswerNumber = secondPlayer.selectedAnswer

                            isAnswered = if (isCreator) {
                                secondPlayerAnswerNumber == currentQuestion.correctAnswerNumber || firstPlayerAnswerNumber != null
                            } else {
                                firstPlayerAnswerNumber == currentQuestion.correctAnswerNumber || secondPlayerAnswerNumber != null
                            }
                        }

                        Text(
                            text = currentQuestion.word,
                            fontSize = 28.sp,
                            fontFamily = fredokaFamily,
                            lineHeight = 34.sp,
                            fontWeight = FontWeight.W600
                        )

                        Spacer(Modifier.height(2.dp))

                        Text(
                            text = currentQuestion.transcription,
                            fontSize = 17.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W400,
                            lineHeight = 22.sp
                        )

                        Spacer(Modifier.height(35.dp))

                        GameAnswerOptions(
                            answers = currentQuestion.answers,
                            correctAnswer = currentQuestion.answers[currentQuestion.correctAnswerNumber],
                            firstPlayerAnswer = firstPlayer.selectedAnswer?.let {
                                currentQuestion.answers[it]
                            },
                            secondPlayerAnswer = secondPlayer.selectedAnswer?.let {
                                currentQuestion.answers[it]
                            },
                            onAnswerClick = {
                                viewModel.answer(
                                    gameId = game.id,
                                    selectedAnswerIndex = it
                                )
                            },
                            isAnswered = isAnswered,
                            isCreator = isCreator
                        )

                        Spacer(Modifier.weight(1f))
                        AppButton(
                            labelResId = R.string.next,
                            onClick = {
                                viewModel.nextQuestion(game.id)
                            },
                            enabled = (firstPlayer.answerIsRight == true
                                    || secondPlayer.answerIsRight == true
                                    ) || (
                                    firstPlayer.answerIsRight == false
                                            && secondPlayer.answerIsRight == false
                                    )
                        )
                        Spacer(Modifier.height(27.dp))

                    }
                } else if (game.status == "FINISHED") {
                    val stringResId = if (isCreator) {
                        R.string.you
                    } else {
                        R.string.p2
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${stringResource(stringResId)} — winner",
                            fontSize = 30.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                        Spacer(Modifier.height(20.dp))
                        AppButton(
                            labelResId = R.string.to_the_main_page,
                            onClick = navigateToMainScreen,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            }
        }
    }
}