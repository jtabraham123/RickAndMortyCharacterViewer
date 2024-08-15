package com.example.rickandmortycharacterviewer.UI.characterlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacterviewer.databinding.CharacterListItemBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class CharacterListAdapter @Inject constructor() : RecyclerView.Adapter<CharacterListItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CharacterListItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}


class CharacterListItemViewHolder(private val itemBinding: CharacterListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

}