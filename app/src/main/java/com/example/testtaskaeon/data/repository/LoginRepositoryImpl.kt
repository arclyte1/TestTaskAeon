package com.example.testtaskaeon.data.repository

import com.example.testtaskaeon.data.remote.PaymentApi
import com.example.testtaskaeon.data.remote.dto.LoginRequest
import com.example.testtaskaeon.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: PaymentApi
) : LoginRepository {

    // Локально не сохраняю
    private var token: String? = null

    override suspend fun login(login: String, password: String): Result<Unit> {
        val response = api.login(LoginRequest(login, password))
        return when(response.success) {
            "true" -> {
                response.response?.let {
                    token = it.token
                    Result.success(Unit)
                } ?: Result.failure(IllegalArgumentException("Invalid credentials"))
            }
            "false" -> {
                Result.failure(Exception(response.error?.errorMsg ?: "Unexpected error"))
            }
            else -> {
                response.response?.let {
                    token = it.token
                    Result.success(Unit)
                } ?: Result.failure(Exception(response.error?.errorMsg ?: "Unexpected error"))
            }
        }
    }

    override suspend fun getToken(): String? {
        return token
    }
}