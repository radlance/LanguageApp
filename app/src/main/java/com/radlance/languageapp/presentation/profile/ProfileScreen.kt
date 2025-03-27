package com.radlance.languageapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
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
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.Green
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userDataUiState by viewModel.userDataUiState.collectAsState()


    userDataUiState.Show(
        onSuccess = { user ->
            Scaffold { contentPadding ->
                Column(modifier = modifier.padding(bottom = contentPadding.calculateBottomPadding())) {

                    Column(
                        modifier = Modifier
                            .background(DeepBlue)
                            .fillMaxWidth()
                            .padding(start = 24.dp)
                    ) {
                        Spacer(Modifier.height(44.dp))
                        Box(
                            modifier = Modifier
                                .size(134.dp)
                                .clip(CircleShape)
                                .background(Green)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .crossfade(true)
                                    .data(user.avatar)
                                    .build(),
                                contentDescription = "user_avatar"
                            )
                        }
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = "You profile, ${user.firstName}",
                            fontSize = 22.sp,
                            fontFamily = fredokaFamily,
                            fontWeight = FontWeight.W500,
                            lineHeight = 28.sp,
                            color = Color.White
                        )
                        Spacer(Modifier.height(20.dp))
                    }
                    Spacer(Modifier.weight(1f))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        AppButton(labelResId = R.string.switch_to_dark, onClick = {})
                        AppButton(
                            labelResId = R.string.change_mother_language,
                            onClick = {})
                        AppButton(labelResId = R.string.change_your_image, onClick = {})
                        AppButton(
                            labelResId = R.string.logout,
                            onClick = {},
                            enabled = false
                        )
                        Spacer(modifier = Modifier.height(26.dp))
                    }
                }
            }
        },
        onError = {
            NoConnectionScreen(onRetryClick = viewModel::loadUserData)
        },
        onLoading = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    )
}