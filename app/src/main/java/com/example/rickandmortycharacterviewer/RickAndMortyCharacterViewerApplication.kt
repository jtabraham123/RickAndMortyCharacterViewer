package com.example.rickandmortycharacterviewer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RickAndMortyCharacterViewerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}