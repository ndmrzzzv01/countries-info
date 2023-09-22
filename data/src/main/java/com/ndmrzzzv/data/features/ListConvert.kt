package com.ndmrzzzv.data.features

import com.ndmrzzzv.domain.model.Country

fun List<com.ndmrzzzv.data.data.Country>.convert(): List<Country> {
    return this.map { country ->
        Country(
            country.name?.common,
            country.flags?.png,
            country.population,
            country.area,
            country.capital,
            country.name?.official,
            country.coatOfArms?.png,
            country.code,
//                country.languages,
            country.maps?.googleMaps,
            country.timezones,
            country.borders
        )
    }
}