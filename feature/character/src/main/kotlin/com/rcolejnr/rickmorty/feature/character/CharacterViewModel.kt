package com.rcolejnr.rickmorty.feature.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
import com.rcolejnr.rickmorty.core.data.util.FetchResult
import com.rcolejnr.rickmorty.core.domain.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val userDataRepository: UserDataRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var characterId = savedStateHandle.getStateFlow(key = CHARACTER_ID, initialValue = -1)

    fun setCharacterId(characterId: Int) {
        savedStateHandle[CHARACTER_ID] = characterId
    }

    val character: StateFlow<CharacterUiState> = characterId
        .flatMapLatest { id ->
            if (id == -1) {
                flowOf(CharacterUiState.LoadError("Invalid character"))
            } else {
                getCharacterByIdUseCase.invoke(id)
                    .map { result ->
                        when (result) {
                            is FetchResult.Success -> CharacterUiState.Success(result.value)
                            is FetchResult.Failure -> CharacterUiState.LoadError(result.error)
                        }
                    }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CharacterUiState.Loading
        )

    fun updateCharacterFavorite(characterId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            userDataRepository.setCharacterFavoriteId(
                characterId = characterId,
                isFavorite = !isFavorite
            )
        }
    }
}

private const val CHARACTER_ID = "characterId"