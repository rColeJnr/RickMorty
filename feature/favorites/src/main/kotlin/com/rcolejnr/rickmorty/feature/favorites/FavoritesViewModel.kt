package com.rcolejnr.rickmorty.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcolejnr.rickmorty.core.data.repository.UserCharacterRepository
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userCharacterRepository: UserCharacterRepository
): ViewModel() {

    val favorites: StateFlow<FavoritesUiState> =
        userCharacterRepository.observeCharacterFavorites()
            .map<List<UserRMCharacter>, FavoritesUiState>(FavoritesUiState::Success)
            .stateIn (
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FavoritesUiState.Loading
            )

    fun removeCharacterFavorite(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataRepository.setCharacterFavoriteId(characterId, false)
        }
    }

}