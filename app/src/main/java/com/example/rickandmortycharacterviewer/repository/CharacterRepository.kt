package com.example.rickandmortycharacterviewer.repository

import android.util.Log
import com.example.rickandmortycharacterviewer.model.CharacterResponse
import com.example.rickandmortycharacterviewer.model.CharacterResults
import com.example.rickandmortycharacterviewer.network.CharacterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CharacterRepository @Inject constructor(
    private val characterService: CharacterService) {


    suspend fun getCharacters(status: String): CharacterResponse {
        return withContext(Dispatchers.IO) {
            val characterResponse = characterService.getCharactersByStatus(status)
            val pages = characterResponse.info.pages
            Log.d("res", characterResponse.results.size.toString())
            characterResponse
            /*
            for (i in 2..pages) {

            }
            */
        }
    }

}