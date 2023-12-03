package com.example.testtaskaeon.domain.usecase

import com.example.testtaskaeon.common.Resource
import com.example.testtaskaeon.domain.repository.PaymentsRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetPaymentsListUseCase @Inject constructor(
    private val repository: PaymentsRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading)
            val result = repository.getPaymentsList()
            if (result.isSuccess) {
                emit(Resource.Success(result.getOrNull()))
            } else {
                emit(Resource.Error(result.exceptionOrNull()?.message ?: "Unexpected error"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}