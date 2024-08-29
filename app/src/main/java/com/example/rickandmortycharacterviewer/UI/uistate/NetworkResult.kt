package com.example.rickandmortycharacterviewer.ui.uistate

class NetworkResult<out T : Any> {
    class Loading<T : Any> : NetworkResult<T>()

}