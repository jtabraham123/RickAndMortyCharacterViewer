package com.example.rickandmortycharacterviewer.repository

import android.util.Log
import com.example.rickandmortycharacterviewer.network.CharacterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterService: CharacterService) {


    suspend fun getCharacters(status: String) {
        withContext(Dispatchers.IO) {
            val obj = characterService.getCharactersByStatus(status)
            Log.d("infoJ", obj.info.pages.toString())
        }
    }

}