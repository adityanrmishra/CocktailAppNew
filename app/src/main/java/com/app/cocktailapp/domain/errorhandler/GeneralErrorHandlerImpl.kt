package com.app.cocktailapp.domain.errorhandler

import com.app.cocktailapp.core.ErrorEntity
import okio.IOException
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class GeneralErrorHandlerImpl @Inject constructor() : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                    else -> ErrorEntity.Network
                }
            }
            else -> ErrorEntity.Unknown
        }
    }
}