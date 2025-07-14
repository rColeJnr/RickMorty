package com.rcolejnr.rickmorty.core.data.di

import com.rcolejnr.rickmorty.core.data.repository.CharacterRepository
import com.rcolejnr.rickmorty.core.data.repository.CharacterRepositoryImpl
import com.rcolejnr.rickmorty.core.data.repository.RecentSearchRepository
import com.rcolejnr.rickmorty.core.data.repository.RecentSearchRepositoryImpl
import com.rcolejnr.rickmorty.core.data.repository.SearchContentRepository
import com.rcolejnr.rickmorty.core.data.repository.SearchContentRepositoryImpl
import com.rcolejnr.rickmorty.core.data.repository.UserCharacterRepository
import com.rcolejnr.rickmorty.core.data.repository.UserCharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsCharacterRepository(
        characterRepository: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    fun bindsRecentSearchRepository(
        recentSearchRepository: RecentSearchRepositoryImpl
    ): RecentSearchRepository

    @Binds
    fun bindsSearchContentRepository(
        searchContentRepository: SearchContentRepositoryImpl
    ): SearchContentRepository

    @Binds
    fun bindsUserCharacterRepository(
        userCharacterRepository: UserCharacterRepositoryImpl
    ): UserCharacterRepository
}