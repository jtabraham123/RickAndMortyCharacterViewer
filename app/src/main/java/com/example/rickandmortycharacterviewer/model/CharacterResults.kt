package com.example.rickandmortycharacterviewer.model

data class CharacterResults (
    val name: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocation,
    val location: CharacterLocation,
    val image: String
)