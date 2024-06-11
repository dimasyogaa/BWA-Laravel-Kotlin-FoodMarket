package com.yogadimas.yogadimas_foodmarketbwa.ui.order.detailsorders

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentOrdersDetailBinding
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.formatPrice


class OrdersDetailFragment : Fragment(), OrdersDetailContract.View {

    private var _binding: FragmentOrdersDetailBinding? = null

    private val binding get() = _binding!!

    private var progressDialog: Dialog? = null
    lateinit var presenter: OrdersDetailPresenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentOrdersDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data: Data? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(OrdersDetailActivity.KEY_DATA, Data::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(OrdersDetailActivity.KEY_DATA)
        }

        (activity as OrdersDetailActivity?)!!.toolbarPayment()
        initView(data)
        initLoading()
        presenter = OrdersDetailPresenter(this)

    }


    private fun initView(data: Data?) {
        binding.apply {
            tvTitle.text = data?.food?.name
            tvPrice.formatPrice(data?.food?.price.toString())
            Glide.with(requireContext())
                .load(Helpers.replaceLocalhostWithBaseUrl(data?.food?.picturePath))
                .into(ivPoster)

            tvNameItem.text = data?.food?.name
            tvHarga.formatPrice(data?.food?.price.toString())

            if (data?.food?.price.toString().isNotEmpty()) {
                val totalTax = data?.food?.price?.div(10)
                tvTax.formatPrice(totalTax.toString())

                val total = data?.food?.price!! + totalTax!! + 8000
                tvTotal.formatPrice(total.toString())
            } else {
                tvPrice.text = "IDR. 0"
                tvTax.text = "IDR. 0"
                tvTotal.text = "IDR. 0"
            }

            tvName.text = data?.user?.name
            tvPhone.text = data?.user?.phoneNumber
            tvAddress.text = data?.user?.address
            tvHouseNo.text = data?.user?.houseNumber
            tvCity.text = data?.user?.city

            tvOrderStatus.text = data?.id.toString()

            if (data?.status.equals("ON_DELIVERY", true)) {
                btnCancelled.visibility = View.VISIBLE
                constraintLayout3.visibility = View.VISIBLE
                tvPending.text = getString(R.string.text_paid)
            } else if (data?.status.equals("SUCCESS", true) ||
                data?.status.equals("DELIVERED", true)
            ) {
                btnCancelled.visibility = View.INVISIBLE
                constraintLayout3.visibility = View.VISIBLE
                tvPending.text = getString(R.string.text_paid)
            } else if (data?.status.equals("PENDING", true)) {
                btnCancelled.visibility = View.VISIBLE
                btnCancelled.text = getString(R.string.text_pay_now)
                constraintLayout3.visibility = View.VISIBLE
                tvPending.text = getString(R.string.text_pending)
            } else {
                btnCancelled.visibility = View.GONE
                constraintLayout3.visibility = View.VISIBLE
                tvPending.text = getString(R.string.text_cancelled)
            }

            btnCancelled.setOnClickListener {
                if (btnCancelled.text.equals(getString(R.string.text_pay_now))) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(data?.paymentUrl)
                    startActivity(i)
                } else {
                    presenter.getUpdateTransaction(data?.id.toString(), "CANCELLED")
                }
            }
        }
    }

    override fun onUpdateTransactionSuccess(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        requireActivity().finish()
    }

    override fun onUpdateTransactionFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initLoading() {
        progressDialog =
            MaterialAlertDialogBuilder(requireContext()).setView(R.layout.dialog_loader).create()
        progressDialog?.let {
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}