package com.example.testtaskaeon.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PaymentsResponse(
    val success: String? = null,
    val response: List<PaymentDto>? = null,
    val error: ErrorDto? = null,
)
