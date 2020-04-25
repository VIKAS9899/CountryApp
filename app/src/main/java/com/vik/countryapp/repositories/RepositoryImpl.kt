package com.vik.countryapp.repositories

import com.vik.countryapp.models.Country
import com.vik.countryapp.network.NetworkProvider
import com.vik.countryapp.network.NetworkService

class RepositoryImpl(private val _networkService: NetworkService = NetworkProvider.networkService()) :
    Repository {

    override suspend fun fetchAllCountry(): ArrayList<Country> {
        return _networkService.fetchAllCountry()
    }


}