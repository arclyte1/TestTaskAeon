package com.example.testtaskaeon.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    @SerialName("error_code") val errorCode: Int,
    @SerialName("error_msg") val errorMsg: String,
)
