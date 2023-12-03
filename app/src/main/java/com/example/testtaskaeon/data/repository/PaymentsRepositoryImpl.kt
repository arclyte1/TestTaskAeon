package com.example.testtaskaeon.data.repository

import com.example.testtaskaeon.data.remote.PaymentApi
import com.example.testtaskaeon.domain.model.Payment
import com.example.testtaskaeon.domain.repository.LoginRepository
import com.example.testtaskaeon.domain.repository.PaymentsRepository
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val api: PaymentApi,
    private val loginRepository: LoginRepository
) : PaymentsRepository {

    override suspend fun getPaymentsList(): Result<List<Payment>> {
        return loginRepository.getToken()?.let { token ->
            val response = api.getPayments(token)
            return when(response.success) {
                "true" -> {
                    response.response?.let { list ->
                        Result.success(list.map { it.toPayment() })
                    } ?: Result.failure(IllegalArgumentException("Invalid credentials"))
                }
                "false" -> {
                    Result.failure(Exception(response.error?.errorMsg ?: "Unexpected error"))
                }
                else -> {
                    response.response?.let { list ->
                        Result.success(list.map { it.toPayment() })
                    } ?: Result.failure(Exception(response.error?.errorMsg ?: "Unexpected error"))
                }
            }
        } ?: Result.failure(Exception("User not logged in"))
    }
}