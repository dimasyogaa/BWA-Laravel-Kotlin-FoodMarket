package com.yogadimas.yogadimas_foodmarketbwa.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.Data
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentDetailBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.viewmodel.FoodDetailViewmodel
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.dataToFoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.foodEntityToData
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.formatPrice
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.replaceLocalhostWithBaseUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// object Data {
//     var data: Data? = null
// }
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val foodDetailViewmodel: FoodDetailViewmodel by activityViewModels()

    var bundle: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // foodDetailViewmodel.getDetailFood().observe(viewLifecycleOwner) {
        //     Log.e("TAG", "onViewCreated: $it", )
        //     initView(it)
        // }

        val foodEntity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(DetailActivity.KEY_DATA, FoodEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(DetailActivity.KEY_DATA)
        }

        initView(foodEntityToData(foodEntity ?: FoodEntity()))


    }


    private fun initView(data: Data) {
        binding.apply {
            Glide.with(requireContext())
                .load(replaceLocalhostWithBaseUrl(data.picturePath))
                .into(ivPoster)

            ratingBar.rating = data.rate?.toFloat() ?: 0F

            tvTitle.text = data.name
            tvDesc.text = data.description
            tvIngredients.text = data.ingredients

            tvTotal.formatPrice(data.price.toString())


            btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
            btnOrderNow.setOnClickListener {
                lifecycleScope.launch {
                    delay(400)
                    val toPaymentFragment =
                        DetailFragmentDirections.actionDetailFragmentToPaymentFragment()
                    toPaymentFragment.food = dataToFoodEntity(data)

                    // mengirim data dengan aman dari null karena safe args
                    it.findNavController().navigate(
                        toPaymentFragment
                    )
                    (activity as DetailActivity).toolbarPayment()
                }
                // it.findNavController().navigate(R.id.action_detailFragment_to_paymentFragment) // animation

                // Navigation.findNavController(it)
                //     .navigate(R.id.paymentFragment, bundle)


            }


        }

    }

    override fun onResume() {
        super.onResume()
        binding.ivPoster.visibility = View.VISIBLE
        (activity as DetailActivity).toolbarDetail()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}