package com.rcolejnr.rickmorty.core.model

data class RMLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val created: String
)
