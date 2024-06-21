package com.ndmrzzzv.countriesinfo.ui.screens.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.countriesinfo.ui.screens.detail.state.CountryDetailState
import com.ndmrzzzv.countriesinfo.ui.screens.main.state.CountriesState
import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import com.ndmrzzzv.countriesinfo.utils.getPrivateField
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito.any
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Mock
    private lateinit var mockSearchCountriesByCodeUseCase: SearchCountriesByCodeUseCase

    @Mock
    private lateinit var mockInternetChecker: InternetChecker

    @Mock
    private lateinit var mockContext: Context

    private lateinit var mockStateHandle: SavedStateHandle
    private lateinit var mockViewModel: DetailViewModel
    private lateinit var mockStaticUri: MockedStatic<Uri>

    @SuppressLint("CheckResult")
    @Before
    fun setup() {
        mockStaticUri = mockStatic(Uri::class.java)
        mockStateHandle = SavedStateHandle(mapOf("country_code" to "Test"))
        mockViewModel = DetailViewModel(
            mockSearchCountriesByCodeUseCase, mockInternetChecker, mockStateHandle, dispatcher
        )
    }

    @After
    fun tearDown() {
        mockStaticUri.close()
    }

    @Test
    fun initialState_isProduced() {
        val state = mockViewModel.country.value
        Assert.assertEquals(CountryDetailState.Loading, state)
    }

    @Test
    fun getCountryByCode_noInternet() = scope.runTest {
        `when`(mockInternetChecker.checkConnection()).thenReturn(false)

        mockViewModel.loadInfoAboutCountry()

        val state = mockViewModel.country.value
        val failedState = state as? CountryDetailState.LoadingFailed

        Assert.assertNotNull(failedState)
        Assert.assertEquals("You don`t have Internet connection", failedState?.message)
        verify(mockSearchCountriesByCodeUseCase, never()).invoke("Test")
    }

    @Test
    fun getCountryByCode_success() = scope.runTest {
        val code = "Test"
        val country = CountriesForTesting.countriesFilterByCode(code)
        `when`(mockInternetChecker.checkConnection()).thenReturn(true)
        `when`(mockSearchCountriesByCodeUseCase(code)).thenReturn(country)

        mockViewModel.loadInfoAboutCountry()

        advanceUntilIdle()

        val state = mockViewModel.country.value
        val successState = state as? CountryDetailState.LoadedData

        Assert.assertNotNull(successState)
        Assert.assertEquals(country.getOrNull(0), successState?.country)
        verify(mockSearchCountriesByCodeUseCase, times(1)).invoke(code)
    }

    @Test
    fun openGoogleMapLink_validURL() {
        val url = "https://maps.google.com"
        val uri = mock<Uri>()
        `when`(Uri.parse(url)).thenReturn(uri)

        mockViewModel.openGoogleMapLink(mockContext, url)

        verify(mockContext, times(1)).startActivity(any())
    }

    @Test
    fun openGoogleMapLink_nullUrl() {
        val intent = Intent(Intent.ACTION_VIEW, null)

        mockViewModel.openGoogleMapLink(mockContext, null)

        verify(mockContext, never()).startActivity(intent)
    }

    @Test
    fun ensureCountryFlowIsInitialized() {
        val countryFlow = mockViewModel.getPrivateField<CountryDetailState>("_country")

        Assert.assertEquals(CountryDetailState.Loading, countryFlow.value)
    }

}