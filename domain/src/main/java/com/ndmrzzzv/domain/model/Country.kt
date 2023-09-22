package com.ndmrzzzv.domain.model

data class Country(
    val name: String?,
    val image: String?,
    val population: Int?,
    val surface: Double?,
    val capital: List<String>?,
    val officialName: String?,
    val coatOfArms: String?,
    val code: String?,
//    val languages: List<String>?,
    val googleMapLink: String?,
    val timeZone: List<String>?,
    val countriesNear: List<String>?
)
