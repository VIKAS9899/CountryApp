package com.vik.countryapp.models

import com.google.gson.annotations.SerializedName


data class FlagObject(
    @SerializedName("code")
    val code: String?,
    @SerializedName("emoji")
    val emoji: String?,
    @SerializedName("unicode")
    val unicode: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("title")
    val title: String?
)
