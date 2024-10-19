package com.example.rickandmortycharacterviewer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RickAndMortyCharacterViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}