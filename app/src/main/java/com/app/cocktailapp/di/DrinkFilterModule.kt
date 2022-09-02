package com.app.cocktailapp.di

import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.repository.DrinkFilterRepositoryImpl
import com.app.cocktailapp.domain.repository.DrinkFilterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
object DrinkFilterModule {

    @Provides
    fun provideDrinkFilterRepository(apiService: ApiService): DrinkFilterRepository {
        return DrinkFilterRepositoryImpl(apiService)
    }
}