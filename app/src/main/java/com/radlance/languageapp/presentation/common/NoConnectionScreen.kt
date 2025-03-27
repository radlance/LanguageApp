package com.radlance.languageapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.LanguageAppTheme
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoConnectionScreen(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Scaffold(topBar = {
        TopAppBar(
            title = {},
            colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = DeepBlue),
            modifier = Modifier
                .fillMaxWidth()
        )
    }) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.no_connection_emoji),
                contentDescription = "no_connection_emoji",
                modifier = Modifier.size(150.dp)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.no_internet_connection),
                fontSize = 30.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(Modifier.weight(1f))

            AppButton(
                labelResId = R.string.check_again,
                onClick = onRetryClick,
                modifier = Modifier.padding(horizontal = 21.dp)
            )

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Preview
@Composable
private fun NoConnectionScreenPreview() {
    LanguageAppTheme {
        NoConnectionScreen(onRetryClick = {})
    }
}