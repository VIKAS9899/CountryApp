package com.vik.countryapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vik.countryapp.models.Country
import com.vik.countryapp.models.FlagObject
import com.vik.countryapp.network.Resource
import com.vik.countryapp.repositories.Repository
import com.vik.countryapp.repositories.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.vik.countryapp.extensions.asJson
import com.vik.countryapp.extensions.asJsonArray
import com.vik.countryapp.extensions.toTypeToken
import com.vik.countryapp.isNetworkAvailable
import java.io.IOException
import java.net.ConnectException


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val flagMap = hashMapOf<String, FlagObject>()
    private val _repo: Repository = RepositoryImpl()
    private val _countries = arrayListOf<Country>()
    var query: String? = null

    //Live Data
    val fetchCountryLiveData by lazy { MutableLiveData<Resource<ArrayList<Country>>>() }

    init {
        if (application.isNetworkAvailable()) {
            fetchCountryList()
        } else {
            fetchCountryLiveData.value = Resource.Error(ConnectException("Connection error!"))
        }
    }

    fun fetchCountryList() {

        viewModelScope.launch {
            fetchCountryLiveData.value = Resource.Loading()
            try {
                if (flagMap.isEmpty())
                    parseFlagJson()
                if (!getApplication<Application>().isNetworkAvailable()) {
                    throw ConnectException("Connection error!")
                }
                val response = _repo.fetchAllCountry()
                _countries.addAll(response)
                fetchCountryLiveData.value = Resource.Success(data = _countries)
            } catch (e: Exception) {
                fetchCountryLiveData.value = Resource.Error(e)
            }
        }
    }

    fun searchText(text: String?) {
        viewModelScope.launch {
            search(text)
        }
    }


    private suspend fun search(text: String?) = withContext(Dispatchers.Default) {
        query = text
        if (query.isNullOrEmpty()) {
            fetchCountryLiveData.postValue(Resource.Success(data = _countries))
        } else if (query?.length == 2) {
            val newList = _countries.filter { it.alpha2Code?.toLowerCase() == query?.toLowerCase() }
            fetchCountryLiveData.postValue(Resource.Success(data = ArrayList(newList)))
        }

    }

    private suspend fun parseFlagJson() = withContext(Dispatchers.Default) {
        try {
            val json =
                getApplication<Application>().assets.open("CountryFlag.json").bufferedReader()
                    .use { it.readText() }
            val countryList = json.asJsonArray()?.toString()?.toTypeToken<ArrayList<FlagObject>>()
            countryList?.forEach { flagMap[it.code ?: ""] = it }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}