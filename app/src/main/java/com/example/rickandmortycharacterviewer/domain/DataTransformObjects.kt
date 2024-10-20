package com.example.rickandmortycharacterviewer.domain

import com.example.rickandmortycharacterviewer.model.CharacterResponse


fun CharacterResponse.asListItemDomainModel() : CharacterList {
    val list = results.sortedBy { it.name }.map {
        CharacterListItem(
            name = it.name,
            imageURL = it.image
        )
    }
    return CharacterList(list = list)
}