package com.example.rickandmortycharacterviewer.domain


data class CharacterList(
    val list: List<CharacterListItem>
)

data class CharacterListItem(
    val name: String,
    val imageURL: String
)