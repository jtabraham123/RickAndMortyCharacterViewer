package com.example.rickandmortycharacterviewer.UI.characterlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.rickandmortycharacterviewer.ui.characterlist.CharacterListViewModel
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

    fun initView() {
        // TODO: Handle screen rotation (fragment/activity death)
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
        // apply the adapter
        binding.rvCharactersList.apply {}

    }

    suspend fun observeCharacterListFlow() {
        characterListViewModel.characterListFlow.collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> TODO()
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Error -> TODO()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}