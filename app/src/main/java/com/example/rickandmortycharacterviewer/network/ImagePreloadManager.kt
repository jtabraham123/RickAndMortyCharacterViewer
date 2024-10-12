package com.example.rickandmortycharacterviewer.network

class ImagePreloadManager {
    // Todo: make this list locked somehow
    private var imageURLs: MutableList<String> = mutableListOf()
    private val numImages = 10

    private fun preloadImages(toLoad: Int) {

    }

    fun addImageURLsAndLoad(toAdd: List<String>) {
        imageURLs.addAll(imageURLs)
    }
}