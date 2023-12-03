package com.example.testtaskaeon.domain.repository

interface LoginRepository {

    suspend fun login(login: String, password: String): Result<Unit>

    suspend fun getToken(): String?
}