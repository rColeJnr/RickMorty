package com.rcolejnr.rickmorty.core.data.repository

import com.rcolejnr.rickmorty.core.common.Dispatcher
import com.rcolejnr.rickmorty.core.common.RMDispatchers
import com.rcolejnr.rickmorty.core.database.dao.RMDao
import com.rcolejnr.rickmorty.core.database.dao.RMFtsDao
import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.database.model.asFtsEntity
import com.rcolejnr.rickmorty.core.database.model.asRMCharacter
import com.rcolejnr.rickmorty.core.model.SearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchContentRepositoryImpl @Inject constructor(
    private val rmDao: RMDao,
    private val rmFtsDao: RMFtsDao,
    @Dispatcher(RMDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SearchContentRepository {
    override suspend fun populateFtsData() {
        withContext(ioDispatcher) {
            rmFtsDao.upsertCharacters(
                rmDao.getCharacters(
                    useFilterIds = false
                )
                    .first()
                    .map(RMCharacterEntity::asFtsEntity)
            )
        }
    }

    override fun searchContents(searchQuery: String): Flow<SearchResult> {
        val charactersIds = rmFtsDao.searchAllCharacters("*$searchQuery*")

        return charactersIds
            .mapLatest { it.toSet() }
            .distinctUntilChanged()
            .flatMapLatest {
                rmDao.getCharacters(
                    useFilterIds = true,
                    filterIds = it
                )
            }
            .map {
                SearchResult(
                    characters = it.asRMCharacter()
                )
            }
    }

    override fun getSearchContentsCount(): Flow<Int> =
        rmFtsDao.getCount()
}