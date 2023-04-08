package com.canoo.canoo_hotels.view.detail

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
import com.bumptech.glide.Glide
import com.canoo.canoo_hotels.R
import com.canoo.canoo_hotels.databinding.FragmentDetailBinding
import com.canoo.canoo_hotels.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by activityViewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        initDetail()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.id != getPropertyId() || viewModel.hotelDetail.value.hotelDetail.propertyInfo.summary.name == "") {
            viewModel.setDefault()
            viewModel.getHotelDetail(getPropertyId())
        }
        setTitle("...")
    }

    private fun setTitle(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    private fun getPropertyId(): String {
        return arguments?.getString(PROPERTY_ID) ?: "..."
    }

    companion object {
        private const val PROPERTY_ID = "propertyId"
        private const val TAG = "DetailFragment"
    }

    private fun initDetail() {
        handleError()
        initiateLoading()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.hotelDetail.collect { hotelDetail ->
                        with(hotelDetail.hotelDetail.propertyInfo) {
                            with(binding) {
                                tvHotelName.text = summary.name
                                setTitle(summary.name)
                                if (propertyGallery.images.isNotEmpty()) {
                                    Glide.with(requireContext())
                                        .load(propertyGallery.images[0].image.url)
                                        .placeholder(R.drawable.ic_launcher_foreground)
                                        .optionalFitCenter()
                                        .into(ivHotelImageview)
                                }
                                tvHotelTagline.text = summary.tagline
                            }
                            Log.d(TAG, "Test - Count ${propertyGallery.images.size}")
                        }
                    }
                }
            }
        }
    }

    private fun handleError() {
        lifecycleScope.launch {
            viewModel.error.collect {
                binding.errorLayout.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initiateLoading() {
        lifecycleScope.launch {
            viewModel.loading.collect {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}