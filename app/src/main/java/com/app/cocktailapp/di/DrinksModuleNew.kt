package com.app.cocktailapp.di

import com.app.cocktailapp.data.repository.DrinksRepository
import com.app.cocktailapp.domain.repository.DrinksRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object DrinksModuleNew {
    @Provides
    fun provideDrinksRepository(drinksRepository: DrinksRepository): DrinksRepositoryImp =
        drinksRepository
}