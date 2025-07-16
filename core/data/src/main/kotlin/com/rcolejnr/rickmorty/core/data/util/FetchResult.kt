package com.rcolejnr.rickmorty.core.data.util

sealed interface FetchResult<T> {
    data class Failure<T>(val error: String): FetchResult<T>
    data class Success<T>(val value: T): FetchResult<T>
}