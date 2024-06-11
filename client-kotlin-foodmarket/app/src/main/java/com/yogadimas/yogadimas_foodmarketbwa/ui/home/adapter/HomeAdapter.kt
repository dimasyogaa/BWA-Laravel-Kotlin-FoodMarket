package com.yogadimas.yogadimas_foodmarketbwa.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ItemHomeHorizontalBinding
import com.yogadimas.yogadimas_foodmarketbwa.interfaces.ItemAdapterCallback
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.replaceLocalhostWithBaseUrl

class HomeAdapter(
    private val itemAdapterCallback: ItemAdapterCallback<FoodEntity>
) : PagingDataAdapter<FoodEntity, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodEntity = getItem(position)
        if (foodEntity != null) {
            holder.bind(foodEntity, itemAdapterCallback)
        }

    }


    class ViewHolder(private val binding: ItemHomeHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: FoodEntity,
            itemAdapterCallback: ItemAdapterCallback<FoodEntity>
        ) {


            binding.apply {
                tvTitle.text = data.name
                rbFood.rating = data.rate?.toFloat() ?: 0f
                root.apply {
                    Glide.with(context)
                        .load(replaceLocalhostWithBaseUrl(data.picturePath))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                        .placeholder(
                            ContextCompat.getDrawable(context, R.drawable.ic_placeholder_food)
                        )
                        .into(ivPoster)

                    setOnClickListener {
                        itemAdapterCallback.onClick(data)
                    }

                }
            }

        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodEntity>() {
            override fun areItemsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }


}