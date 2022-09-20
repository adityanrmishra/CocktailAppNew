package com.app.cocktailapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object DrinkInfoModule {
    @Provides
    fun provideDrinksRepository(drinkInfoRepository: com.app.cocktailapp.data.repository.DrinkInfoRepository): com.app.cocktailapp.domain.repository.DrinkInfoRepositoryImp =
        drinkInfoRepository
}