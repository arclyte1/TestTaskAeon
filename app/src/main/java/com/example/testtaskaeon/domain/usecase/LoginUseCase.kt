package com.example.testtaskaeon.domain.usecase

import com.example.testtaskaeon.common.Resource
import com.example.testtaskaeon.domain.repository.LoginRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    operator fun invoke(login: String, password: String) = flow {
        try {
            emit(Resource.Loading)
            val result = repository.login(login, password)
            if (result.isSuccess) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error(result.exceptionOrNull()?.message ?: "Unexpected error"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}