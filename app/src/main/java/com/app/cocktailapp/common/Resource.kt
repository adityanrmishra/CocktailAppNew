package com.app.cocktailapp.common

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorEntity: ErrorEntity? = null
) {

    //

    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null, errorEntity: ErrorEntity? = null) :
        Resource<T>(data, message, errorEntity)
}