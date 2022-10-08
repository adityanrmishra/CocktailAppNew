package com.app.cocktailapp.ui.mapper

import com.app.cocktailapp.ui.model.Error
import javax.inject.Inject

class ErrorMapperUI  @Inject constructor() :
    Mapper<Error, String> {
    override fun mapToOut(input: String): Error {
        return Error(
            message = input
        )
    }
}