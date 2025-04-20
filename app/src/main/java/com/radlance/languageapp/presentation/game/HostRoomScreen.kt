package com.radlance.languageapp.presentation.game

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostRoomScreen(
    navigateToGameScreen: (Boolean) -> Unit,
    navigateToMainScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val createGameUiState by viewModel.createGameUiState.collectAsState()
    val connectGameUiState by viewModel.connectGameUiState.collectAsState()
    val connectionUiState by viewModel.connectionStatusUiState.collectAsState()

    Box {
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    AppButton(
                        labelResId = R.string.create_game,
                        onClick = viewModel::createGame
                    )

                    AppButton(
                        labelResId = R.string.join_to_game,
                        onClick = viewModel::connectToGame
                    )
                }
            }
        }
        createGameUiState.Show(
            onSuccess = { game ->
                connectionUiState.apply(game.id, viewModel)
                navigateToGameScreen(true)
                viewModel.resetStates()
            },
            onError = {
                NoConnectionScreen(onRetryClick = viewModel::createGame)
            },

            onLoading = {}
        )

        connectGameUiState.Show(
            onSuccess = { game ->
                connectionUiState.apply(game.id, viewModel)
                navigateToGameScreen(false)
                viewModel.resetStates()
            },
            onError = {
                LaunchedEffect(connectionUiState) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_available_rooms),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            onLoading = {}
        )
    }
}