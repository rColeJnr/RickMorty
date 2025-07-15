package com.rcolejnr.rickmorty.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rcolejnr.rickmorty.feature.search.databinding.FeatureSearchFragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: Fragment() {

    private var _binding: FeatureSearchFragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeatureSearchFragmentSearchBinding.inflate(
            inflater,
            container,
            false
        )

        binding.composeView.setContent {
//            val animeState by viewModel.feedAnimeUiState.collectAsStateWithLifecycle()
//            val shouldDisplayAnimeUndoFavorite by viewModel.shouldDisplayAnimeUndoFavorite.collectAsStateWithLifecycle()
            SearchScreen(
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