package com.app.cocktailapp.domain.errorhandler

import com.app.cocktailapp.core.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}