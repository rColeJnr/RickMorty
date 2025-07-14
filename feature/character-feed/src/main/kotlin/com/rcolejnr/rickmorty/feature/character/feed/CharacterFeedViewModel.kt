package com.rcolejnr.rickmorty.feature.character.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcolejnr.rickmorty.core.data.repository.UserCharacterRepository
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterFeedViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userCharacterRepository: UserCharacterRepository
): ViewModel() {

    val pagingDataFlow: Flow<PagingData<UserRMCharacter>>

    init {
        pagingDataFlow = fetchCharacters().cachedIn(viewModelScope)
    }

    private fun fetchCharacters(): Flow<PagingData<UserRMCharacter>> =
        userCharacterRepository.observeCharacters(
            null, null, null
        )
}