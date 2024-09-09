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
import kotlinx.coroutines.flow.flowOf

class CharacterRepository @Inject constructor(
    private val characterService: CharacterService) {

    // TODO: only expose the read only flow, these should be private
    val aliveCharactersFlow: MutableStateFlow<List<CharacterResponse>> = MutableStateFlow(listOf())
    val aliveCharactersList: MutableList<CharacterResponse> = mutableListOf()
    val deadCharactersFlow: MutableStateFlow<List<CharacterResponse>> = MutableStateFlow(listOf())
    val deadCharactersList: MutableList<CharacterResponse> = mutableListOf()
    val unknownCharactersFlow: MutableStateFlow<List<CharacterResponse>> = MutableStateFlow(listOf())
    val unknownCharactersList: MutableList<CharacterResponse> = mutableListOf()

    suspend fun getCharacters(status: String) {
        withContext(Dispatchers.IO) {
            val j = 1
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
                Log.d("Test1", "size of list" + aliveCharactersList.size)
                aliveCharactersFlow.value = aliveCharactersList.toList()
            }
            "dead" -> {
                deadCharactersList.add(characterResponse)
                deadCharactersFlow.value = deadCharactersList.toList()
            }
            "unknown" -> {
                unknownCharactersList.add(characterResponse)
                unknownCharactersFlow.value = unknownCharactersList.toList()
            }
        }
    }

}