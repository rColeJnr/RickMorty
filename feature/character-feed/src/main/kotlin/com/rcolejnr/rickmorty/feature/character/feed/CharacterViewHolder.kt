package com.rcolejnr.rickmorty.feature.character.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.feature.character.feed.databinding.FeatureCharacterFeedCharacterEntryBinding

class CharacterViewHolder (
    binding: FeatureCharacterFeedCharacterEntryBinding,
    private val onFavoriteClick: (Int) -> Unit,
    private val onCharacterClick: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    private val image = binding.image
    private val name = binding.name
    private val gender = binding.gender
    private val species = binding.species
    private val status = binding.status

    init {
        binding.favoriteButton.setOnClickListener {
            onFavoriteClick(character.id)
        }
        binding.root.setOnClickListener {
            onCharacterClick(character.id)
        }
    }

    private lateinit var character: UserRMCharacter

    fun bind(character: UserRMCharacter) {
        this.character = character
        if (character.image.isNotEmpty()) {
            provideGlide(this.image, character.image)
        }
        this.name.text = character.name
        this.gender.text = character.gender
        this.species.text = character.species
        this.status.text = character.status
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onFavoriteClick: (Int) -> Unit,
            onCharacterClick: (Int) -> Unit,
        ): CharacterViewHolder {
            val itemBinding = FeatureCharacterFeedCharacterEntryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return CharacterViewHolder(itemBinding, onFavoriteClick, onCharacterClick)
        }
    }
}

private fun provideGlide(view: ImageView, image: String) {
    val circularProgressDrawable = CircularProgressDrawable(view.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    Glide.with(view)
        .load(image)
        .apply(
            RequestOptions().placeholder(circularProgressDrawable)
        )
        .into(view)
}