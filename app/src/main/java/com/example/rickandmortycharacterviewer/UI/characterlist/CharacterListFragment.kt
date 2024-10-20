package com.example.rickandmortycharacterviewer.ui.characterlist

import android.R.attr.fragment
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.example.rickandmortycharacterviewer.R
import com.example.rickandmortycharacterviewer.databinding.CharacterListFragmentBinding
import com.example.rickandmortycharacterviewer.ui.uistate.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */


@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: CharacterListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var characterListAdapter: CharacterListAdapter

    private val characterListViewModel by viewModels<CharacterListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        /*
         TODO: Handle screen rotation (fragment/activity death) - causes the list view to
        TODO: only populate with the latest flow - think about this for image loading too
        TODO: Image loading should happen in view model context
        */
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeCharacterListFlow()
                }
            }
        }

        characterListViewModel.headerText.observe(viewLifecycleOwner) { newHeader ->
            binding.textViewHeader.text = newHeader
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_CharacterListFragment_to_MainScreenFragment)
        }

        val preloadModelProvider = characterListViewModel.CharacterPreloadModelProvider()
        val sizeProvider = characterListAdapter.sizeProvider
        val recyclerViewPreloader = RecyclerViewPreloader(Glide.with(this), preloadModelProvider, sizeProvider, 15)

        binding.rvCharactersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterListAdapter
            addOnScrollListener(recyclerViewPreloader)
        }

    }

    private suspend fun observeCharacterListFlow() {
        characterListViewModel.characterListFlow.collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    networkResult.data.let { characterList ->
                        binding.pbLoadingSpinner.visibility = View.GONE
                        characterListAdapter.addToCharacterList(characterList.list)
                    }
                }
                is NetworkResult.Loading -> {
                    binding.pbLoadingSpinner.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {}
            }

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}