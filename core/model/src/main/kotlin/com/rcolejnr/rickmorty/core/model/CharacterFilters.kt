package com.rcolejnr.rickmorty.core.model

enum class CharacterFilters(val filter: String) {
    NO_FILTER("No filter"),
    RICK("Rick"),
    MORTY("Morty"),
    DEAD("Dead"),
    ALIVE("Alive"),
    MALE("Male"),
    FEMALE("Female");
}