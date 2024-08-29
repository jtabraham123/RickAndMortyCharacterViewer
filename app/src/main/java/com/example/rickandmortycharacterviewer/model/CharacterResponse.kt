package com.example.rickandmortycharacterviewer.model

data class CharacterResponse (
    val info: ResponseInfo,
    val results: List<CharacterResults>
)