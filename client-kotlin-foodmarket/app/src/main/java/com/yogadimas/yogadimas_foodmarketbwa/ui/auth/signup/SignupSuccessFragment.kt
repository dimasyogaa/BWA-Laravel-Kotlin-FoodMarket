package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentSignupSuccessBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.MainActivity


class SignupSuccessFragment : Fragment() {
    private var _binding: FragmentSignupSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnFind.setOnClickListener {
                goToHome()
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goToHome()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


    }

    private fun goToHome() {
        val home = Intent(activity, MainActivity::class.java)
        startActivity(home)
        activity?.finishAffinity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}