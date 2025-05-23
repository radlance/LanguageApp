package com.radlance.languageapp.data.api.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.radlance.languageapp.data.api.dto.AnimalDto
import com.radlance.languageapp.data.api.dto.ExerciseDto
import com.radlance.languageapp.data.api.dto.GameDataDto
import com.radlance.languageapp.data.api.dto.GameDto
import com.radlance.languageapp.data.api.dto.PlayerDto
import com.radlance.languageapp.data.api.dto.QuestionDto
import com.radlance.languageapp.data.api.dto.SignInUser
import com.radlance.languageapp.data.api.dto.SignUpUser
import com.radlance.languageapp.data.api.dto.UserResponse
import com.radlance.languageapp.data.api.dto.UserScoreDto
import com.radlance.languageapp.domain.animal.Animal
import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.domain.game.ConnectionStatus
import com.radlance.languageapp.domain.game.Game
import com.radlance.languageapp.domain.game.GameData
import com.radlance.languageapp.domain.game.Player
import com.radlance.languageapp.domain.game.Question
import com.radlance.languageapp.domain.main.Exercise
import com.radlance.languageapp.domain.main.UserScore
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.io.File

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

abstract class RemoteMapper {

    protected fun User.toSignInUser(): SignInUser {
        return SignInUser(email = email, password = password)
    }

    protected fun User.toSignUpUser(): SignUpUser {
        return SignUpUser(
            email = email,
            firstName = firstName,
            lastName = lastName,
            password = password
        )
    }

    protected fun UserScoreDto.toUserScore(responseBody: ResponseBody?): UserScore {
        return UserScore(
            user = User(
                firstName = firstName,
                lastName = lastName,
                id = userId,
                avatarBitmap = responseBody?.toBitmap()
            ),
            score = score
        )
    }

    protected fun UserResponse.toUser(responseBody: ResponseBody?): User {
        return User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            id = id,
            avatarBitmap = responseBody?.toBitmap()
        )
    }

    protected fun ExerciseDto.toExercise(): Exercise {
        return Exercise(id = id, name = name, image = image ?: "")
    }

    private fun ResponseBody.toBitmap(): Bitmap {
        return BitmapFactory.decodeStream(byteStream())
    }

    protected fun File.toMultipartBodyPart(): MultipartBody.Part {
        return MultipartBody.Part.createFormData("file", this.name, this.asRequestBody())
    }

    protected fun GameDto.toGame(): Game {
        return Game(
            id = id,
            firstPlayer = firstPlayer?.toPlayer(),
            secondPlayer = secondPlayer?.toPlayer(),
            status = status,
            gameData = gameData.toGameData(),
            winnerPlayer = winnerPlayer,
            currentQuestion = currentQuestion,
            questionIsFinished = questionIsFinished
        )
    }

    protected fun PlayerDto.toPlayer(): Player {
        return Player(
            id = id,
            email = email,
            score = score,
            selectedAnswer = selectedAnswer,
            answerIsRight = answerIsRight
        )
    }

    protected fun GameDataDto.toGameData(): GameData {
        return GameData(questions = questions.map { it.toQuestion() })
    }

    protected fun QuestionDto.toQuestion(): Question {
        return Question(
            wordId = wordId,
            word = word,
            transcription = transcription,
            answers = answers,
            correctAnswerNumber = correctAnswerNumber
        )
    }

    protected fun AnimalDto.toAnimal(responseBody: ResponseBody?): Animal {
        return Animal(
            id = id,
            name = name,
            image = responseBody?.toBitmap()
        )
    }

    protected fun LifecycleEvent.toConnectionState(): ConnectionStatus {
        return when (type) {
            LifecycleEvent.Type.OPENED -> ConnectionStatus.Connected
            LifecycleEvent.Type.CLOSED -> ConnectionStatus.Disconnected
            LifecycleEvent.Type.ERROR -> ConnectionStatus.Error
            else -> ConnectionStatus.Disconnected
        }
    }
}