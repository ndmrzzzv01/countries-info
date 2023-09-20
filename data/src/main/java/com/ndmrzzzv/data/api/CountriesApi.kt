package com.ndmrzzzv.data.api

import com.ndmrzzzv.data.data.Country
import retrofit2.http.GET

interface CountriesApi {

    @GET("all")
    suspend fun getAllCountries(): List<Country>

}