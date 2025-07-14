package com.rcolejnr.rickmorty.core.network.model

import com.google.gson.annotations.SerializedName

data class RMLocationNetwork(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("residents")
    val residents: List<String>,
    @SerializedName("created")
    val created: String
)
