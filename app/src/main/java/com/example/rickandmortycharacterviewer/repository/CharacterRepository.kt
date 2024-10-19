package com.example.rickandmortycharacterviewer.repository

import android.util.Log
import com.example.rickandmortycharacterviewer.domain.CharacterListItem
import com.example.rickandmortycharacterviewer.domain.asListItemDomainModel
import com.example.rickandmortycharacterviewer.model.CharacterResponse
import com.example.rickandmortycharacterviewer.model.CharacterResults
import com.example.rickandmortycharacterviewer.network.CharacterService
//import com.example.rickandmortycharacterviewer.network.GlideApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

class CharacterRepository @Inject constructor(
    private val characterService: CharacterService) {

    private val _charactersFlow: MutableMap<String, MutableStateFlow<List<CharacterListItem>?>> = mutableMapOf(
        "alive" to MutableStateFlow(null),
        "dead" to MutableStateFlow(null),
        "unknown" to MutableStateFlow(null)
    )
    val aliveCharactersFlow = _charactersFlow["alive"]
    val deadCharactersFlow = _charactersFlow["dead"]
    val unknownCharactersFlow = _charactersFlow["unknown"]

    suspend fun getCharacters(status: String) {
        withContext(Dispatchers.IO) {
            var characterResponse = characterService.getCharactersByStatus(status)
            val pages = characterResponse.info.pages
            val imageURLs = characterResponse.results.map { it.image }
            for (i in 2..pages) {
                characterResponse = characterService.getCharactersByStatus(status, page= i)
                addToList(status, characterResponse)
            }
        }
    }

    private fun addToList(status:String, characterResponse: CharacterResponse) {
        _charactersFlow[status]?.value = characterResponse.asListItemDomainModel()
    }

}