package com.app.cocktailapp.ui.mapper

import android.content.res.Resources
import com.app.cocktailapp.R
import com.app.cocktailapp.common.ErrorEntity
import com.app.cocktailapp.ui.model.ErrorType
import com.app.cocktailapp.ui.model.Error
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
            ErrorEntity.Network -> Resources.getSystem().getString(R.string.network_error)
            ErrorEntity.Unknown -> Resources.getSystem().getString(R.string.unknown_error)
            ErrorEntity.ServiceUnavailable -> Resources.getSystem().getString(R.string.service_error)
            ErrorEntity.AccessDenied -> Resources.getSystem().getString(R.string.access_denied_error)
            ErrorEntity.NotFound -> Resources.getSystem().getString(R.string.not_found_error)
            else -> Resources.getSystem().getString(R.string.unknown_error)
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