package com.radlance.languageapp.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun ChangeProfileImageDialog(
    onPhotoButtonClick: () -> Unit,
    onGalleryButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        val scrollState = rememberScrollState()

        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = stringResource(R.string.pick_image),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 28.sp
                )

                Spacer(Modifier.height(10.dp))

                Column {
                    AppButton(
                        labelResId = R.string.camera,
                        onClick = onPhotoButtonClick
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AppButton(
                        labelResId = R.string.gallery,
                        onClick = onGalleryButtonClick
                    )
                }
            }
        }
    }
}