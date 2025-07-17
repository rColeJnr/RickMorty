package com.rcolejnr.rickmorty.feature.favorites

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rcolejnr.rickmorty.feature.favorite.databinding.FeatureFavoritesFragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: Fragment() {

    private var _binding: FeatureFavoritesFragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeatureFavoritesFragmentFavoritesBinding.inflate(
            inflater,
            container,
            false
        )

        navController = findNavController()

        binding.composeView.setContent {
            FavoritesScreen(
                onBackClick = this::onBackClick,
                onCharacterClick = this::onCharacterClick,
                favoriteViewModel = viewModel

            )
        }
        return binding.root
    }

    private fun onBackClick() {
        navController.navigateUp()
    }

    private fun onCharacterClick(id: Int) {
        val uri = Uri.parse("com.rcolejnr.rmapp://character_fragment/$id")
        navController.navigate(uri)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}