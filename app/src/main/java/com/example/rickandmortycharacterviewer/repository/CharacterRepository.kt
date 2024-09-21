package com.example.rickandmortycharacterviewer.repository

import android.util.Log
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

    // TODO: Check why they need to be private again lol
    private val _aliveCharactersFlow: MutableStateFlow<CharacterResponse?> = MutableStateFlow(null)
    val aliveCharactersFlow: StateFlow<CharacterResponse?> = _aliveCharactersFlow

    private val _deadCharactersFlow: MutableStateFlow<CharacterResponse?> = MutableStateFlow(null)
    val deadCharactersFlow: StateFlow<CharacterResponse?> = _deadCharactersFlow

    private val _unknownCharactersFlow: MutableStateFlow<CharacterResponse?> = MutableStateFlow(null)
    val unknownCharactersFlow: StateFlow<CharacterResponse?> = _unknownCharactersFlow

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
        when (status) {
            "alive" -> {
                _aliveCharactersFlow.value = characterResponse
            }
            "dead" -> {
                _deadCharactersFlow.value = characterResponse
            }
            "unknown" -> {
                _unknownCharactersFlow.value = characterResponse
            }
        }
    }

}