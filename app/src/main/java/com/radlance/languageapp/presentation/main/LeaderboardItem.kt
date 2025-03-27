package com.radlance.languageapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.languageapp.R
import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.domain.main.UserScore
import com.radlance.languageapp.presentation.ui.theme.LanguageAppTheme
import com.radlance.languageapp.presentation.ui.theme.Leaderboard
import com.radlance.languageapp.presentation.ui.theme.fredokaFamily

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Composable
fun LeaderboardItem(
    userScore: UserScore,
    modifier: Modifier = Modifier
) {
//    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Leaderboard)
    ) {
        Spacer(Modifier.width(17.dp))
        Box(modifier = Modifier.size(36.dp)) {
//            AsyncImage(
//                model = ImageRequest.Builder(context)
//                    .crossfade(true)
//                    .data(userScore.user.avatar)
//                    .build(),
//                contentDescription = "leader_image"
//            )
            Image(
                painter = painterResource(R.drawable.person_example),
                contentDescription = "leader_image"
            )
        }

        Spacer(Modifier.width(24.dp))
        Text(
            text = "${userScore.user.firstName} ${userScore.user.lastName}",
            fontSize = 17.sp,
            fontFamily = fredokaFamily,
            fontWeight = FontWeight.W500,
            lineHeight = 22.sp
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "${userScore.score} points",
            fontSize = 17.sp,
            fontFamily = fredokaFamily,
            fontWeight = FontWeight.W500,
            lineHeight = 22.sp
        )

        Spacer(Modifier.width(13.dp))
    }
}

@Preview
@Composable
private fun LeaderboardItemPreview() {
    LanguageAppTheme {
        LeaderboardItem(
            userScore = UserScore(
                user = User(
                    firstName = "Vincent",
                    lastName = "van Gogh"
                ),
                score = 12
            )
        )
    }
}