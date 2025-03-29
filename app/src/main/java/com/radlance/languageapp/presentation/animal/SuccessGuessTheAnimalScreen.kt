package com.radlance.languageapp.presentation.animal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.presentation.component.AppButton
import com.radlance.languageapp.presentation.ui.theme.Green
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 29.03.2025
 * Автор: Манякин Дмитрий
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessGuessTheAnimalScreen(
    navigateToMainScreen: () -> Unit,
    navigateToNextQuestion: () -> Unit,
    modifier: Modifier = Modifier
) {
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

                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Green)
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Spacer(Modifier.height(80.dp))
            Image(
                painter = painterResource(R.drawable.success),
                contentDescription = "success",
                modifier = Modifier.size(160.dp)
            )
            Spacer(Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.holy_molly_that_is_right),
                fontSize = 20.sp,
                fontFamily = fredokaFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(42.dp))
            AppButton(
                labelResId = R.string.next,
                onClick = navigateToNextQuestion,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}