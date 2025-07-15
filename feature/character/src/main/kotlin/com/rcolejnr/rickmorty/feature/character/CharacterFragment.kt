package com.rcolejnr.rickmorty.feature.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rcolejnr.rickmorty.feature.character.databinding.FeatureCharacterFragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment: Fragment() {

    private var _binding: FeatureCharacterFragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeatureCharacterFragmentCharacterBinding.inflate(
            inflater,
            container,
            false
        )

        binding.composeView.setContent {
//            val animeState by viewModel.feedAnimeUiState.collectAsStateWithLifecycle()
//            val shouldDisplayAnimeUndoFavorite by viewModel.shouldDisplayAnimeUndoFavorite.collectAsStateWithLifecycle()
            CharacterScreen(
//                state = animeState,
//                onFavClick = { viewModel.onEvent(FavoriteUiEvents.RemoveAnimeFavorite(it)) },
//                shouldDisplayUndoFavorite = shouldDisplayAnimeUndoFavorite,
//                undoFavoriteRemoval = { viewModel.onEvent(FavoriteUiEvents.UndoAnimeFavoriteRemoval) },
//                clearUndoState = { viewModel.onEvent(FavoriteUiEvents.ClearUndoState) }
            )
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}