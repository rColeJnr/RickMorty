package com.rcolejnr.rickmorty.core.data.di

import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UserDataRepositoryModule {

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository
}