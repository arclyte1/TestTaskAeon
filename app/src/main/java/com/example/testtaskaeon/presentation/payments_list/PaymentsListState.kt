package com.example.testtaskaeon.presentation.payments_list

import com.example.testtaskaeon.domain.model.Payment

data class PaymentsListState(
    val paymentList: List<Payment> = emptyList(),
    val isLoading: Boolean = false,
)
