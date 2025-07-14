package com.rcolejnr.rickmorty.feature.character.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.rcolejnr.rickmorty.feature.character.feed.databinding.FeatureCharacterFeedLoadStateFooterBinding

class CharacterLoadStateViewHolder(
    private val binding: FeatureCharacterFeedLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharacterLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.feature_character_feed_load_state_footer, parent, false)
            val binding = FeatureCharacterFeedLoadStateFooterBinding.bind(view)
            return CharacterLoadStateViewHolder(binding, retry)
        }
    }

}