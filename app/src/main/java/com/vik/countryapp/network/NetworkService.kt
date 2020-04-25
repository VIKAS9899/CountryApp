package com.vik.countryapp.network

import com.vik.countryapp.models.Country
import retrofit2.http.GET

interface NetworkService {

    @GET("rest/v2/all ")
    suspend fun fetchAllCountry(): ArrayList<Country>
}