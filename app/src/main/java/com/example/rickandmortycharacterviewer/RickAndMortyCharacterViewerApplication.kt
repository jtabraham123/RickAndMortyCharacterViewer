package com.example.rickandmortycharacterviewer

import android.app.Application
import com.example.rickandmortycharacterviewer.network.ImageInterceptor
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RickAndMortyCharacterViewerApplication : Application() {

    @Inject
    lateinit var imageInterceptor: ImageInterceptor

    override fun onCreate() {
        super.onCreate()
    }
}