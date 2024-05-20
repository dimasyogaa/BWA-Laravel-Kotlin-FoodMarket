package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentSigninBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.MainActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.AuthActivity


class SigninFragment : Fragment() {


    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            btnSignin.setOnClickListener {

                // var email = etEmail.text.toString()
                // var password = etPassword.text.toString()
                //
                // if (email.isNullOrEmpty()) {
                //     etEmail.error = "Silahkan masukkan email Anda"
                //     etEmail.requestFocus()
                // } else if (password.isNullOrEmpty()) {
                //     etPassword.error = "Silahkan masukkan password Anda"
                //     etPassword.requestFocus()
                // } else {
                //     presenter.subimtLogin(email, password)
                // }

                startActivity(Intent(activity, MainActivity::class.java))
                requireActivity().finish()

            }

            btnSignup.setOnClickListener {
                val signup = Intent(activity, AuthActivity::class.java)
                signup.putExtra(AuthActivity.KEY_PAGE_REQUEST, 2)
                startActivity(signup)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}