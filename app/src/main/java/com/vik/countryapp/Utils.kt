package com.vik.countryapp

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable(): Boolean {
    val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return manager.activeNetworkInfo?.isConnected ?: false
}