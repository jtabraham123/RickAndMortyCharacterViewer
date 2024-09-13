package com.example.rickandmortycharacterviewer.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacterviewer.databinding.CharacterListItemBinding
import com.example.rickandmortycharacterviewer.ui.domain.CharacterListItem
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class CharacterListAdapter @Inject constructor() : RecyclerView.Adapter<CharacterListItemViewHolder>(){

    private var characterList: MutableList<CharacterListItem> = mutableListOf()

    fun addToCharacterList(toAdd: List<CharacterListItem>) {
        val firstIndex = characterList.size
        characterList.addAll(toAdd)
        notifyItemRangeChanged(firstIndex, toAdd.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemViewHolder {
        val binding = CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListItemViewHolder(binding)
    }

    override fun getItemCount() = characterList.size

    override fun onBindViewHolder(holder: CharacterListItemViewHolder, position: Int) {
        val listItem = characterList[position]
        holder.bind(listItem)
    }
}


class CharacterListItemViewHolder(private val itemBinding: CharacterListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(characterListItem: CharacterListItem) {
            itemBinding.apply {
                tvCharacterName.text = characterListItem.name
            }
        }
    }