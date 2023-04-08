package com.canoo.canoo_hotels.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.canoo.canoo_hotels.R
import com.canoo.canoo_hotels.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * [SearchFragment] Search fragment provides option to search for hotels in a city.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSearch.setOnClickListener {
            view.findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToListFragment(
                    binding.etSearchLocation.text.toString()
                )
            )
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }
}