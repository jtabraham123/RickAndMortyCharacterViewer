package com.example.rickandmortycharacterviewer.ui.mainscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmortycharacterviewer.util.Buttons
import com.example.rickandmortycharacterviewer.R
import com.example.rickandmortycharacterviewer.databinding.MainScreenFragmentBinding
import com.example.rickandmortycharacterviewer.ui.characterlist.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private var _binding: MainScreenFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aliveButton.setOnClickListener {
            navigateToMultiCharacterView("Alive")
        }
        binding.deadButton.setOnClickListener {
            navigateToMultiCharacterView("Dead")
        }
        binding.unknownButton.setOnClickListener {
            navigateToMultiCharacterView("Unknown")
        }
    }

    fun navigateToMultiCharacterView(status: String) {
        val action = MainScreenFragmentDirections.actionMainScreenFragmentToCharacterListFragment(status)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}