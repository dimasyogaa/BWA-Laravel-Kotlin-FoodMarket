package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentSigninBinding
import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.UserPreference
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.AuthResponse
import com.yogadimas.yogadimas_foodmarketbwa.ui.MainActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.AuthActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signin.presenter.SigninContract
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signin.presenter.SigninPresenter
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.hideKeyboard


class SigninFragment : Fragment(), SigninContract.View {


    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: SigninPresenter

    private var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        presenter = SigninPresenter(this)

        if (!UserPreference.getToken().isNullOrEmpty()) {
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish()
        }

        initDummy()
        initView()

        binding.apply {

            btnSignin.setOnClickListener {

                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email.isEmpty()) {
                    etEmail.error = getString(R.string.text_hint_email_address)
                    etEmail.requestFocus()
                } else if (password.isEmpty()) {
                    etPassword.error = getString(R.string.text_hint_password)
                    etPassword.requestFocus()
                } else {
                    hideKeyboard()
                    presenter.submitLogin(email, password)
                }


            }

            btnSignup.setOnClickListener {
                val signup = Intent(activity, AuthActivity::class.java)
                signup.putExtra(AuthActivity.KEY_PAGE_REQUEST, 2)
                startActivity(signup)
            }
        }
    }


    private fun initView() {
        progressDialog =
            MaterialAlertDialogBuilder(requireContext()).setView(R.layout.dialog_loader).create()
        progressDialog?.let {
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }


    override fun onLoginSuccess(authResponse: AuthResponse) {


        UserPreference.setToken(authResponse.access_token)

        val gson = Gson()
        val json = gson.toJson(authResponse.user)

        UserPreference.setUser(json)

        startActivity(Intent(activity, MainActivity::class.java))
        requireActivity().finish()
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


    private fun hideKeyboard() {
        fun clearFocus() {
            binding.apply {
                etEmail.clearFocus()
                etPassword.clearFocus()
            }
        }
        requireContext().hideKeyboard(requireView())
        clearFocus()
    }

    private fun initDummy() {
        binding.apply {
            etEmail.setText("admin1@yogadimas.com")
            etPassword.setText("12345678")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}