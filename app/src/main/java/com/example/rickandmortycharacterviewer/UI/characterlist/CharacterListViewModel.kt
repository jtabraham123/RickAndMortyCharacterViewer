package com.example.rickandmortycharacterviewer.ui.characterlist

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader.PreloadModelProvider
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.example.rickandmortycharacterviewer.model.CharacterResponse
import com.example.rickandmortycharacterviewer.domain.asListItemDomainModel
import com.example.rickandmortycharacterviewer.repository.CharacterRepository
import com.example.rickandmortycharacterviewer.domain.CharacterListItem
import com.example.rickandmortycharacterviewer.ui.uistate.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> get() = _headerText
    private val _characterListFlow =
        MutableStateFlow<NetworkResult<List<CharacterListItem>>>(NetworkResult.Loading())
    val characterListFlow: StateFlow<NetworkResult<List<CharacterListItem>>> get() = _characterListFlow
    val characterListItems: MutableList<CharacterListItem> = mutableListOf()
    private var status: String = ""

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

    private fun collectNetworkResult(characters: List<CharacterListItem>) {
        _characterListFlow.value = NetworkResult.Success(characters)
        characterListItems.addAll(characters)
    }


    private fun observeCharacterFlow(characterStatus: String) {
        viewModelScope.launch {
            Log.d("characters", "new characters fetched")

            characterRepository.charactersFlow[characterStatus]?.collect { newCharacters ->
                if (newCharacters != null) {
                    collectNetworkResult(newCharacters)
                }
            }
        }

    }

    private fun initializeWithStatus(newStatus: String) {
        _headerText.value = newStatus + " Characters"
        val characterStatus = newStatus.lowercase()
        observeCharacterFlow(characterStatus)
        status = newStatus
        getCharacters(characterStatus)
    }


    inner class CharacterPreloadModelProvider : PreloadModelProvider<Any?> {
        override fun getPreloadItems(position: Int): List<String?> {
            val url = characterRepository.charactersList[status]?.get(position)?.imageURL
            if (TextUtils.isEmpty(url)) {
                return emptyList()
            }
            return listOf(url)
        }

        override fun getPreloadRequestBuilder(url: Any): RequestBuilder<*> {
            return Glide.with(appContext)
                .load(url)
                .priority(Priority.LOW)
        }

    }


}