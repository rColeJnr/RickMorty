package com.rcolejnr.rickmorty.core.network.model

import com.google.gson.annotations.SerializedName

data class RMEpisodeNetwork(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val released: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("characters")
    val characters: List<String>
)
