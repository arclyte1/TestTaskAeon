package com.example.testtaskaeon.presentation.payments_list

sealed class PaymentsListEffect {

    data class Error(val message: String) : PaymentsListEffect()
}
