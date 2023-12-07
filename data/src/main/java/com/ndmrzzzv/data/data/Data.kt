package com.ndmrzzzv.data.data

import com.google.gson.annotations.SerializedName
import com.ndmrzzzv.domain.model.Country

data class Country(
    @SerializedName("name") val name: Name?,
    @SerializedName("flags") val flags: Flag?,
    @SerializedName("population") val population: Int?,
    @SerializedName("area") val area: Double?,
    @SerializedName("capital") val capital: List<String>?,
    @SerializedName("coatOfArms") val coatOfArms: CoatOfArms?,
    @SerializedName("cca3") val code: String,
    @SerializedName("languages") val languages: Map<String, String>?,
    @SerializedName("maps") val maps: Maps?,
    @SerializedName("timezones") val timezones: List<String>?,
    @SerializedName("borders") val borders: List<String>?
) {
    fun convert(): Country {
        val country = this
        return Country(
            country.name?.common,
            country.flags?.png,
            country.population,
            country.area,
            country.capital,
            country.name?.official,
            country.coatOfArms?.png,
            country.code,
            country.languages,
            country.maps?.googleMaps,
            country.timezones,
            country.borders
        )
    }
}

data class Name(
    @SerializedName("common") val common: String,
    @SerializedName("official") val official: String,
)

data class Flag(
    @SerializedName("png") val png: String
)

data class CoatOfArms(
    @SerializedName("png") val png: String
)

data class Maps(
    @SerializedName("googleMaps") val googleMaps: String,
)