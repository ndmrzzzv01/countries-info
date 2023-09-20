package com.ndmrzzzv.data.module

import com.ndmrzzzv.data.api.CountriesApi
import com.ndmrzzzv.data.repository.CountriesRepositoryImpl
import com.ndmrzzzv.domain.repository.CountriesRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://restcountries.com/v3.1/"

val dataModule = module {

    factory<CountriesRepository> { CountriesRepositoryImpl(get()) }

    single<CountriesApi> {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@single retrofit.create(CountriesApi::class.java)
    }

}