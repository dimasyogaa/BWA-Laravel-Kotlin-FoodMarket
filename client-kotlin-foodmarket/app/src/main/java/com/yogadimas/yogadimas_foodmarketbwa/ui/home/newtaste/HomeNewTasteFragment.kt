package com.yogadimas.yogadimas_foodmarketbwa.ui.home.newtaste

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
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentHomeNewTasteBinding
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.DetailActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.adapter.HomeFoodTypesAdapter
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.viewmodel.HomeFoodTypesViewmodel
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers.toFoodEntityList
import com.yogadimas.yogadimas_foodmarketbwa.utils.animateFade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeNewTasteFragment : Fragment() {

    private var _binding: FragmentHomeNewTasteBinding? = null

    private val binding get() = _binding!!

    // private var foodList: MutableList<HomeVerticalModel> = mutableListOf()

    // private var foodList: MutableList<Data> = mutableListOf()

    // private var newStateList: List<Data> = listOf()

    private val homeFoodTypesViewmodel: HomeFoodTypesViewmodel by activityViewModels()

    private lateinit var adapterNewTaste: HomeFoodTypesAdapter

    // private lateinit var sharedPreferencesAppearedOnce: SharedPreferences
    // private lateinit var editorAppearedOnce: SharedPreferences.Editor

    private var newFoodList: MutableList<FoodEntity> = mutableListOf()

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeNewTasteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // sharedPreferencesAppearedOnce = requireActivity().getSharedPreferences(ONE_TIME, Context.MODE_PRIVATE)
        // editorAppearedOnce = sharedPreferencesAppearedOnce.edit()




        adapterNewTaste = HomeFoodTypesAdapter {
            val detail = Intent(requireActivity(), DetailActivity::class.java)
            detail.putExtra(DetailActivity.KEY_DATA, it)
            startActivity(detail)
            // val detail = HomeFragmentDirections.actionNavigationHomeToDetailActivity()
            // detail.keyData = it
            // requireView().findNavController().navigate(detail)
        }
        binding.rcList.apply {
            adapter = adapterNewTaste
            layoutManager = LinearLayoutManager(activity)
        }



        homeFoodTypesViewmodel.getNewFood()
        homeFoodTypesViewmodel.isLoading.observe(viewLifecycleOwner) {
            binding.apply {
                loadingProgress.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    rcIsVisible(false)
                    errorLayoutIsVisible(false)
                }

            }
        }
        homeFoodTypesViewmodel.newFood.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Main) {
                it.data?.data?.toFoodEntityList()?.let { it1 ->
                    newFoodList.clear()
                    newFoodList.addAll(it1)
                 }
                adapterNewTaste.submitList(newFoodList)
                delay(100)
                rcIsVisible(true)
            }


        }
        homeFoodTypesViewmodel.isError.observe(viewLifecycleOwner) {

            val isError = it.first ?: false
            if (isError) {
                rcIsVisible(false)
                if (binding.rcList.visibility == View.GONE) {
                    errorLayoutIsVisible(true)
                }
            }
            // showSnackBarIsVisible(isError)
        }

        // homeFoodTypesViewmodel.getAllFoodsByNewTaste.observe(viewLifecycleOwner) {
        //
        //     when (it) {
        //         is Result.Error -> Toast.makeText(
        //             requireActivity(),
        //             "Gagal Memuat...",
        //             Toast.LENGTH_SHORT
        //         ).show()
        //
        //         is Result.Loading -> {}
        //         is Result.Success -> {
        //             Log.e("TAG", "onViewCreated: ${it.data?.data?.data}")
        //             // adapterNewTaste.submitList()
        //         }
        //     }
        //
        // }

        // newStateList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
    //     foodList.add(HomeVerticalModel("Cherry Healthy", "10000", "", 5f))
    //     foodList.add(HomeVerticalModel("Burger Tamayo", "50000", "", 4f))
    //     foodList.add(HomeVerticalModel("Bakhwan Cihuy", "70000", "", 4.5f))
    //     foodList.add(foodList.last())
    // }


    private fun initSnackBar() {
        try {
            snackbar = Snackbar.make(
                binding.root as ViewGroup,
                getString(R.string.text_new_taste) + ": " + getString(R.string.text_failed_loading_data_from_internet),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.text_refresh)) {
                homeFoodTypesViewmodel.getNewFood()
            }.setAnchorView(binding.barrierSnackbar)
        } catch (_: Exception) {
        }
    }

    private fun showSnackBarIsVisible(boolean: Boolean) {
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
                btnRefresh.setOnClickListener { homeFoodTypesViewmodel.getNewFood() }
            } else {
                root.visibility = View.GONE
            }
        }
    }

    private fun rcIsVisible(boolean: Boolean) {
        animateFade(binding.rcList, boolean)
        binding.rcList.visibility = if (boolean) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        // setOneTimeSharedPreferences(false)
    }

    override fun onDetach() {
        super.onDetach()
    }

    // private fun setOneTimeSharedPreferences(boolean: Boolean) {
    //     editorAppearedOnce.putBoolean(ONE_TIME, boolean)
    //     editorAppearedOnce.apply()
    // }
    //
    // private fun getOneTimeSharedPreferences(): Boolean {
    //     return sharedPreferencesAppearedOnce.getBoolean(ONE_TIME, false)
    // }
    companion object {

        private const val ONE_TIME = "one_time"

    }


}