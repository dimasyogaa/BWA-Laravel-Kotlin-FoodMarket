package com.yogadimas.yogadimas_foodmarketbwa.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ActivitySplashScreenBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.auth.AuthActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashScreenActivity, AuthActivity::class.java))
            finish()
        }
    }
}