package com.ndmrzzzv.countriesinfo

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class InternetCheckerTest {

    @Test
    fun `internet checker returned false`() {
        // Setup
        val connectivityManagerMock = mock<ConnectivityManager>()

        val contextMock = mock<Context>()
        `when`(
            contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)
        ).thenReturn(connectivityManagerMock)

        // Call
        val subject = InternetChecker(contextMock)
        val result = subject.checkConnection()

        // Assert
        Assert.assertFalse(result)
    }

    @Test
    fun `internet checker returned null`() {
        // Setup
        val connectivityManagerMock = mock<ConnectivityManager>()
        val contextMock = mock<Context>()
        `when`(
            contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)
        ).thenReturn(connectivityManagerMock)

        `when`(
            connectivityManagerMock.getNetworkCapabilities(connectivityManagerMock.activeNetwork)
        ).thenReturn(null)

        // Call
        val subject = InternetChecker(contextMock)
        val result = subject.checkConnection()

        // Assert
        Assert.assertFalse(result)
    }

    @Test
    fun `internet checker returned true`() {
        // Setup
        val connectivityManagerMock = mock<ConnectivityManager>()
        val networkMock = mock<Network>()
        val networkCapabilitiesMock = mock<NetworkCapabilities>()

        val contextMock = mock<Context>()

        `when`(
            contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)
        ).thenReturn(connectivityManagerMock)
        `when`(connectivityManagerMock.activeNetwork).thenReturn(networkMock)
        `when`(connectivityManagerMock.getNetworkCapabilities(networkMock)).thenReturn(networkCapabilitiesMock)
        `when`(networkCapabilitiesMock.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)

        // Call
        val subject = InternetChecker(contextMock)
        val result = subject.checkConnection()

        // Assert
        Assert.assertTrue(result)
    }

}