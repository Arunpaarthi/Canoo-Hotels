package com.canoo.canoo_hotels.view.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.canoo.canoo_hotels.databinding.FragmentListBinding
import com.canoo.canoo_hotels.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private val listViewModel: ListViewModel by activityViewModels()

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: HotelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        startList()
        return binding.root
    }

    private fun getCity(): String {
        return arguments?.getString(CITY) ?: "..."
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HotelListAdapter {
            Log.d(TAG, "OnItemClicked!!!")
            view.findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailFragment(
                    propertyId = it.id
                )
            )
        }
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            val dividerItemDecoration = DividerItemDecoration(
                recyclerView.context,
                layoutManager.orientation
            )
            recyclerView.addItemDecoration(dividerItemDecoration)
            binding.recyclerView.adapter = adapter
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Hotels in ${getCity()}"
        if (listViewModel.city != getCity() || listViewModel.propertyList.value.isEmpty()) {
            listViewModel.setDefault()
            listViewModel.getHotels(getCity())
        }
    }

    companion object {
        private const val CITY = "city"
        private const val TAG = "ListFragment"
    }

    private fun startList() {
        handleError()
        initiateLoading()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    listViewModel.propertyList.collect { propertyList ->
                        adapter.submitList(propertyList)
                        propertyList.forEach {
                            Log.d(TAG, "Test - Property Id ${it.id} - Offer - ${it.offerSummary}")
                        }
                    }
                }
            }
        }
    }


    private fun handleError() {
        lifecycleScope.launch {
            listViewModel.error.collect {
                binding.errorLayout.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initiateLoading() {
        lifecycleScope.launch {
            listViewModel.loading.collect {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}