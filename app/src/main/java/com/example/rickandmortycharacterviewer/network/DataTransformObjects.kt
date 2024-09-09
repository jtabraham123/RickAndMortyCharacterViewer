package com.example.rickandmortycharacterviewer.network

import com.example.rickandmortycharacterviewer.model.CharacterResponse
import com.example.rickandmortycharacterviewer.ui.domain.CharacterListItem


fun CharacterResponse.asListItemDomainModel() : List<CharacterListItem> {
    return results.map {
        CharacterListItem(
            name = it.name,
            imageURL = it.image
        )
    }
}