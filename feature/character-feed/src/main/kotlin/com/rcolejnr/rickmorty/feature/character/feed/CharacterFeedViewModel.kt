package com.rcolejnr.rickmorty.feature.character.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcolejnr.rickmorty.core.data.repository.UserCharacterRepository
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterFeedViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userCharacterRepository: UserCharacterRepository
): ViewModel() {

    val pagingDataFlow: Flow<PagingData<UserRMCharacter>>

    init {
        pagingDataFlow = fetchCharacters(0).cachedIn(viewModelScope)
    }

    fun onEvent(event: CharacterFeedEvents) {
        when (event){
            is CharacterFeedEvents.SelectedFilter -> {
                fetchCharacters(event.filter)
            }
        }
    }

    private fun fetchCharacters(filter: Int): Flow<PagingData<UserRMCharacter>> {
        val characterFilter = CharacterFilters.entries[filter]
        viewModelScope.launch(Dispatchers.IO) {
            userDataRepository.setCharacterFilter(characterFilter)
        }
        return userCharacterRepository.observeCharacters(
            viewModelScope,
            characterFilter
        )
    }
}

sealed class CharacterFeedEvents{
    data class SelectedFilter(val filter: Int): CharacterFeedEvents()
}