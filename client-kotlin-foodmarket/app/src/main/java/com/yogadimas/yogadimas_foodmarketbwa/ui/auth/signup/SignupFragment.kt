package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentSignupBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.AuthActivity


class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnContinue.setOnClickListener {

                // var fullname = etFullname.text.toString()
                // var email = etEmail.text.toString()
                // var pass = etPassword.text.toString()
                //
                // if (fullname.isNullOrEmpty()) {
                //     etFullname.error = "Silahkan masukkan fullname"
                //     etFullname.requestFocus()
                // } else if (email.isNullOrEmpty()) {
                //     etEmail.error = "Silahkan masukkan email"
                //     etEmail.requestFocus()
                // } else if (pass.isNullOrEmpty()) {
                //     etPassword.error = "Silahkan masukkan pass"
                //     etPassword.requestFocus()
                // } else {
                //     var data = RegisterRequest(
                //         fullname,
                //         email,
                //         pass,
                //         pass,
                //         "", "","","",
                //         filePath
                //     )
                //
                //     var bundle = Bundle()
                //     bundle.putParcelable("data", data)

                    Navigation.findNavController(it)
                        // .navigate(R.id.action_signupFragment_to_signupAddressFragment, bundle)
                        .navigate(R.id.signupAddressFragment, null)
                    (activity as AuthActivity).toolbarSignUpAddress()
                }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}