package com.yogadimas.yogadimas_foodmarketbwa.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentPaymentSuccessBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.MainActivity


class PaymentSuccessFragment : Fragment() {
    private var _binding: FragmentPaymentSuccessBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnMyOrder.setOnClickListener {
                val home = Intent(activity, MainActivity::class.java)
                startActivity(home)
                activity?.finishAffinity()
            }
            btnOtherFood.setOnClickListener {
                val home = Intent(activity, MainActivity::class.java)
                startActivity(home)
                activity?.finishAffinity()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as DetailActivity).toolbarDetail()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}