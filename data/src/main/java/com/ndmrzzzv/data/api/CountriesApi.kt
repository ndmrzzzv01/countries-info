package com.ndmrzzzv.data.api

import com.ndmrzzzv.data.data.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {

    @GET("all")
    suspend fun getAllCountries(): List<Country>

    @GET("alpha/{code}")
    suspend fun searchCountryByCode(@Path("code") code: String): Country

}