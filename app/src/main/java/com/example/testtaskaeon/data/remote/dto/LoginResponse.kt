package com.example.testtaskaeon.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: String? = null,
    val response: TokenDto? = null,
    val error: ErrorDto? = null,
)
