package com.rami.weatherapp.data.remore

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
        addHeaders(requestBuilder)
        return chain.proceed(requestBuilder.build())
    }

    private fun addHeaders(requestBuilder: Request.Builder) {
        requestBuilder.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
        requestBuilder.addHeader(ACCEPT_ENCODING, CONTENT_TYPE_VALUE)
        requestBuilder.addHeader(ACCEPT, CONTENT_TYPE_VALUE)
        requestBuilder.addHeader(PLATFORM, ANDROID)
        requestBuilder.addHeader(OS_VERSION, Build.VERSION.SDK_INT.toString())
    }
}

private const val ACCEPT_LANGUAGE = "Accept-Language"
private const val ACCEPT_ENCODING = "Accept-Encoding"
private const val ACCEPT = "Accept"
private const val PLATFORM = "Platform"
private const val CONTENT_TYPE = "Content-Type"
private const val OS_VERSION = "OSVersion"
private const val CONTENT_TYPE_VALUE = "application/json"
private const val ANDROID = "android"