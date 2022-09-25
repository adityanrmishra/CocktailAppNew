package com.app.cocktailapp.common

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}