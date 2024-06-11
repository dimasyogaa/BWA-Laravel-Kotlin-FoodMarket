package com.yogadimas.yogadimas_foodmarketbwa.ui.detail.payment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.UserPreference
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.User
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.checkout.CheckoutResponse
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.Data
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentPaymentBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.DetailActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.payment.presenter.PaymentContract
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.payment.presenter.PaymentPresenter
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.foodEntityToData
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.formatPrice


class PaymentFragment : Fragment(), PaymentContract.View {
    private var _binding: FragmentPaymentBinding? = null

    private val binding get() = _binding!!

    private var progressDialog: Dialog? = null

    lateinit var presenter: PaymentPresenter
    private var total: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnCheckout.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.paymentSuccessFragment, null)
                (activity as DetailActivity).toolbarDetail()
            }
        }

        val foodEntity = PaymentFragmentArgs.fromBundle(
            arguments as Bundle
        ).food

        initView(foodEntityToData(foodEntity ?: FoodEntity()))

        initView()
        presenter = PaymentPresenter(this)
    }

    private fun initView() {
        progressDialog =
            MaterialAlertDialogBuilder(requireContext()).setView(R.layout.dialog_loader).create()
        progressDialog?.let {
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(data: Data?) {

        binding.apply {
            tvTitle.text = data?.name
            tvPrice.formatPrice(data?.price.toString())

            Glide.with(requireContext())
                .load(Helpers.replaceLocalhostWithBaseUrl(data?.picturePath))
                .into(ivPoster)

            tvNameItem.text = data?.name
            tvHarga.formatPrice(data?.price.toString())

            if (data?.price.toString().isNotEmpty()) {
                val totalTax = data?.price?.div(10)
                tvTax.formatPrice(totalTax.toString())

                total = data?.price!! + totalTax!! + 8000
                tvTotal.formatPrice(total.toString())
            } else {
                tvPrice.text = "IDR. 0"
                tvTax.text = "IDR. 0"
                tvTotal.text = "IDR. 0"
                total = 0
            }

            val user = UserPreference.getUser()
            val userResponse = Gson().fromJson(user, User::class.java)

            tvName.text = userResponse?.name
            tvPhoneNo.text = userResponse?.phoneNumber
            tvAddress.text = userResponse?.address
            tvCity.text = userResponse?.city

            btnCheckout.setOnClickListener {
                presenter.getCheckout(
                    data?.id.toString(),
                    userResponse?.id.toString(),
                    "1",
                    total.toString(),
                    it
                )
            }
        }

    }




    override fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: View) {

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(checkoutResponse.paymentUrl)
        startActivity(i)

        Navigation.findNavController(view).navigate(R.id.action_paymentFragment_to_paymentSuccessFragment)
    }

    override fun onCheckoutFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


    override fun onResume() {
        super.onResume()
        (activity as DetailActivity).toolbarPayment()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}