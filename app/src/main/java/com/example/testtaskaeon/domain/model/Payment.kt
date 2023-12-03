package com.example.testtaskaeon.domain.model

import java.time.LocalDateTime

data class Payment(
    val id: Long,
    val title: String,
    val amount: String?,
    val created: LocalDateTime?,
)
