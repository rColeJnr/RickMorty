package com.rcolejnr.rickmorty.core.data.repository

import com.rcolejnr.rickmorty.core.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchContentRepository {

    suspend fun populateFtsData()

    fun searchContents(searchQuery: String): Flow<SearchResult>

    fun getSearchContentsCount(): Flow<Int>
}