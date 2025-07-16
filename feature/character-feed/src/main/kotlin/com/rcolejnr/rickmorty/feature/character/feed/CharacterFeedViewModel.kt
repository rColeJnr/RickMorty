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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterFeedViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userCharacterRepository: UserCharacterRepository
): ViewModel() {

    private val _characterFilter = MutableStateFlow(CharacterFilters.NO_FILTER)
    val characterFilter: StateFlow<CharacterFilters> = _characterFilter.asStateFlow()

    val pagingDataFlow: Flow<PagingData<UserRMCharacter>> = characterFilter
        .flatMapLatest { filter ->
            viewModelScope.launch(Dispatchers.IO) {
                userDataRepository.setCharacterFilter(filter)
            }
            userCharacterRepository.observeCharacters(
                viewModelScope, filter
            )
        }
        .cachedIn(viewModelScope)

    fun onEvent(event: CharacterFeedEvents) {
        when (event){
            is CharacterFeedEvents.SelectedFilter -> {
                _characterFilter.value = CharacterFilters.entries[event.filter]
            }
        }
    }
}

sealed class CharacterFeedEvents{
    data class SelectedFilter(val filter: Int): CharacterFeedEvents()
}