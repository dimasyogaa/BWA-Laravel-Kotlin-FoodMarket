package com.yogadimas.yogadimas_foodmarketbwa.ui.order.pastorders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ItemPastordersBinding
import com.yogadimas.yogadimas_foodmarketbwa.interfaces.ItemAdapterCallback
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.convertLongToTime
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.formatPrice

class PastordersAdapter (
    private val itemAdapterCallback: ItemAdapterCallback<Data>,
) : ListAdapter<Data, PastordersAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPastordersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, itemAdapterCallback)
        }

    }


    class ViewHolder(private val binding: ItemPastordersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback<Data>) {
            binding.apply {
                tvTitle.text = data.food.name
                tvPrice.formatPrice(data.food.price.toString())

                root.apply {
                    Glide.with(context)
                        .load(Helpers.replaceLocalhostWithBaseUrl(data.food.picturePath))
                        .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                        .placeholder(
                            ContextCompat.getDrawable(context, R.drawable.ic_placeholder_food)
                        )
                        .into(ivPoster)

                    setOnClickListener{itemAdapterCallback.onClick(data)}
                }


                tvDate.text = data.createdAt.convertLongToTime("MMM dd , HH.mm")

                if(data.status.equals("CANCELLED", true)) {
                    tvCancelled.visibility = View.VISIBLE
                }


            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }



}