package com.rcolejnr.rickmorty.feature.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        arguments?.let {
            viewModel.setCharacterId(it.getInt("character"))
        }

        binding.composeView.setContent {
            CharacterScreen(
                onRetry = this::onRetry,
                onBackClick = { findNavController().navigateUp() },
                characterViewModel = viewModel
            )
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun onRetry() {
        viewModel.setCharacterId(-1)
        arguments?.let {
            viewModel.setCharacterId(it.getInt("character"))
        }
    }
}