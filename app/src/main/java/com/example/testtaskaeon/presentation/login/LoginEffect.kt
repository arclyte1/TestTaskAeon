package com.example.testtaskaeon.presentation.login

sealed class LoginEffect {

    data class Error(val message: String) : LoginEffect()
    data object NavigateForward : LoginEffect()
}
