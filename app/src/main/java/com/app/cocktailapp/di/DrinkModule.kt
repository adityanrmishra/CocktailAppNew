package com.app.cocktailapp.di

import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.repository.DrinkRepositoryImpl
import com.app.cocktailapp.domain.repository.DrinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DrinkModule {
    @Provides
    fun provideDrinkRepository(apiService: ApiService): DrinkRepository {
        return DrinkRepositoryImpl(apiService)
    }
}