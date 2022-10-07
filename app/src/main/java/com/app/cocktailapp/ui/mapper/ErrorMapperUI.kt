package com.app.cocktailapp.ui.mapper

import com.app.cocktailapp.common.ErrorEntity
import com.app.cocktailapp.ui.model.ErrorType
import com.app.cocktailapp.ui.model.Error
import com.app.cocktailapp.ui.model.NetworkErrorUI
import javax.inject.Inject

class ErrorMapperUI @Inject constructor() :
    Mapper<Error, ErrorEntity?> {

    override fun mapToOut(input: ErrorEntity?): Error {
        return Error(
            message = getMessage(input),
            type = getErrorType(input)
        )
    }

    private fun getMessage(errorEntity: ErrorEntity?): String {
        return when (errorEntity) {
            ErrorEntity.Network -> NetworkErrorUI.NETWORK_ERROR
            ErrorEntity.Unknown ->  NetworkErrorUI.UNKNOWN_ERROR
            ErrorEntity.ServiceUnavailable -> NetworkErrorUI.SERVICE_ERROR
            ErrorEntity.AccessDenied -> NetworkErrorUI.ACCESS_ERROR
            ErrorEntity.NotFound -> NetworkErrorUI.NOT_FOUND_ERROR
            else -> NetworkErrorUI.UNKNOWN_ERROR
        }
    }

    companion object {
        private fun getErrorType(errorEntity: ErrorEntity?): Int {
            return when (errorEntity) {
                ErrorEntity.Network -> ErrorType.TOAST
                ErrorEntity.Unknown -> ErrorType.TOAST
                ErrorEntity.ServiceUnavailable -> ErrorType.TOAST
                ErrorEntity.AccessDenied -> ErrorType.TOAST
                ErrorEntity.NotFound -> ErrorType.TOAST
                null -> ErrorType.TOAST
            }
        }
    }
}