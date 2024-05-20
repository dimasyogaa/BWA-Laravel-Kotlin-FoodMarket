package com.yogadimas.yogadimas_foodmarketbwa.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        toolbarDetail()

    }

    fun toolbarPayment() {
        binding.includeToolbar.toolbar.apply {
            visibility = View.VISIBLE
            title = getString(R.string.text_payment)
            subtitle = getString(R.string.text_you_deserve_better_meal)
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_000)
            setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }

    }

    fun toolbarDetail() {
        binding.includeToolbar.toolbar.visibility = View.GONE
    }


    companion object {
        const val KEY_DATA = "key_data"
    }
}