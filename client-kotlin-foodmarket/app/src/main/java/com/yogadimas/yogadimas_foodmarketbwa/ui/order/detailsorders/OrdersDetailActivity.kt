package com.yogadimas.yogadimas_foodmarketbwa.ui.order.detailsorders

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data
import com.yogadimas.yogadimas_foodmarketbwa.databinding.ActivityOrdersDetailBinding

class OrdersDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrdersDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrdersDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_DATA, Data::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(KEY_DATA)
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.detailOrdersHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bundle = Bundle()
        bundle.putParcelable(KEY_DATA, data)
        navController.setGraph(R.navigation.nav_detail_orders, bundle)
    }

    fun toolbarPayment() {
        binding.includeToolbar.apply {
            root.visibility = View.VISIBLE
            toolbar.apply {
                title = "Payment"
                subtitle = "You deserve better meal"
                navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_000)
                setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

            }
        }
    }



    companion object {
        const val KEY_DATA = "key_data"
    }
}