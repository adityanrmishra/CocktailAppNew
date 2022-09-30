package com.app.cocktailapp.common

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorEntity: ErrorEntity? = null
) {

    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, errorEntity: ErrorEntity? = null) :
        Resource<T>(message = message, errorEntity = errorEntity)
}