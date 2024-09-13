package com.example.rickandmortycharacterviewer.ui.characterlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacterviewer.network.asListItemDomainModel
import com.example.rickandmortycharacterviewer.repository.CharacterRepository
import com.example.rickandmortycharacterviewer.ui.domain.CharacterListItem
import com.example.rickandmortycharacterviewer.ui.uistate.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> get() = _headerText
    private val _characterListFlow =
        MutableStateFlow<NetworkResult<List<CharacterListItem>>>(NetworkResult.Loading())
    val characterListFlow: StateFlow<NetworkResult<List<CharacterListItem>>> get() = _characterListFlow

    //private val


    init {
        val status = savedStateHandle.get<String>("characterStatus") ?: "defaultStatus"
        initializeWithStatus(status)
    }

    private fun getCharacters(characterStatus: String) {
        viewModelScope.launch {
            characterStatus?.let {
                try {
                    Log.d("characters", "fetching from network")
                    characterRepository.getCharacters(it)
                } catch (networkError: IOException) {
                    // TODO: implement error handling

                }
            }
        }
    }


    private fun observeCharacterFlow(newStatus: String) {
        viewModelScope.launch {
            Log.d("characters", "new characters fetched")
            when (newStatus) {
                "Alive" -> {
                    characterRepository.aliveCharactersFlow.collect{ aliveCharacters ->
                        if (aliveCharacters != null) {
                            _characterListFlow.value = NetworkResult.Success(aliveCharacters.asListItemDomainModel())
                        }
                    }
                }
                "Dead" -> {
                    characterRepository.deadCharactersFlow.collect{ deadCharacters ->
                        if (deadCharacters != null) {
                            _characterListFlow.value = NetworkResult.Success(deadCharacters.asListItemDomainModel())
                        }
                    }
                }

                "Unknown" -> {
                    // Code to execute if none of the above conditions match
                    characterRepository.unknownCharactersFlow.collect{ unknownCharacters ->
                        if (unknownCharacters != null) {
                            _characterListFlow.value = NetworkResult.Success(unknownCharacters.asListItemDomainModel())
                        }
                    }
                }
            }
        }

    }

    private fun initializeWithStatus(newStatus: String) {
        _headerText.value = newStatus + " Characters"
        val characterStatus = newStatus.lowercase()
        observeCharacterFlow(newStatus)
        getCharacters(characterStatus)
    }
}