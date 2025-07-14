package com.rcolejnr.rickmorty.core.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.datetime.Instant

class RMTypeConverters {
    @TypeConverter
    fun fromListToString(list: List<String>) : String =
        list.joinToString(separator = ",")

    @TypeConverter
    fun fromStringToList(string: String): List<String> =
        string.split(",")

    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}