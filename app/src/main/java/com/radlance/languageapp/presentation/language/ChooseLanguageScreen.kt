package com.radlance.languageapp.presentation.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseLanguageScreen(
    navigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LanguageViewModel = hiltViewModel()
) {
    val languages by viewModel.languages.collectAsState()
    val configuration = LocalConfiguration.current

    var selectedLanguageId by rememberSaveable { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.language_select),
                        color = Color.White,
                        fontSize = 17.sp,
                        fontFamily = fredokaFamily,
                        fontWeight = FontWeight.W500,
                        lineHeight = 22.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.what_is_your_mother_language),
                fontSize = 22.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                items(items = languages, key = { language -> language.id }) { language ->
                    LanguageSelectItem(
                        languageItem = language,
                        selected = selectedLanguageId == language.id,
                        onClick = { selectedLanguageId = it },
                        enabled = language.code == configuration.locales[0].language || language.code == "en"
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            AppButton(
                labelResId = R.string.choose,
                onClick = navigateToSignIn,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(26.dp))
        }
    }
}