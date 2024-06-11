package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.UserPreference
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.request.RegisterRequest
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.AuthResponse
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.User
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentSignupAddressBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.AuthActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup.presenter.SignupContract
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup.presenter.SignupPresenter


class SignupAddressFragment : Fragment(), SignupContract.View {

    private var _binding: FragmentSignupAddressBinding? = null
    private val binding get() = _binding!!


    private lateinit var data: RegisterRequest
    lateinit var presenter: SignupPresenter
    var progressDialog: Dialog? = null


    private lateinit var gson: Gson
    private lateinit var user: User

    // Chucker ( Android 13 Tiramisu (API Level 33) ke atas )
     private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Toast.makeText(activity, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // Toast.makeText(activity, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gson = Gson()

        // Chucker ( Android 13 Tiramisu (API Level 33) ke atas )
         if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        presenter = SignupPresenter(this, requireActivity())
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(KEY_DATA, RegisterRequest::class.java) ?: RegisterRequest()
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(KEY_DATA) ?: RegisterRequest()
        }




        initDummy()
        initListener()
        initView()


    }


    private fun initListener() {
        binding.apply {

            btnSignUpNow.setOnClickListener { view ->

                val phone = etPhoneNumber.text.toString()
                val address = etAddress.text.toString()
                val houseNumber = etHouseNumber.text.toString()
                val city = etCity.text.toString()

                data.let {
                    it.address = address
                    it.city = city
                    it.houseNumber = houseNumber
                    it.phoneNumber = phone
                }

                if (phone.isEmpty()) {
                    etPhoneNumber.error = getString(R.string.text_hint_phone)
                    etPhoneNumber.requestFocus()
                } else if (address.isEmpty()) {
                    etAddress.error = getString(R.string.text_hint_address)
                    etAddress.requestFocus()
                } else if (houseNumber.isEmpty()) {
                    etHouseNumber.error = getString(R.string.text_hint_house_no)
                    etHouseNumber.requestFocus()
                } else if (city.isEmpty()) {
                    etCity.error = getString(R.string.text_hint_city)
                    etCity.requestFocus()
                } else {
                    presenter.submitRegister(data, view)
                }
            }
        }
    }

    private fun initDummy() {
        binding.apply {
            etPhoneNumber.setText("085758145632")
            etAddress.setText("Jalan Pemuda")
            etHouseNumber.setText("155")
            etCity.setText("Semarang")
        }
    }

    override fun onRegisterSuccess(authResponse: AuthResponse, view: View) {

        user = authResponse.user

        UserPreference.setToken(authResponse.access_token)

        UserPreference.setUser(gson.toJson(user))

        if (data.filePath == null) {
            Navigation.findNavController(view)
                .navigate(R.id.signupSuccessFragment, null)
            (activity as AuthActivity).toolbarSignUpSuccess()
        } else {
            presenter.submitPhotoRegister(data.filePath!!, view)
        }
    }

    override fun onRegisterPhotoSuccess(data: Any, view: View) {
        if (data is List<*>) {
            val profilePhotoPath: List<String> = data.filterIsInstance<String>()
            user.profile_photo_path = profilePhotoPath.first()
            UserPreference.setUser(gson.toJson(user))
            Navigation.findNavController(view)
                .navigate(R.id.signupSuccessFragment, null)
            (activity as AuthActivity).toolbarSignUpSuccess()
        }
    }

    override fun onRegisterFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initView() {
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

    companion object {
        const val KEY_DATA = "data"
    }

}