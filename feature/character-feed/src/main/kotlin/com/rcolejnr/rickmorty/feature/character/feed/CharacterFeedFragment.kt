package com.rcolejnr.rickmorty.feature.character.feed

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.feature.character.feed.databinding.FeatureCharacterFeedFragmentCharacterFeedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFeedFragment: Fragment() {

    private var _binding: FeatureCharacterFeedFragmentCharacterFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterFeedViewModel by viewModels()

    private lateinit var adapter: CharacterFeedAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeatureCharacterFeedFragmentCharacterFeedBinding.inflate(
            inflater, container, false
        )

        navController = findNavController()

        initAdapter()

        binding.bindSpinner()

        binding.bindSearch()

        binding.bindFavorites()

        return binding.root
    }

    private fun initAdapter() {
        adapter = CharacterFeedAdapter(
            onFavoriteClick = this::onFavoriteClick,
            onCharacterClick = this::onCharacterClick,
        )

        binding.recyclerView.layoutManager = GridLayoutManager(this.context, 2)

        binding.recyclerView.adapter =
            adapter.withLoadStateFooter(footer = CharacterLoadStateAdapter { adapter.retry() })

        binding.recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun FeatureCharacterFeedFragmentCharacterFeedBinding.bindList(
        adapter: CharacterFeedAdapter,
        pagingDataFlow: Flow<PagingData<UserRMCharacter>>,
    ) {
        lifecycleScope.launch {
            pagingDataFlow.collect(adapter::submitData)
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                swipeRefresh.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
            }
        }

        val notLoading = adapter.loadStateFlow.asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }

        lifecycleScope.launch {
            notLoading.collectLatest {
                if (it) recyclerView.scrollToPosition(0)
            }
        }

        swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun FeatureCharacterFeedFragmentCharacterFeedBinding.bindSpinner() {
        ArrayAdapter.createFromResource(
            this@CharacterFeedFragment.requireContext(),
            R.array.feature_character_feed_character_filters,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                viewModel.onEvent(CharacterFeedEvents.SelectedFilter(pos))
                bindList(
                    adapter,
                    viewModel.pagingDataFlow
                )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Do nothing
            }
        }
    }

    private fun FeatureCharacterFeedFragmentCharacterFeedBinding.bindSearch() {
        search.setOnClickListener {
            val uri = Uri.parse("com.rcolejnr.rmapp://search_fragment")
            navController.navigate(uri)
        }
    }

    private fun FeatureCharacterFeedFragmentCharacterFeedBinding.bindFavorites() {
        favorites.setOnClickListener {
            val uri = Uri.parse("com.rcolejnr.rmapp://favorites_fragment")
            navController.navigate(uri)
        }
    }

    private fun onCharacterClick(id: Int) {
        val uri = Uri.parse("com.rcolejnr.rmapp://character_fragment/$id")
        navController.navigate(uri)
    }

    private fun onFavoriteClick(characterId: Int) {
        Toast.makeText(requireContext(),
            getString(R.string.feature_character_feed_added_to_favorites), Toast.LENGTH_SHORT).show()
        viewModel.onEvent(CharacterFeedEvents.UpdateFavorite(characterId))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}