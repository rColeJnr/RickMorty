package com.rcolejnr.rickmorty.core.domain

import com.rcolejnr.rickmorty.core.data.model.RecentSearchQuery
import com.rcolejnr.rickmorty.core.data.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetRecentSearchQueriesUseCase @Inject constructor(
    private val rsqRepository: RecentSearchRepository
) {
    operator fun invoke(limit: Int = 10): Flow<List<RecentSearchQuery>> =
        rsqRepository.getRecentSearchQueries(limit)
}