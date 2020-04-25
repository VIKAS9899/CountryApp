package com.vik.countryapp.network


sealed class Resource<T> {

    data class Loading<T>(val msg: String? = null) : Resource<T>()
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val error: Throwable? = null) : Resource<T>()
}