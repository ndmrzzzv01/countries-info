package com.ndmrzzzv.countriesinfo.fragment.main.data

import com.ndmrzzzv.countriesinfo.R

object MapForSorting {

    fun get(): Map<Int, SortType> {
        return mapOf(
            R.id.sort_a_z to SortType.NAME_A_Z,
            R.id.sort_z_a to SortType.NAME_Z_A,
            R.id.population_up to SortType.POPULATION_UP,
            R.id.population_down to SortType.POPULATION_DOWN,
            R.id.surface_up to SortType.SURFACE_UP,
            R.id.surface_down to SortType.SURFACE_DOWN
        )
    }

}