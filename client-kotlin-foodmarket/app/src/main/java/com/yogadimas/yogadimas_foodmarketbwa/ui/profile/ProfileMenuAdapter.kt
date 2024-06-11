package com.yogadimas.yogadimas_foodmarketbwa.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yogadimas.yogadimas_foodmarketbwa.data.dummy.ProfileMenuModel
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ItemMenuProfileBinding
import com.yogadimas.yogadimas_foodmarketbwa.interfaces.ItemAdapterCallback

class ProfileMenuAdapter(
    private val listData: List<ProfileMenuModel>,
    private val itemAdapterCallback: ItemAdapterCallback<ProfileMenuModel>,
) : RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMenuProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(private val binding: ItemMenuProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: ProfileMenuModel,
            itemAdapterCallback: ItemAdapterCallback<ProfileMenuModel>,
        ) {
            binding.apply {
                tvTitle.text = data.title
                root.setOnClickListener { itemAdapterCallback.onClick(data) }
            }
        }
    }


}