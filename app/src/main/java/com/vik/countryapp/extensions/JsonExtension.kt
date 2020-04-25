package com.vik.countryapp.extensions

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

inline fun <reified T> String?.toObject(default: T? = null): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (e: Exception) {
        default
    }
}

inline fun <reified T> String.toTypeToken(): T? {
    return try {
        Gson().fromJson(this, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        null
    }
}

fun Any?.toJson(): String {
    this?.let {
        return Gson().toJson(this)
    } ?: kotlin.run { return "{}" }
}

fun String.asJson(): JsonObject? {
    try {
        return JsonParser().parse(this)?.asJsonObject
    } catch (e: Exception) {
        return null
    }
}

fun String.asJsonArray(): JsonArray? {
    return try {
        JsonParser().parse(this)?.asJsonArray
    } catch (e: Exception) {
        null
    }
}