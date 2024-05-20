package com.yogadimas.yogadimas_foodmarketbwa.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pageRequest = intent.getIntExtra(KEY_PAGE_REQUEST, 0)
        if (pageRequest == 2) {
            toolbarSignUp()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragmentSignIn, true)
                .build()

            Navigation.findNavController(findViewById(R.id.authHostFragment))
                .navigate(R.id.signupFragment, null, navOptions)
        }
    }

    private fun toolbarSignUp() {
        binding.layoutToolbar.toolbar.apply {
            title = getString(R.string.text_sign_up)
            subtitle = getString(R.string.text_register_and_eat)
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_000)
            setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    fun toolbarSignUpAddress() {
        binding.layoutToolbar.toolbar.apply {
            title = getString(R.string.text_address)
            subtitle = getString(R.string.text_make_sure_it_s_valid)
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_000)
            setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    fun toolbarSignUpSuccess() {
        binding.layoutToolbar.toolbar.visibility = View.GONE
    }

    companion object {
        const val KEY_PAGE_REQUEST = "key_page_request"
    }
}