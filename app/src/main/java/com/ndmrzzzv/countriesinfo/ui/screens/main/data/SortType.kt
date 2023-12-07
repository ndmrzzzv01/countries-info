package com.ndmrzzzv.countriesinfo.ui.screens.main.data

enum class SortType {

    NAME_A_Z, NAME_Z_A, POPULATION_UP, POPULATION_DOWN, SURFACE_UP, SURFACE_DOWN

}

object SortTypes {

    fun get(): Map<String, SortType> {
        return mapOf(
            "Sort by name (A - Z)" to SortType.NAME_A_Z,
            "Sort by name (Z - A)" to SortType.NAME_Z_A,
            "Sort by population (low to high)" to SortType.POPULATION_UP,
            "Sort by population (high to low)" to SortType.POPULATION_DOWN,
            "Sort by surface (low to high)" to SortType.SURFACE_UP,
            "Sort by surface (high to low)" to SortType.SURFACE_DOWN
        )
    }

}