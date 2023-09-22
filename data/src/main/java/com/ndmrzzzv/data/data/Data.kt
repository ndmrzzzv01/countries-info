package com.ndmrzzzv.data.data

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: Name?,
    @SerializedName("flags") val flags: Flag?,
    @SerializedName("population") val population: Int?,
    @SerializedName("area") val area: Double?,
    @SerializedName("capital") val capital: List<String>?,
    @SerializedName("coatOfArms") val coatOfArms: CoatOfArms?,
    @SerializedName("cios") val code: String?,
//    @SerializedName("languages") val languages: List<String>?,
    @SerializedName("maps") val maps: Maps?,
    @SerializedName("timezones") val timezones: List<String>?,
    @SerializedName("borders") val borders: List<String>?
)

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