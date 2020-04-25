package com.vik.countryapp.repositories

import com.vik.countryapp.models.Country


interface Repository {

    suspend fun fetchAllCountry():ArrayList<Country>
}