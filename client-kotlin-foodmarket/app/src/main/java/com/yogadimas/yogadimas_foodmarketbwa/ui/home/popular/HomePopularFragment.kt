package com.yogadimas.yogadimas_foodmarketbwa.ui.home.popular

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
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentHomePopularBinding
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

class HomePopularFragment : Fragment() {


    private var _binding: FragmentHomePopularBinding? = null

    private val binding get() = _binding!!


    // private var foodList: MutableList<HomeVerticalModel> = mutableListOf()

    // private var popularList: MutableList<Data> = mutableListOf()

    private val homeFoodTypesViewmodel: HomeFoodTypesViewmodel by activityViewModels()

    private lateinit var adapterPopular: HomeFoodTypesAdapter

    private var snackbar: Snackbar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomePopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapterPopular = HomeFoodTypesAdapter {
            val detail = Intent(requireActivity(), DetailActivity::class.java)
            detail.putExtra(DetailActivity.KEY_DATA, it)
            startActivity(detail)

            // val detail = HomeFragmentDirections.actionNavigationHomeToDetailActivity()
            // detail.keyData = it
            // requireView().findNavController().navigate(detail)
        }
        binding.rcList.apply {
            adapter = adapterPopular
            layoutManager = LinearLayoutManager(activity)
        }

        initPopularFoods()
        homeFoodTypesViewmodel.getAllFoodsByPopular.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> errorState()
                is Result.Loading -> loadingState()
                is Result.Success -> successState(it.data?.data?.data?.toFoodEntityList())
            }
        }


        // homeFoodTypesViewmodel.popularList.observe(viewLifecycleOwner) {data ->
        //     binding.rcList.apply {
        //         adapter = HomeFoodTypesAdapter(data) {
        //             val detail = HomePopularFragmentDirections.actionHomePopularFragmentToDetailActivity()
        //             detail.keyData = it
        //             requireView().findNavController().navigate(detail)
        //         }
        //         layoutManager = LinearLayoutManager(activity)
        //     }
        // }

        // initDataDummy()

        // popularList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        //     arguments?.getParcelableArrayList(MainActivity.KEY_DATA, Data::class.java) ?: mutableListOf()
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


    private fun initPopularFoods() {
        // homeFoodTypesViewmodel.refreshFoods(RefreshAction.Popular)
        homeFoodTypesViewmodel.refreshPopularFoods()

    }

    private fun errorState() {
        showLoading(false)
        // showAlertErrorIsVisible(true)
        if (binding.rcList.visibility == View.GONE) {
            errorLayoutIsVisible(true)
        }
    }

    private fun loadingState() {
        showLoading(true)
        rcIsVisible(false)
        // showAlertErrorIsVisible(false)
        errorLayoutIsVisible(false)
    }

    private fun successState(foodEntityList: List<FoodEntity>?) {
        lifecycleScope.launch(Dispatchers.Main) {
            showLoading(false)
            adapterPopular.submitList(foodEntityList)
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
                getString(R.string.text_popular) + ": " + getString(R.string.text_failed_loading_data_from_internet),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.text_refresh)) { initPopularFoods() }
                .setAnchorView(binding.barrierSnackbar)
        } catch (_: Exception) {
        }


    }

    private fun showAlertErrorIsVisible(boolean: Boolean) {
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
                btnRefresh.setOnClickListener { homeFoodTypesViewmodel.refreshPopularFoods() }
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


    // companion object {
    //     const val FRAGMENT_NAME = "HomePopularFragment"
    // }


}