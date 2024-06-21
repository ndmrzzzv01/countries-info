package com.ndmrzzzv.countriesinfo.utils

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> Any.getPrivateField(fieldName: String): MutableStateFlow<T> {
    val field = this::class.java.getDeclaredField(fieldName)
    field.isAccessible = true
    return field.get(this) as MutableStateFlow<T>
}

fun Any.callPrivateMethod(methodName: String) {
    val method = this::class.java.getDeclaredMethod(methodName)
    method.isAccessible = true
    method.invoke(this)
}