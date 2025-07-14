package com.rcolejnr.rickmorty.core.model

data class UserRMCharacter internal constructor(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val originUrl: String,
    val location: String,
    val locationUrl: String,
    val image: String,
    val characterUrl: String,
    val created: String,
    val isFavorite: Boolean
) {
    constructor(character: RMCharacter, userData: UserData) : this(
        id = character.id,
        name = character.name,
        status = character.status,
        species = character.species,
        type = character.type,
        gender = character.gender,
        origin = character.origin,
        originUrl = character.originUrl,
        location = character.location,
        locationUrl = character.locationUrl,
        image = character.image,
        characterUrl = character.characterUrl,
        created = character.created,
        isFavorite = character.id in userData.characterFavorites
    )
}

fun List<RMCharacter>.mapToUserRMCharacter(userData: UserData): List <UserRMCharacter> =
    map { UserRMCharacter(it, userData) }

fun RMCharacter.mapToUserRMCharacter(userData: UserData): UserRMCharacter =
    UserRMCharacter(this, userData)