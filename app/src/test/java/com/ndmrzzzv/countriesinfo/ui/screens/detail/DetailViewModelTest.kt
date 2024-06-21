package com.ndmrzzzv.countriesinfo.ui.screens.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.countriesinfo.ui.screens.detail.state.CountryDetailState
import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var searchCountriesByCodeUseCase: SearchCountriesByCodeUseCase
    private lateinit var internetChecker: InternetChecker
    private lateinit var stateHandle: SavedStateHandle
    private lateinit var viewModel: DetailViewModel
    private lateinit var context: Context
    private lateinit var mockStaticUri: MockedStatic<Uri>

    @SuppressLint("CheckResult")
    @Before
    fun setup() {
        mockStaticUri = mockStatic(Uri::class.java)
        context = mock<Context>()
        searchCountriesByCodeUseCase = mock()
        internetChecker = mock()
        stateHandle = SavedStateHandle(mapOf("country_code" to "Test"))
        viewModel = DetailViewModel(
            searchCountriesByCodeUseCase, internetChecker, stateHandle, dispatcher
        )
    }

    @After
    fun tearDown() {
        mockStaticUri.close()
    }

    @Test
    fun initialState_isProduced() {
        val state = viewModel.country.value
        Assert.assertEquals(CountryDetailState.Loading, state)
    }

    @Test
    fun getCountryByCode_noInternet() = scope.runTest {
        `when`(internetChecker.checkConnection()).thenReturn(false)

        viewModel.loadInfoAboutCountry()

        val state = viewModel.country.value
        val failedState = state as? CountryDetailState.LoadingFailed

        Assert.assertNotNull(failedState)
        Assert.assertEquals("You don`t have Internet connection", failedState?.message)
        verify(searchCountriesByCodeUseCase, never()).invoke("Test")
    }

    @Test
    fun getCountryByCode_success() = scope.runTest {
        val code = "Test"
        val country = CountriesForTesting.countriesFilterByCode(code)
        `when`(internetChecker.checkConnection()).thenReturn(true)
        `when`(searchCountriesByCodeUseCase(code)).thenReturn(country)

        viewModel.loadInfoAboutCountry()

        advanceUntilIdle()

        val state = viewModel.country.value
        val successState = state as? CountryDetailState.LoadedData

        Assert.assertNotNull(successState)
        Assert.assertEquals(country.getOrNull(0), successState?.country)
        verify(searchCountriesByCodeUseCase, times(1)).invoke(code)
    }

    @Test
    fun openGoogleMapLink_validURL() {
        val url = "https://maps.google.com"
        val uri = mock<Uri>()
        `when`(Uri.parse(url)).thenReturn(uri)

        viewModel.openGoogleMapLink(context, url)

        verify(context, times(1)).startActivity(any())
    }

    @Test
    fun openGoogleMapLink_nullUrl() {
        val intent = Intent(Intent.ACTION_VIEW, null)

        viewModel.openGoogleMapLink(context, null)

        verify(context, never()).startActivity(intent)
    }

}