package com.example.rickandmortycharacterviewer.domain

import com.example.rickandmortycharacterviewer.model.CharacterResponse


fun CharacterResponse.asListItemDomainModel() : List<CharacterListItem> {
    return results.map {
        CharacterListItem(
            name = it.name,
            imageURL = it.image
        )
    }
}