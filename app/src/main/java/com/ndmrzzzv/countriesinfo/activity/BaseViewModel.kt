package com.ndmrzzzv.countriesinfo.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

open class BaseViewModel: ViewModel() {

    val scopeWithExceptionHandler =
        viewModelScope + CoroutineExceptionHandler { context, throwable ->
            Log.d("Coroutine Exception Handler", throwable.message.toString())
        }

}