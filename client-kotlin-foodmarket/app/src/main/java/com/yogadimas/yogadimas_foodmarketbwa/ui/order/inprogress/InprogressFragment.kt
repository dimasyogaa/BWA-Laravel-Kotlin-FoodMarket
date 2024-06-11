package com.yogadimas.yogadimas_foodmarketbwa.ui.order.inprogress

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentInprogressBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.order.detailsorders.OrdersDetailActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.order.viewmodel.OrderStatusViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InprogressFragment : Fragment() {


    private var _binding: FragmentInprogressBinding? = null

    private val binding get() = _binding!!

    private var adapter: InprogressAdapter? = null
    private var inprogressList:ArrayList<Data>? = ArrayList()

    private val orderStatusViewModel: OrderStatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentInprogressBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inprogressList?.clear()

        // inprogressList = if (Build.VERSION.SDK_INT >= 33) {
        //     arguments?.getParcelableArrayList(OrderFragment.KEY_DATA, Data::class.java)
        // } else {
        //     @Suppress("DEPRECATION")
        //     arguments?.getParcelableArrayList(OrderFragment.KEY_DATA)
        // }


        orderStatusViewModel.inProgressOrderData.observe(viewLifecycleOwner) {
            inprogressList = it
            if (!inprogressList.isNullOrEmpty()) {
                adapter= InprogressAdapter {
                    val detail = Intent(activity, OrdersDetailActivity::class.java).putExtra(OrdersDetailActivity.KEY_DATA, it)
                    startActivity(detail)
                }
                adapter?.submitList(inprogressList)
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

    companion object {
        fun newInstance(position: Int): InprogressFragment {
            val fragment = InprogressFragment()
            val args = Bundle()
            args.putInt("position", position)
            fragment.arguments = args
            return fragment
        }
    }


}