package com.kotlin.base.data.net

import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Create by Pidan
 */
object RetrofitFactory {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BaseConstant.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(initClient())
            .build()
    }
    private val interceptor by lazy {
        Interceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "utf-8")
                .addHeader("token", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                .build()
            it.proceed(request)
        }
    }

    private fun initClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(initLogInterceptor())
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service: Class<T>) = retrofit.create(service)
}