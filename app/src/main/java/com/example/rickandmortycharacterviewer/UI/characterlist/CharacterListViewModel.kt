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
    private val _characterList =
        MutableLiveData<NetworkResult<List<CharacterListItem>>>(NetworkResult.Loading())
    val characterList: LiveData<NetworkResult<List<CharacterListItem>>> get() = _characterList

    //private val


    init {
        val status = savedStateHandle.get<String>("characterStatus") ?: "defaultStatus"
        initializeWithStatus(status)
    }

    private fun getCharacters(characterStatus: String) {
        // TODO: Figure out what exactly is the point of viewmodel scope
        viewModelScope.launch {
            characterStatus?.let {
                try {
                    characterRepository.getCharacters(it)
                } catch (networkError: IOException) {
                    // TODO: implement error handling

                }
            }
        }
    }

    private fun observeCharacterFlow(newStatus: String) {
        viewModelScope.launch {
            when (newStatus) {
                "Alive" -> {
                    // Code to execute if expression == value1
                    characterRepository.aliveCharactersFlow.collect{ aliveCharacters ->
                        Log.d("Test1" , aliveCharacters.size.toString())
                        if (aliveCharacters.size != 0) {

                        }
                        /*
                        if (currentWeather != null) {
                            currentWeatherMutableStateFlow.value = NetworkResult.Success(currentWeather.asCurrentWeatherDomainModel())

                            dailyForecastWeatherListMutableStateFlow.value =
                                NetworkResult.Success(currentWeather.forecast.asCurrentWeatherDailyForecastDomainModel())
                        }
                        */
                    }
                }

                "Dead" -> {
                    // Code to execute if expression == value2
                }

                "Unknown" -> {
                    // Code to execute if none of the above conditions match
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