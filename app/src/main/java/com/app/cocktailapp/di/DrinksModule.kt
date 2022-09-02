package com.app.cocktailapp.di

import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.repository.DrinksRepositoryImpl
import com.app.cocktailapp.domain.repository.DrinksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DrinksModule {
    @Provides
    fun provideDrinksRepository(apiService: ApiService): DrinksRepository {
        return DrinksRepositoryImpl(apiService)
    }
}