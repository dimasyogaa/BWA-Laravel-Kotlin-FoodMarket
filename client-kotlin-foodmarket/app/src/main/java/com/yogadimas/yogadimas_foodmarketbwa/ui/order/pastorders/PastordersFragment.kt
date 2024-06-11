package com.yogadimas.yogadimas_foodmarketbwa.ui.order.pastorders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentPastordersBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.order.detailsorders.OrdersDetailActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.order.viewmodel.OrderStatusViewModel


class PastordersFragment : Fragment() {
    private var _binding: FragmentPastordersBinding? = null

    private val binding get() = _binding!!

    private var adapter: PastordersAdapter? = null
    private var pastordersList: ArrayList<Data>? = ArrayList()

    private val orderStatusViewModel: OrderStatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentPastordersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pastordersList?.clear()

        // pastordersList = if (Build.VERSION.SDK_INT >= 33) {
        //     arguments?.getParcelableArrayList(OrderFragment.KEY_DATA, Data::class.java)
        // } else {
        //     @Suppress("DEPRECATION")
        //     arguments?.getParcelableArrayList(OrderFragment.KEY_DATA)
        // }

        orderStatusViewModel.inPastOrderData.observe(viewLifecycleOwner) { it ->
            pastordersList = it
            if (!pastordersList.isNullOrEmpty()) {
                adapter = PastordersAdapter {
                    val detail = Intent(activity, OrdersDetailActivity::class.java).putExtra(
                        OrdersDetailActivity.KEY_DATA, it
                    )
                    startActivity(detail)
                }
                pastordersList?.sortByDescending { it.createdAt }
                adapter?.submitList(pastordersList)
                val layoutManager = LinearLayoutManager(activity)

                binding.apply {
                    rcList.layoutManager = layoutManager
                    rcList.adapter = adapter
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}