package com.app.cocktailapp.di

import com.app.cocktailapp.data.repository.FilterRepository
import com.app.cocktailapp.domain.repository.FilterRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object FilterModule {

    @Provides
    fun provideFilterRepository(filterRepositoryImpl: FilterRepository): FilterRepositoryImp =
        filterRepositoryImpl
}