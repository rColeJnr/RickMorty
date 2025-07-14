package com.rcolejnr.rickmorty.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "recent_search_queries")
data class RecentSearchQueryEntity(
    @PrimaryKey
    val query: String,
    val queriedDate: Instant
)
