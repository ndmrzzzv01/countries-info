package com.ndmrzzzv.domain.usecase

import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.model.SortType

class SortAndFilterCountriesUseCase {

    operator fun invoke(allCountries: List<Country>,
                                sortType: SortType? = null,
                                searchRequest: String? = null
    ): List<Country> {
        var sortedList = when (sortType) {
            SortType.NAME_A_Z -> allCountries.sortedBy { it.name }
            SortType.NAME_Z_A -> allCountries.sortedByDescending { it.name }
            SortType.POPULATION_UP -> allCountries.sortedBy { it.population }
            SortType.POPULATION_DOWN -> allCountries.sortedByDescending { it.population }
            SortType.SURFACE_UP -> allCountries.sortedBy { it.surface }
            SortType.SURFACE_DOWN -> allCountries.sortedByDescending { it.surface }
            else -> allCountries
        }

        if (searchRequest?.isNotEmpty() == true) {
            sortedList = sortedList.filter {
                it.name?.lowercase()?.contains(searchRequest.lowercase()) == true
            }
        }

        return sortedList
    }


}