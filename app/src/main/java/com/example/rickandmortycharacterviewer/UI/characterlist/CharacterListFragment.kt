package com.example.rickandmortycharacterviewer.UI.characterlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmortycharacterviewer.ui.characterlist.CharacterListViewModel
import com.example.rickandmortycharacterviewer.R
import com.example.rickandmortycharacterviewer.databinding.CharacterListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: CharacterListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        characterListViewModel.headerText.observe(viewLifecycleOwner) { newHeader ->
            binding.textViewHeader.text = newHeader
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_CharacterListFragment_to_MainScreenFragment)
        }
        // apply the adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}