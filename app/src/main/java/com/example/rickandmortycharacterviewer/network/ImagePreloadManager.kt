package com.example.rickandmortycharacterviewer.network

class ImagePreloadManager {
    // Todo: make this list locked somehow
    private var imageURLs: MutableList<String> = mutableListOf()

    private fun preloadImages() {

    }

    fun addImageURLsAndLoad(toAdd: List<String>) {
        imageURLs.addAll(imageURLs)
    }
}