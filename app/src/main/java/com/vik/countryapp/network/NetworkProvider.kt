package com.vik.countryapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vik.countryapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkProvider {
    companion object {

        private val sInstance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            val retrofit = createRetrofit()
            retrofit.create(NetworkService::class.java)
        }

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://restcountries.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.Companion.invoke())
                .client(provideOkHttpClient())
                .build()
        }

        private fun provideOkHttpClient(): OkHttpClient {

            val builder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }
            return builder.build()
        }

        fun networkService() = sInstance
    }
}