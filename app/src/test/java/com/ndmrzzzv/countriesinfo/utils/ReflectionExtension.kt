package com.ndmrzzzv.countriesinfo.utils

fun Any.getPrivateField(fieldName: String): Any? {
    val field = this::class.java.getDeclaredField(fieldName)
    field.isAccessible = true
    return field.get(this)
}

fun Any.callPrivateMethod(methodName: String) {
    val method = this::class.java.getDeclaredMethod(methodName)
    method.isAccessible = true
    method.invoke(this)
}