package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.request.RegisterRequest
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentSignupBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.AuthActivity


class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private var filePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDummy()
        initListener()


    }

    private fun initListener() {
        if (filePath != null) {
            setImage(filePath)
        }

        binding.apply {
            ivProfil.apply {
                setOnClickListener {
                    ImagePicker.with(this@SignupFragment)
                        .compress(2048) //Final image size will be less than 2 MB(Optional)
                        .galleryOnly()
                        .createIntent { intent ->
                            startForProfileImageResult.launch(intent)
                        }
                }
            }





            btnContinue.setOnClickListener {


                val fullname = etFullname.text.toString()
                val email = etEmail.text.toString()
                val pass = etPassword.text.toString()

                if (fullname.isEmpty()) {
                    etFullname.error = getString(R.string.text_hint_full_name)
                    etFullname.requestFocus()
                } else if (email.isEmpty()) {
                    etEmail.error = getString(R.string.text_hint_email_address)
                    etEmail.requestFocus()
                } else if (pass.isEmpty()) {
                    etPassword.error = getString(R.string.text_hint_password)
                    etPassword.requestFocus()
                } else {
                    val data = RegisterRequest(
                        fullname,
                        email,
                        pass,
                        pass,
                        "", "", "", "",
                        filePath
                    )

                    val bundle = Bundle()
                    bundle.putParcelable(SignupAddressFragment.KEY_DATA, data)

                    Navigation.findNavController(it)
                        .navigate(R.id.signupAddressFragment, bundle)
                    (activity as AuthActivity).toolbarSignUpAddress()
                }
            }

        }
    }

    private fun initDummy() {
        binding.apply {
            etFullname.setText("Yoga Dimas")
            etEmail.setText("admin1@yogadimas.com")
            etPassword.setText("12345678")
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                filePath = data?.data
                setImage(filePath)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    private fun setImage(uri: Uri?) {
        Glide.with(this)
            .load(uri)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfil)
    }

    override fun onResume() {
        super.onResume()
        (activity as AuthActivity).toolbarSignUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}