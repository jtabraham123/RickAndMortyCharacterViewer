package com.example.rickandmortycharacterviewer.network

import com.example.rickandmortycharacterviewer.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("character/")
    suspend fun getCharactersByStatus(
        @Query("status") status: String,
        @Query("page") page: Int = 1
    ): CharacterResponse
}