package com.canoo.canoo_hotels.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canoo.canoo_hotels.R
import com.canoo.canoo_hotels.databinding.ListItemLayoutBinding
import com.canoo.canoo_hotels.model.data.Property

/**
 * [HotelListAdapter] class is responsible for binding and displaying data in recycler view
 *
 * @author Arun Paarthi
 * */
class HotelListAdapter(private val onItemClicked: (Property) -> Unit): ListAdapter<Property, HotelListAdapter.HotelViewHolder>(DiffUtil) {

    inner class HotelViewHolder(private var binding: ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Property) {
            with(binding) {
                tvHotelName.text = data.name
                Glide.with(imageView.context)
                    .load(data.propertyImage.image.url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .optionalFitCenter()
                    .into(imageView)
                with (data.offerSummary.messages) {
                    isNotEmpty().let {
                        if(this[0].message.contains("pay later")) {
                            ivPayLater.visibility = View.VISIBLE
                        } else {
                            ivPayLater.visibility = View.INVISIBLE
                        }
                    }
                }
                listItem.setOnClickListener { onItemClicked(data) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(
            ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<Property>() {
            override fun areItemsTheSame(oldItem: Property, newItem: Property): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Property, newItem: Property): Boolean {
                return oldItem == newItem
            }

        }
    }


}