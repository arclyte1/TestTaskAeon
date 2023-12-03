package com.example.testtaskaeon.di

import com.example.testtaskaeon.common.Constants
import com.example.testtaskaeon.data.remote.BaseHeaderInterceptor
import com.example.testtaskaeon.data.remote.PaymentApi
import com.example.testtaskaeon.data.repository.LoginRepositoryImpl
import com.example.testtaskaeon.data.repository.PaymentsRepositoryImpl
import com.example.testtaskaeon.domain.repository.LoginRepository
import com.example.testtaskaeon.domain.repository.PaymentsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(BaseHeaderInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.REMOTE_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    @Singleton
    fun providePaymentApi(retrofit: Retrofit): PaymentApi {
        return retrofit.create(PaymentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: PaymentApi): LoginRepository = LoginRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePaymentsRepository(api: PaymentApi, loginRepository: LoginRepository): PaymentsRepository =
        PaymentsRepositoryImpl(api, loginRepository)
}