package com.app.cocktailapp.ui.mapper

import android.content.Context
import com.app.cocktailapp.R
import com.app.cocktailapp.common.ErrorEntity
import com.app.cocktailapp.ui.model.ErrorType
import com.app.cocktailapp.ui.model.Error
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapperUI @Inject constructor(@ApplicationContext private val context: Context) :
    Mapper<Error, ErrorEntity?> {

    override fun mapToOut(input: ErrorEntity?): Error {
        return Error(
            message = getMessage(input),
            type = getErrorType(input)
        )
    }

    private fun getMessage(errorEntity: ErrorEntity?): String {
        return when (errorEntity) {
            ErrorEntity.Network -> context.getString(R.string.network_error)
            ErrorEntity.Unknown -> context.getString(R.string.unknown_error)
            ErrorEntity.ServiceUnavailable -> context.getString(R.string.service_error)
            else -> context.getString(R.string.unknown_error)
        }
    }

    private fun getErrorType(errorEntity: ErrorEntity?): Int {
        return when (errorEntity) {
            ErrorEntity.Network -> ErrorType.TOAST
            ErrorEntity.Unknown -> ErrorType.TOAST
            ErrorEntity.ServiceUnavailable -> ErrorType.TOAST
            else -> ErrorType.TOAST
        }
    }
}