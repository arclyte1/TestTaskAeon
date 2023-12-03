package com.example.testtaskaeon.data.remote

import com.example.testtaskaeon.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class BaseHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("app-key", Constants.APP_KEY)
                .addHeader("v", Constants.REMOTE_API_VER)
                .build()
        )
    }
}