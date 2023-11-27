package com.ndmrzzzv.countriesinfo.screens.detail.state

data class CountryDetailScreenAction(
    val openGoogleMap: (url: String?) -> Unit = {},
    val onCodeClick: (code: String?) -> Unit = {},
    val loadCountryAgainEvent: () -> Unit = {}
)