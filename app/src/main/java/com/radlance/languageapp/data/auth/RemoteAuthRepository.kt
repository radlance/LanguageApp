package com.radlance.languageapp.data.auth

import com.radlance.languageapp.R
import com.radlance.languageapp.common.ResourceProvider
import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.core.RemoteMapper
import com.radlance.languageapp.data.core.DataStoreManager
import com.radlance.languageapp.domain.auth.AuthRepository
import com.radlance.languageapp.domain.auth.AuthResult
import com.radlance.languageapp.domain.auth.User
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class RemoteAuthRepository @Inject constructor(
    private val service: AppService,
    private val dataStoreManager: DataStoreManager,
    private val resourceProvider: ResourceProvider
) : AuthRepository, RemoteMapper() {

    override suspend fun signIn(user: User): AuthResult {
        return try {
            val userResponse = service.login(user.toSignInUser())
            dataStoreManager.saveJwt(userResponse.token)

            AuthResult.Success
        } catch (e: HttpException) {

            AuthResult.Error(resourceProvider.getString(R.string.error))

        } catch (e: Exception) {
            AuthResult.Error(resourceProvider.getString(R.string.error))
        }
    }

    override suspend fun signUp(user: User): AuthResult {
        return try {
            val userResponse = service.register(user.toSignUpUser())
            dataStoreManager.saveJwt(userResponse.token)

            AuthResult.Success
        } catch (e: HttpException) {
            AuthResult.Error(resourceProvider.getString(R.string.error))

        } catch (e: Exception) {
            AuthResult.Error(resourceProvider.getString(R.string.error))
        }
    }
}