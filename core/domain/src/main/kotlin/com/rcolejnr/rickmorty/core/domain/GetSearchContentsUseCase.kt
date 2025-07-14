package com.rcolejnr.rickmorty.core.domain

import com.rcolejnr.rickmorty.core.data.repository.SearchContentRepository
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
import com.rcolejnr.rickmorty.core.model.SearchResult
import com.rcolejnr.rickmorty.core.model.UserData
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.core.model.UserSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSearchContentsUseCase @Inject constructor(
    private val searchContentRepository: SearchContentRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(
        searchQuery: String
    ): Flow<UserSearchResult> =
        searchContentRepository.searchContents(searchQuery)
            .mapToUserSearchResult(userDataRepository.userData)
}

private fun Flow<SearchResult>.mapToUserSearchResult(userDataStream: Flow<UserData>): Flow<UserSearchResult> =
    combine(userDataStream) { searchResult, userData ->
        UserSearchResult(
            characters = searchResult.characters.map { char ->
                UserRMCharacter(
                    character = char,
                    userData = userData
                )
            }
        )
    }