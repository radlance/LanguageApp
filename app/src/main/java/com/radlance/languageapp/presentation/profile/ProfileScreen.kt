package com.radlance.languageapp.presentation.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.common.NoConnectionScreen
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.Green
import com.radlance.languageapp.presentation.ui.theme.ThemeViewModel
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun ProfileScreen(
    navigateToChooseLanguage: () -> Unit,
    navigateToResizePictureImage: () -> Unit,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val isDarkUserTheme by themeViewModel.isDarkUserTheme.collectAsState()
    val context = LocalContext.current
    val userDataUiState by profileViewModel.userDataUiState.collectAsState()

    val isDarkSystemTheme = isSystemInDarkTheme()
    var showPickImageTypeDialog by remember { mutableStateOf(false) }
    var currentImage by remember { mutableStateOf<Any?>(null) }
    val currentFile by profileViewModel.currentFile.collectAsState()

    LaunchedEffect(currentImage) {
        currentImage?.let {
            profileViewModel.selectImage(it)
            navigateToResizePictureImage()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            currentImage = it
        }
    }

    val previewLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        currentImage = bitmap
    }

    if (showPickImageTypeDialog) {
        Dialog(
            onDismissRequest = { showPickImageTypeDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            ChangeProfileImageDialog(
                onGalleryButtonClick = {
                    showPickImageTypeDialog = false
                    galleryLauncher.launch("image/*")
                },

                onPhotoButtonClick = {
                    previewLauncher.launch()
                    showPickImageTypeDialog = false

                },
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }

    userDataUiState.Show(
        onSuccess = { user ->
            Scaffold { contentPadding ->
                Column(
                    modifier = modifier
                        .padding(bottom = contentPadding.calculateBottomPadding())
                ) {

                    Column(
                        modifier = Modifier
                            .background(DeepBlue)
                            .fillMaxWidth()
                            .padding(start = 24.dp)
                    ) {
                        Spacer(Modifier.height(44.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(134.dp)
                                .clip(CircleShape)
                                .background(Green)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .crossfade(true)
                                    .data(currentFile ?: user.avatar)
                                    .build(),
                                contentDescription = "user_avatar",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
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
                        val themeLabelResId = isDarkUserTheme?.let {
                            if (it) R.string.switch_to_light else R.string.switch_to_dark
                        } ?: run {
                            if (isDarkSystemTheme) R.string.switch_to_light else R.string.switch_to_dark
                        }

                        AppButton(
                            labelResId = themeLabelResId,
                            onClick = {
                                themeViewModel.changeTheme(
                                    isDark = !(isDarkUserTheme ?: isDarkSystemTheme)
                                )
                            }
                        )

                        AppButton(
                            labelResId = R.string.change_mother_language,
                            onClick = { navigateToChooseLanguage() }
                        )

                        AppButton(
                            labelResId = R.string.change_your_image,
                            onClick = { showPickImageTypeDialog = true }
                        )

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
            NoConnectionScreen(onRetryClick = profileViewModel::loadUserData)
        },
        onLoading = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    )
}