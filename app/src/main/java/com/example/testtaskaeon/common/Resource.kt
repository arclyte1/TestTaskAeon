package com.example.testtaskaeon.common

sealed class Resource<in T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Any>()
    data object Loading : Resource<Any>()
}