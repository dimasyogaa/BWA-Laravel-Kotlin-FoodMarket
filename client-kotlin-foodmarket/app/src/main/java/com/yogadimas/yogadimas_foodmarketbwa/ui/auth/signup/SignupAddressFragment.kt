package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentSignupAddressBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.AuthActivity


class SignupAddressFragment : Fragment() {

    private var _binding: FragmentSignupAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            btnSignUpNow.setOnClickListener {

                // var phone = etPhoneNumber.text.toString()
                // var address = etAddress.text.toString()
                // var houseNumber = etHouseNumber.text.toString()
                // var city = etCity.text.toString()
                //
                // data.let {
                //     it.address = address
                //     it.city = city
                //     it.houseNumber = houseNumber
                //     it.phoneNumber = phone
                // }
                //
                // if (phone.isNullOrEmpty()) {
                //     etPhoneNumber.error = "Silahkan masukkan nomor phone"
                //     etPhoneNumber.requestFocus()
                // } else if (address.isNullOrEmpty()) {
                //     etAddress.error = "Silahkan masukkan address"
                //     etAddress.requestFocus()
                // } else if (houseNumber.isNullOrEmpty()) {
                //     etHouseNumber.error = "Silahkan masukkan house number"
                //     etHouseNumber.requestFocus()
                // } else if (city.isNullOrEmpty()) {
                //     etCity.error = "Silahkan masukkan city"
                //     etCity.requestFocus()
                // } else {
                //     presenter.submitRegister(data, it)
                // }
                Navigation.findNavController(it)
                    // .navigate(R.id.action_signupFragment_to_signupAddressFragment, bundle)
                    .navigate(R.id.signupSuccessFragment, null)
                (activity as AuthActivity).toolbarSignUpSuccess()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}