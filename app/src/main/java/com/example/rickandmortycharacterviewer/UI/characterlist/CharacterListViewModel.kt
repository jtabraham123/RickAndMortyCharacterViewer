package com.example.rickandmortycharacterviewer.ui.characterlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacterviewer.network.CharacterService
import com.example.rickandmortycharacterviewer.repository.CharacterRepository
import com.example.rickandmortycharacterviewer.util.Buttons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> get() = _headerText
    //private val


    init {
        val status = savedStateHandle.get<String>("characterStatus") ?: "defaultStatus"
        initializeWithStatus(status)
    }

    private fun getCharacters(characterStatus:String) {
        viewModelScope.launch {
            characterStatus?.let {
                characterRepository.getCharacters(it)
            }
        }
    }

    private fun initializeWithStatus(newStatus: String) {
        _headerText.value = newStatus + " Characters"
        val characterStatus = newStatus.lowercase()
        getCharacters(characterStatus)
    }

}