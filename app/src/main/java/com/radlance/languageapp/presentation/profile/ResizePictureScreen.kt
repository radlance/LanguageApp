package com.radlance.languageapp.presentation.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily
import java.io.File

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResizePictureScreen(
    navigateUp: () -> Unit,
    image: Any,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(image)

    val uploadImageResultUiState by profileViewModel.updateUserProfileUiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose { profileViewModel.resetUploadImageResultUiState() }
    }

    uploadImageResultUiState.Show(
        onSuccess = {
            navigateUp()
        },
        onError = {
            Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show()
        },
        onLoading = {}
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.your_photo_is_gorgeous),
                        fontSize = 22.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W500,
                        lineHeight = 22.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = DeepBlue)
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(Color.Black)
        ) {
            Spacer(Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.just_resize_that_photo),
                fontSize = 22.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 22.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "selected_image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 253.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.weight(1f))

            AppButton(
                labelResId = R.string.use_that_image,
                onClick = {
                    image.toFile(context)?.let { profileViewModel.saveFile(it) }
                },
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(28.dp))
        }
    }
}

private fun Any.toFile(context: Context): File? {
    val cacheDir = context.cacheDir
    val file = File(cacheDir, "resized_image_${System.currentTimeMillis()}.jpg")

    return when (this) {
        is Uri -> {
            context.contentResolver.openInputStream(this)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                file
            }
        }

        is Bitmap -> {
            file.outputStream().use { outputStream ->
                compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            file
        }

        else -> null
    }
}