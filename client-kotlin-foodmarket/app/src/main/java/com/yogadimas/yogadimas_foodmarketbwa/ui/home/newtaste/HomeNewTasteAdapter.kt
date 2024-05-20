package com.yogadimas.yogadimas_foodmarketbwa.ui.home.newtaste

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.foodmarketkotlin.utils.Helpers.formatPrice
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ItemHomeVerticalBinding
import com.yogadimas.yogadimas_foodmarketbwa.interfaces.ItemAdapterCallback
import com.yogadimas.yogadimas_foodmarketbwa.model.dummy.HomeVerticalModel

class HomeNewTasteAdapter (
    private val listData: List<HomeVerticalModel>,
    private val itemAdapterCallback: ItemAdapterCallback<HomeVerticalModel>,
) : RecyclerView.Adapter<HomeNewTasteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(private val binding: ItemHomeVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeVerticalModel, itemAdapterCallback: ItemAdapterCallback<HomeVerticalModel>) {
            binding.apply {
                tvTitle.text = data.title
                tvPrice.formatPrice(data.price)
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