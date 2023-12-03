package com.example.testtaskaeon.data.remote.dto

import com.example.testtaskaeon.domain.model.Payment
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId

@Serializable
data class PaymentDto(
    val id: Long,
    val title: String? = null,
    val amount: String? = null,
    val created: Long? = null
) {

    fun toPayment() = Payment(
        id = id,
        title = title ?: "",
        amount = amount,
        created = created?.let {
            Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
    )
}
