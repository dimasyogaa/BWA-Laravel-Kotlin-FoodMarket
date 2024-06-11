package com.yogadimas.yogadimas_foodmarketbwa.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
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

        val food = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_DATA, FoodEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(KEY_DATA)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.detailHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bundle = Bundle()
        bundle.putParcelable(KEY_DATA, food)
        navController.setGraph(R.navigation.nav_detail, bundle)


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