package com.radlance.languageapp.presentation.animal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.component.EnterInputField
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.NoImage
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 29.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalQuestionScreen(
    navigateToMainScreen: () -> Unit,
    navigateToSuccessScreen: () -> Unit,
    navigateToErrorScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnimalViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val imageClassifier = remember { ImageClassifierHelper(context) }

    val loadAnimalsResultUiState by viewModel.loadAnimalResultUiState.collectAsState()
    val incrementScoreResultUiState by viewModel.incrementResultUiState.collectAsState()

    val selectedAnimal by viewModel.selectedAnimal.collectAsState()

    var answerFieldValue by rememberSaveable { mutableStateOf("") }

    Box {
        loadAnimalsResultUiState.Show(
            onSuccess = { animals ->
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(R.string.guess_the_animal),
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
                        modifier = modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                            .padding(horizontal = 23.dp)
                    ) {
                        Spacer(Modifier.height(17.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(NoImage)
                        ) {
                            selectedAnimal?.let { randomAnimal ->
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .crossfade(true)
                                        .data(randomAnimal.image)
                                        .build(),
                                    contentDescription = "animal",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } ?: run { viewModel.selectRandomAnimal(animals) }
                        }

                        Spacer(Modifier.height(17.dp))

                        Text(
                            text = stringResource(R.string.write_who_is_on_image),
                            fontSize = 15.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W400,
                            lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        EnterInputField(
                            value = answerFieldValue,
                            onValueChange = { answerFieldValue = it },
                            labelResId = R.string.empty,
                            hintResId = R.string.empty
                        )

                        Spacer(Modifier.height(17.dp))

                        AppButton(
                            labelResId = R.string.check,
                            onClick = {
                                selectedAnimal?.let { randomAnimal ->
                                    val result = imageClassifier.classify(randomAnimal.image!!)

                                    if (result.trim().lowercase() == answerFieldValue.trim().lowercase()) {
                                        viewModel.updateCurrentStreak()
                                        viewModel.incrementUserScore()
                                    } else {
                                        viewModel.resetStreak()
                                        navigateToErrorScreen(randomAnimal.name)
                                    }
                                }
                            }
                        )
                    }
                }
            },

            onError = {
                NoConnectionScreen(onRetryClick = viewModel::loadAnimals)
            },

            onLoading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        )

        incrementScoreResultUiState.Show(
            onSuccess = { navigateToSuccessScreen() },
            onError = {
                NoConnectionScreen(onRetryClick = viewModel::incrementUserScore)
            },
            onLoading = {}
        )
    }
}