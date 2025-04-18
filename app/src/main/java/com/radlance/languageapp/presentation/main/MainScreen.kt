package com.radlance.languageapp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.common.NoConnectionScreen
import com.radlance.languageapp.presentation.ui.theme.Blue
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.Green
import com.radlance.languageapp.presentation.ui.theme.MainPersonColor
import com.radlance.languageapp.presentation.ui.theme.NoImage
import com.radlance.languageapp.presentation.ui.theme.Orange
import com.radlance.languageapp.presentation.ui.theme.Red
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun MainScreen(
    navigateToProfile: () -> Unit,
    navigateToGuessTheAnimal: (Int) -> Unit,
    navigateToWordPractice: (Int) -> Unit,
    navigateToAudition: (Int) -> Unit,
    navigateToGame: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val mainContentUiState by viewModel.mainContentResultUiState.collectAsState()
    val context = LocalContext.current

    mainContentUiState.Show(
        onSuccess = { fetchContent ->
            Scaffold { contentPadding ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(bottom = contentPadding.calculateBottomPadding())
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DeepBlue)
                            .padding(start = 28.dp)
                    ) {

                        Spacer(Modifier.height(50.dp))

                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(NoImage)
                                .clickable { navigateToProfile() }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .crossfade(true)
                                    .data(fetchContent.currentUser.avatarBitmap)
                                    .build(),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                contentDescription = "user_avatar"
                            )
                        }

                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = "Hello, ${fetchContent.currentUser.firstName}",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W500,
                            lineHeight = 28.sp
                        )
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = stringResource(R.string.are_you_ready_for_learning_today),
                            fontSize = 17.sp,
                            color = MainPersonColor,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W500
                        )
                        Spacer(Modifier.height(11.dp))
                    }

                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        Spacer(Modifier.height(11.dp))
                        Text(
                            text = stringResource(R.string.top_users),
                            fontSize = 20.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W500,
                            lineHeight = 24.sp
                        )
                        Spacer(Modifier.height(5.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            fetchContent.leaderboard.sortedByDescending {
                                it.score
                            }.take(3).forEach { userScore ->
                                LeaderboardItem(userScore = userScore)
                            }
                        }

                        Spacer(Modifier.height(11.dp))
                        Text(
                            text = stringResource(R.string.available_exercises),
                            fontSize = 20.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W500,
                            lineHeight = 24.sp
                        )
                        Spacer(Modifier.height(9.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(count = 2),
                            verticalArrangement = Arrangement.spacedBy(17.dp),
                            horizontalArrangement = Arrangement.spacedBy(21.dp)
                        ) {
                            val colors = listOf(Blue, Red, Orange, Green)
                            val navigateActions = listOf(
                                navigateToGuessTheAnimal,
                                navigateToWordPractice,
                                navigateToAudition,
                                navigateToGame
                            )

                            itemsIndexed(
                                items = fetchContent.exercises,
                                key = { _, e -> e.id }
                            ) { index, exercise ->
                                ExerciseItem(
                                    exercise = exercise,
                                    backgroundColor = colors[index],
                                    modifier = Modifier.clickable {
                                        navigateActions[index].invoke(fetchContent.currentUser.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },

        onError = {
            NoConnectionScreen(onRetryClick = { viewModel.fetchMainContent() })
        },
        onLoading = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    )
}