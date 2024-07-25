package com.example.rickandmortycharacterviewer

import android.util.Log
import android.widget.Switch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor() : ViewModel() {
    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> get() = _headerText

    fun onButtonClicked(buttonClicked: Buttons) {
        when (buttonClicked) {
            Buttons.ALIVE -> setHeaderText("Alive Characters")
            Buttons.DEAD -> setHeaderText("Dead Characters")
            Buttons.UNKNOWN -> setHeaderText("Unknown Characters")
        }
    }

    fun setHeaderText(newText: String) {
        _headerText.value = newText
        Log.d("Jack", "Setting header")
    }

}