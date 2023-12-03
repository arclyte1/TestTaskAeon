package com.example.testtaskaeon.domain.repository

import com.example.testtaskaeon.domain.model.Payment

interface PaymentsRepository {

    suspend fun getPaymentsList(): Result<List<Payment>>
}