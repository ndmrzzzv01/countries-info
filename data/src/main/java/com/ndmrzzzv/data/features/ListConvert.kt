package com.ndmrzzzv.data.features

import com.ndmrzzzv.domain.model.Country

fun com.ndmrzzzv.data.data.Country.convert(): Country {
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