package com.ndmrzzzv.countriesinfo.screens.main.state

import com.ndmrzzzv.countriesinfo.screens.main.data.SortType

data class CountriesScreenAction(
    val onItemClick: (code: String) -> Unit = {},
    val searchEvent: (searchString: String) -> Unit = {},
    val sortEvent: (type: SortType) -> Unit = {},
    val getAllCountriesEvent: () -> Unit = {},
)