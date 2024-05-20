package com.yogadimas.yogadimas_foodmarketbwa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ItemHomeHorizontalBinding
import com.yogadimas.yogadimas_foodmarketbwa.interfaces.ItemAdapterCallback
import com.yogadimas.yogadimas_foodmarketbwa.model.dummy.HomeModel

class HomeAdapter(
    private val listData: List<HomeModel>,
    private val itemAdapterCallback: ItemAdapterCallback<HomeModel>,
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(private val binding: ItemHomeHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeModel, itemAdapterCallback: ItemAdapterCallback<HomeModel>) {
            binding.apply {
                tvTitle.text = data.title
                rbFood.rating = data.rating

                root.apply {
                    // Glide.with(context)
                    //     .load(data.src)
                    //     .into(ivPoster)

                    setOnClickListener{itemAdapterCallback.onClick(data)}
                }

            }
        }
    }


}