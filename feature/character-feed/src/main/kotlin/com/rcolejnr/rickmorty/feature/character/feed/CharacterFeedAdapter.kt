package com.rcolejnr.rickmorty.feature.character.feed

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.rcolejnr.rickmorty.core.model.UserRMCharacter

class CharacterFeedAdapter(
    private val onFavoriteClick: (Int) -> Unit,
    private val onCharacterClick: (Int) -> Unit,
): PagingDataAdapter<UserRMCharacter, CharacterViewHolder>(DIFF_UTIL) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent, onFavoriteClick, onCharacterClick)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<UserRMCharacter>() {
            override fun areItemsTheSame(
                oldItem: UserRMCharacter,
                newItem: UserRMCharacter
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UserRMCharacter,
                newItem: UserRMCharacter
            ): Boolean =
                oldItem == newItem
        }
    }
}