package com.example.rickandmortycharacterviewer.repository

import android.util.Log
import com.example.rickandmortycharacterviewer.model.CharacterResponse
import com.example.rickandmortycharacterviewer.model.CharacterResults
import com.example.rickandmortycharacterviewer.network.CharacterService
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
    private val _aliveCharactersFlow: MutableStateFlow<List<CharacterResponse>> = MutableStateFlow(listOf())
    val aliveCharactersFlow: StateFlow<List<CharacterResponse>> = _aliveCharactersFlow
    private val aliveCharactersList: MutableList<CharacterResponse> = mutableListOf()

    private val _deadCharactersFlow: MutableStateFlow<List<CharacterResponse>> = MutableStateFlow(listOf())
    val deadCharactersFlow: StateFlow<List<CharacterResponse>> = _deadCharactersFlow
    private val deadCharactersList: MutableList<CharacterResponse> = mutableListOf()

    private val _unknownCharactersFlow: MutableStateFlow<List<CharacterResponse>> = MutableStateFlow(listOf())
    val unknownCharactersFlow: StateFlow<List<CharacterResponse>> = _unknownCharactersFlow
    private val unknownCharactersList: MutableList<CharacterResponse> = mutableListOf()

    // TODO: Only make network call if the characters dont already exist in the repo
    suspend fun getCharacters(status: String) {
        withContext(Dispatchers.IO) {
            var characterResponse = characterService.getCharactersByStatus(status)
            val pages = characterResponse.info.pages
            addToList(status, characterResponse)
            for (i in 2..pages) {
                characterResponse = characterService.getCharactersByStatus(status, page= i)
                addToList(status, characterResponse)
            }
        }
    }

    private fun addToList(status:String, characterResponse: CharacterResponse) {
        when (status) {
            "alive" -> {
                aliveCharactersList.add(characterResponse)
                _aliveCharactersFlow.value = aliveCharactersList.toList()
            }
            "dead" -> {
                deadCharactersList.add(characterResponse)
                _deadCharactersFlow.value = deadCharactersList.toList()
            }
            "unknown" -> {
                unknownCharactersList.add(characterResponse)
                _unknownCharactersFlow.value = unknownCharactersList.toList()
            }
        }
    }

}