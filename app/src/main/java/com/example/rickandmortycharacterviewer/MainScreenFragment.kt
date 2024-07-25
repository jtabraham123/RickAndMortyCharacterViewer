package com.example.rickandmortycharacterviewer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmortycharacterviewer.databinding.MainScreenFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainScreenFragment : Fragment() {

    private var _binding: MainScreenFragmentBinding? = null

    private val characterListViewModel by activityViewModels<CharacterListViewModel>()

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

        Log.d("VM", "VM in Frag1: " + characterListViewModel.hashCode())

        binding.aliveButton.setOnClickListener {
            characterListViewModel.onButtonClicked(Buttons.ALIVE)
            navigateToMultiCharacterView()
        }
        binding.deadButton.setOnClickListener {
            characterListViewModel.onButtonClicked(Buttons.DEAD)
            navigateToMultiCharacterView()
        }
        binding.unknownButton.setOnClickListener {
            characterListViewModel.onButtonClicked(Buttons.UNKNOWN)
            navigateToMultiCharacterView()
        }
    }

    fun navigateToMultiCharacterView() {
        findNavController().navigate(R.id.action_MainScreenFragment_to_CharacterListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}