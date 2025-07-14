package com.rcolejnr.rickmorty.core.data.repository

import com.rcolejnr.rickmorty.core.data.model.RecentSearchQuery
import com.rcolejnr.rickmorty.core.data.model.asExternalModel
import com.rcolejnr.rickmorty.core.database.dao.RecentSearchQueryDao
import com.rcolejnr.rickmorty.core.database.model.RecentSearchQueryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import javax.inject.Inject

internal class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchQueryDao: RecentSearchQueryDao
): RecentSearchRepository {

    override suspend fun upsertRecentSearchQueries(searchQuery: String) {
        recentSearchQueryDao.insertOrReplaceRecentSearchQuery(
            RecentSearchQueryEntity(
                query = searchQuery,
                queriedDate = Clock.System.now()
            )
        )
    }

    override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> =
        recentSearchQueryDao.getRecentSearchQueryEntities(limit).map { searchQueries ->
            searchQueries.map { it.asExternalModel() }
        }


    override suspend fun clearRecentSearchQueries() = recentSearchQueryDao.clearRecentSearchQueries()
}