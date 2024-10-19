package com.example.rickandmortycharacterviewer.ui.characterlist

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.rickandmortycharacterviewer.databinding.CharacterListItemBinding
import com.example.rickandmortycharacterviewer.domain.CharacterListItem
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class CharacterListAdapter @Inject constructor(val sizeProvider: ViewPreloadSizeProvider<Any>) : RecyclerView.Adapter<CharacterListItemViewHolder>(){

    private var characterList: MutableList<CharacterListItem> = mutableListOf()

    fun addToCharacterList(toAdd: List<CharacterListItem>) {
        val firstIndex = characterList.size
        characterList.addAll(toAdd)
        notifyItemRangeChanged(firstIndex, toAdd.size)
        Log.d("size", toAdd.size.toString())
        Log.d("size", "char list size$itemCount")
        for (item in toAdd) {
            Log.d("test", item.name)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemViewHolder {
        val binding = CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        sizeProvider.setView(binding.pbLoadingSpinnerImage)
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
                characterListItem.apply{
                    pbLoadingSpinnerImage.visibility = View.VISIBLE
                    tvCharacterName.text = name
                    Glide.with(ivCharacterIcon.context).load(imageURL).priority(Priority.IMMEDIATE).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            // Hide progress bar and show error message or placeholder
                            pbLoadingSpinnerImage.visibility = View.GONE
                            return false // Glide handles the error placeholder
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            pbLoadingSpinnerImage.visibility = View.GONE
                            return false // Glide handles the error placeholder
                        }
                    }).into(ivCharacterIcon)
                }

            }
        }
    }