package com.rcolejnr.rickmorty.feature.search

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rcolejnr.rickmorty.feature.search.databinding.FeatureSearchFragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: Fragment() {

    private var _binding: FeatureSearchFragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var navController: NavController

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

        navController = findNavController()

        binding.composeView.setContent {
            SearchScreen(
                onBackClick = { navController.navigateUp() },
                onCharacterClick = this::navigateToCharacter,
                searchViewModel = viewModel
            )
        }
        return binding.root
    }

    private fun navigateToCharacter(id: Int) {
        val uri = Uri.parse("com.rcolejnr.rmapp://character_fragment/$id")
        navController.navigate(uri)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}