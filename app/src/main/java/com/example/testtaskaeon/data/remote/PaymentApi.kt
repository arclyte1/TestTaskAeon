package com.example.testtaskaeon.data.remote

import com.example.testtaskaeon.data.remote.dto.LoginRequest
import com.example.testtaskaeon.data.remote.dto.LoginResponse
import com.example.testtaskaeon.data.remote.dto.PaymentsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PaymentApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ) : LoginResponse

    @GET("payments")
    suspend fun getPayments(
        @Header("token") token: String
    ) : PaymentsResponse
}