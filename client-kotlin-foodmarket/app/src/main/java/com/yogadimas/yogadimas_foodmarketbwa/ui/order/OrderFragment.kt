package com.yogadimas.yogadimas_foodmarketbwa.ui.order

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.TransactionResponse
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentOrderBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.order.viewmodel.OrderStatusViewModel

class OrderFragment : Fragment(), OrderContract.View {

    private var _binding: FragmentOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var presenter: OrderPresenter
    private var progressDialog: Dialog? = null

    private var inprogressList: ArrayList<Data>? = arrayListOf()
    private var pastordersList: ArrayList<Data>? = arrayListOf()

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null

    private val orderStatusViewModel: OrderStatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        presenter = OrderPresenter(this)
        presenter.getTransaction()
    }

    private fun initView() {
        initLoading()
        binding.apply {
            includeToolbar.toolbar.title = getString(R.string.text_your_orders)
            includeToolbar.toolbar.subtitle = getString(R.string.text_wait_for_the_best_meal)
        }
    }

    private fun initLoading() {
        progressDialog =
            MaterialAlertDialogBuilder(requireContext()).setView(R.layout.dialog_loader).create()
        progressDialog?.let {
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }


    override fun onTransactionSuccess(transactionResponse: TransactionResponse) {
        binding.apply {
            if (transactionResponse.data.isEmpty()) {
                llEmpty.visibility = View.VISIBLE
                llTab.visibility = View.GONE
                includeToolbar.root.visibility = View.GONE
            } else {
                inprogressList?.clear()
                pastordersList?.clear()
                for (a in transactionResponse.data.indices) {
                    if (transactionResponse.data[a].status.equals("ON_DELIVERY", true)
                        || transactionResponse.data[a].status.equals("PENDING", true)
                    ) {
                        inprogressList?.add(transactionResponse.data[a])
                        orderStatusViewModel.setInProgressOrderData(inprogressList)
                    } else if (transactionResponse.data[a].status.equals("DELIVERED", true)
                        || transactionResponse.data[a].status.equals("CANCELLED", true)
                        || transactionResponse.data[a].status.equals("SUCCESS", true)
                    ) {
                        pastordersList?.add(transactionResponse.data[a])
                        orderStatusViewModel.setInPastOrderData(pastordersList)
                    }
                }

                try {
                    sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager, lifecycle)
                    sectionsPagerAdapter?.setData(inprogressList, pastordersList)
                    viewPager.adapter = sectionsPagerAdapter
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        tab.text = resources.getString(TAB_TITLES[position])
                    }.attach()
                } catch (_: Exception) {
                }



            }
        }
    }

    override fun onTransactionFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        sectionsPagerAdapter = null
        _binding = null
    }

    companion object {
        const val KEY_DATA = "key_data"

        private val TAB_TITLES = intArrayOf(
            R.string.text_in_progress,
            R.string.text_past_orders
        )
    }
}