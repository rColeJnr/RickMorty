package com.rcolejnr.rickmorty.core.network.model

data class RMCharacterResponse(
    val info: Info,
    val results: List<RMCharacterNetwork>
) {
    data class Info (
        val count: Int,
        val pages: Int,
        val next: String?,
        val prev: String?
    )
}
