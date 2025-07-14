package com.rcolejnr.rickmorty.core.data.repository

import com.rcolejnr.rickmorty.core.data.model.RecentSearchQuery
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {

    fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>>

    suspend fun upsertRecentSearchQueries(searchQuery: String)

    suspend fun clearRecentSearchQueries()
}