package com.radlance.languageapp.presentation.game

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsState()

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            gameState?.let { game ->

                DisposableEffect(Unit) {
                    onDispose { viewModel.cancelGame(game.id) }
                }

                if (game.firstPlayer == null || game.secondPlayer == null) {
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
                } else {
                    Text(
                        text = "Connected!",
                        fontSize = 30.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        modifier = Modifier.padding(horizontal = 30.dp)
                    )
                }
            }
        }
    }
}