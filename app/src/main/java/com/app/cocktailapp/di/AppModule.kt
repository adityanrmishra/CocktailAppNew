package com.app.cocktailapp.di

import com.app.cocktailapp.domain.errorhandler.ErrorHandler
import com.app.cocktailapp.domain.errorhandler.GeneralErrorHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return GeneralErrorHandlerImpl()
    }
}