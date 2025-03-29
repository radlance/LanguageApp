package com.radlance.languageapp.presentation.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.DeepBlue
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.game),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 28.sp,
                        fontFamily = fredokaFamily,
                        color = Color.White
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
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                AppButton(
                    labelResId = R.string.create_game,
                    onClick = {
                        viewModel.createGame()
                    }
                )

                AppButton(
                    labelResId = R.string.join_to_game,
                    onClick = {}
                )
            }
        }
    }
}