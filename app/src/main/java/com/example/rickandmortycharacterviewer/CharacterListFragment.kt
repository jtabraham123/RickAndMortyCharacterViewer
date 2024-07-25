package com.example.rickandmortycharacterviewer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.rickandmortycharacterviewer.databinding.CharacterListFragmentBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CharacterListFragment : Fragment() {

    private var _binding: CharacterListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val characterListViewModel by activityViewModels<CharacterListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterListViewModel.headerText.observe(viewLifecycleOwner) { newHeader ->
            binding.textViewHeader.text = newHeader
            Log.d("Jack", newHeader)
        }
        Log.d("VM", "VM in Frag2: " + characterListViewModel.hashCode())

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_CharacterListFragment_to_MainScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}