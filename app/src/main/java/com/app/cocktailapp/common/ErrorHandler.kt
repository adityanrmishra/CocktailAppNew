package com.app.cocktailapp.common

import com.app.cocktailapp.common.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}