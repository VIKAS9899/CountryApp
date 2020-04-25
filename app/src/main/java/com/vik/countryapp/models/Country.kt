package com.vik.countryapp.models

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: String?,
    @SerializedName("alpha2Code")
    val alpha2Code: String?,
    @SerializedName("alpha3Code")
    val alpha3Code: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("flag")
    val flag: String?,
    @SerializedName("currencies")
    val currencies: ArrayList<Currency?>?
)

data class Currency(
    @SerializedName("code")
    val code: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?
)