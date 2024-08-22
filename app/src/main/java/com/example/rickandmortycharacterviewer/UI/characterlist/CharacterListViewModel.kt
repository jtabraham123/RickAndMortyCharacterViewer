package com.example.rickandmortycharacterviewer.ui.characterlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacterviewer.network.CharacterService
import com.example.rickandmortycharacterviewer.repository.CharacterRepository
import com.example.rickandmortycharacterviewer.util.Buttons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private var characterStatus : String? = null
    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> get() = _headerText

    fun onButtonClicked(buttonClicked: Buttons) {

        when (buttonClicked) {
            Buttons.ALIVE -> setStatus("Alive")
            Buttons.DEAD -> setStatus("Dead")
            Buttons.UNKNOWN -> setStatus("Unknown")
        }
    }

    fun callAPI() {
        viewModelScope.launch {
            characterStatus?.let { characterRepository.getCharacters(it) }
        }
    }

    fun setStatus(newStatus: String) {
        characterStatus = newStatus.lowercase(Locale.ROOT)
        _headerText.value = newStatus + " Characters"
        callAPI()
        Log.d("Jack", "Setting header")
    }

}