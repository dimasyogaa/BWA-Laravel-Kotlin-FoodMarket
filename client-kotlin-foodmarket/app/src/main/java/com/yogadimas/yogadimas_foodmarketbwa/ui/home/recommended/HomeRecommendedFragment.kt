package com.yogadimas.yogadimas_foodmarketbwa.ui.home.recommended

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentHomeRecommendedBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.DetailActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.adapter.HomeFoodTypesAdapter
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.viewmodel.HomeFoodTypesViewmodel
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.showLoading
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.toFoodEntityList
import com.yogadimas.yogadimas_foodmarketbwa.utils.Result
import com.yogadimas.yogadimas_foodmarketbwa.utils.animateFade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeRecommendedFragment : Fragment() {

    private var _binding: FragmentHomeRecommendedBinding? = null

    private val binding get() = _binding!!

    // private var foodList: MutableList<HomeVerticalModel> = mutableListOf()

    // private var foodList: MutableList<Data> = mutableListOf()
    //
    // private var recomendedList: MutableList<Data> = mutableListOf()

    private val homeFoodTypesViewmodel: HomeFoodTypesViewmodel by activityViewModels()

    private lateinit var adapterRecommended: HomeFoodTypesAdapter

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeRecommendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterRecommended = HomeFoodTypesAdapter {
            val detail = Intent(requireActivity(), DetailActivity::class.java)
            detail.putExtra(DetailActivity.KEY_DATA, it)
            startActivity(detail)
            // val detail = HomeFragmentDirections.actionNavigationHomeToDetailActivity()
            // detail.keyData = it
            // requireView().findNavController().navigate(detail)
        }

        initRecommendedFoods()
        binding.rcList.apply {
            adapter = adapterRecommended
            layoutManager = LinearLayoutManager(activity)
        }


        homeFoodTypesViewmodel.getAllFoodsByRecommended.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error ->errorState()
                is Result.Loading -> loadingState()
                is Result.Success -> successState(it.data?.data?.data?.toFoodEntityList())
            }

        }

        // homeFoodTypesViewmodel.recommendedList.observe(viewLifecycleOwner) {data ->
        //     binding.rcList.apply {
        //         adapter = HomeFoodTypesAdapter(data) {
        //             val detail = HomeRecommendedFragmentDirections.actionHomeRecommendedFragmentToDetailActivity()
        //             detail.keyData = it
        //             requireView().findNavController().navigate(detail)
        //         }
        //         layoutManager = LinearLayoutManager(activity)
        //     }
        // }

        // initDataDummy()

        // recomendedList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        //     arguments?.getParcelableArrayList(MainActivity.KEY_DATA, Data::class.java)
        //         ?: mutableListOf()
        // } else {
        //     @Suppress("DEPRECATION")
        //     arguments?.getParcelableArrayList(MainActivity.KEY_DATA) ?: mutableListOf()
        // }

    }

    // private fun initDataDummy() {
    //     foodList = mutableListOf()
    //     foodList.add(HomeVerticalModel("Cherry Healthy", "28000", "", 5f))
    //     foodList.add(HomeVerticalModel("Burger Tamayo", "50000", "", 4f))
    //     foodList.add(HomeVerticalModel("Bakhwan Cihuy", "70000", "", 4.5f))
    // }

    private fun initRecommendedFoods(){
        homeFoodTypesViewmodel.refreshRecommendedFoods()
    }

    private fun errorState() {
        showLoading(false)
        // showSnackBarErrorIsVisible(true)
        if (binding.rcList.visibility == View.GONE) {
            errorLayoutIsVisible(true)
        }
    }

    private fun loadingState() {
        showLoading(true)
        rcIsVisible(false)
        // showSnackBarErrorIsVisible(false)
        errorLayoutIsVisible(false)
    }

    private fun successState(foodEntityList: List<FoodEntity>?){
        lifecycleScope.launch(Dispatchers.Main) {
            showLoading(false)
            adapterRecommended.submitList(foodEntityList)
            delay(100)
            rcIsVisible(true)
        }
    }



    private fun showLoading(boolean: Boolean) {
        showLoading(binding.loadingProgress, boolean)
    }

    private fun initSnackBar() {
        try {
            snackbar = Snackbar.make(
                binding.root as ViewGroup,
                getString(R.string.text_recommended) + ": " + getString(R.string.text_failed_loading_data_from_internet),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.text_refresh)) {
                homeFoodTypesViewmodel.getNewFood()
            }.setAnchorView(binding.barrierSnackbar)
        } catch (_: Exception) {
        }


    }

    private fun showSnackBarErrorIsVisible(boolean: Boolean) {
        initSnackBar()
        binding.barrierSnackbar.apply {
            if (boolean) {
                snackbar?.show()
            } else {
                snackbar?.dismiss()
            }
        }
    }

    private fun errorLayoutIsVisible(boolean: Boolean) {
        binding.errorState.apply {
            if (boolean) {
                root.visibility = View.VISIBLE
                btnRefresh.setOnClickListener { homeFoodTypesViewmodel.refreshRecommendedFoods() }
            } else {
                root.visibility = View.GONE
            }
        }
    }

    private fun rcIsVisible(boolean: Boolean) {
        animateFade(binding.rcList, boolean)
        binding.rcList.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val FRAGMENT_NAME = "HomeRecommendedFragment"
    }


}