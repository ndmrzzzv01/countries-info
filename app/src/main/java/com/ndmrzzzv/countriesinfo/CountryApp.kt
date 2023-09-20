package com.ndmrzzzv.countriesinfo

import android.app.Application
import com.ndmrzzzv.countriesinfo.module.appModule
import com.ndmrzzzv.data.module.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountryApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CountryApp)
            modules(listOf(dataModule, appModule))
        }
    }

}