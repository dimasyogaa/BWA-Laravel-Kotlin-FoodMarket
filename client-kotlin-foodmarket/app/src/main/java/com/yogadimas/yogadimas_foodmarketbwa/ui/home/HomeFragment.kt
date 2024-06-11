package com.yogadimas.yogadimas_foodmarketbwa.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.yogadimas.yogadimas_foodmarketbwa.BuildConfig
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.UserPreference
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.User
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.HomeResponse
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentHomeBinding
import com.yogadimas.yogadimas_foodmarketbwa.paging.loadingadapter.LoadingStateAdapter
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.DetailActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.viewmodel.FoodDetailViewmodel
import com.yogadimas.yogadimas_foodmarketbwa.ui.factories.ViewModelFactory
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.adapter.HomeAdapter
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.presenter.HomeContract
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.viewmodel.HomeFoodTypesViewmodel
import com.yogadimas.yogadimas_foodmarketbwa.utils.Result
import com.yogadimas.yogadimas_foodmarketbwa.utils.animateFade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    // private var foodList: MutableList<HomeModel> = mutableListOf()

    // private var newStateList: MutableList<Data> = mutableListOf()
    // private var popularList: MutableList<Data> = mutableListOf()
    // private var recomendedList: MutableList<Data> = mutableListOf()
    //
    // private lateinit var presenter: HomePresenter

    private var oneTimeAdapter: Boolean = false
    private var oneTImeError: Boolean = false

    private var listBoolean: MutableList<Boolean> = mutableListOf()
    private val booleanStateFlow = MutableStateFlow(false)

    private var progressDialog: Dialog? = null
    private var snackbar: Snackbar? = null

    private lateinit var homeAdapter: HomeAdapter

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    private val homeFoodTypesViewmodel: HomeFoodTypesViewmodel by activityViewModels {
        ViewModelFactory(requireContext())
    }
    private val foodDetailViewmodel: FoodDetailViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // initDataDummy()


        // initView()
        // presenter = HomePresenter(this@HomeFragment)
        // presenter.getHome()

        initLoading()


        homeAdapter = HomeAdapter {
            val detail = Intent(requireActivity(), DetailActivity::class.java)
            detail.putExtra(DetailActivity.KEY_DATA, it)
            startActivity(detail)
        }
        binding.apply {


            rcList.apply {
                adapter = homeAdapter.apply {
                    withLoadStateFooter(
                        footer = LoadingStateAdapter {
                            homeAdapter.retry()
                        }
                    )
                    addLoadStateListener {

                        val isLoading = it.refresh is LoadState.Loading
                        val isError = it.refresh is LoadState.Error
                        val isPagingDataLoaded = it.prepend.endOfPaginationReached

                        loadingIsVisible(isLoading)

                        lifecycleScope.launch(Dispatchers.IO) {
                            booleanStateFlow.emit(!isLoading && !isError && isPagingDataLoaded)
                        }

                        rcHorizontalIsVisible()

                        if (!isLoading && !isError) {
                            initView(isPagingDataLoaded)
                        }

                        binding.viewDisableLayout.visibility =
                            if (isError) View.VISIBLE else View.GONE

                        if (isLoading) {
                            oneTImeError = false
                        }

                        showSnackBarIsVisible(!isLoading && isError && (!isPagingDataLoaded || binding.viewDisableLayout.isVisible))


                        // show a retry button outside the list when refresh hits an error
                        // retryButton.isVisible = it.refresh is LoadState.Error
                        //
                        // swipeRefreshLayout displays whether refresh is occurring
                        // swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
                        //
                        // show an empty state over the list when loading initially, before items are loaded
                        // emptyState.isVisible = it.refresh is LoadState.Loading && adapter.itemCount == 0
                    }
                }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }


        }
        fetchFoodHorizontal()
        fetchFoodNoPaging()

        val user = UserPreference.getUser()
        val userResponse = Gson().fromJson(user, User::class.java)
        val profilePicture: Any? =
            if (userResponse.profile_photo_path == null) {
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_placeholder_person_account
                )
            } else {
                BuildConfig.BASE_URL_STORAGE + userResponse.profile_photo_path
            }
        Glide.with(requireActivity())
            .load(profilePicture)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfil)


    }


    private fun fetchFoodHorizontal() {
        homeFoodTypesViewmodel.foods.observe(viewLifecycleOwner) {
            homeAdapter.submitData(lifecycle, it)
        }
    }

    private fun fetchFoodNoPaging() {
        homeFoodTypesViewmodel.foodsNoPaging.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {}
            }
        }
    }


    private fun initView(isPagingDataLoaded: Boolean) {


        listBoolean.add(isPagingDataLoaded)
        if (listBoolean[0]) {
            listBoolean.removeAt(0)
        }
        if (listBoolean.contains(true) && !oneTimeAdapter) {
            oneTimeAdapter = true
            foodTypesFragment()
        }


    }

    private fun foodTypesFragment() {
        binding.apply {
            try {
                sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager, lifecycle)
                viewPager.adapter = sectionsPagerAdapter

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            } catch (_: Exception) {
            }

        }
    }


    // private fun initDataDummy() {
    //     foodList = mutableListOf(
    //         HomeModel("Cherry Healthy", "", 5f),
    //         HomeModel("Burger Tamayo", "", 4f),
    //         HomeModel("Bakhwan Cihuy", "", 4.5f)
    //     )
    //
    // }

    private fun initLoading() {


        progressDialog =
            MaterialAlertDialogBuilder(requireContext()).setView(R.layout.dialog_loader).create()
        progressDialog?.let {
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }


    }

    private fun initSnackBar() {
        try {
            snackbar = Snackbar.make(
                requireActivity(),
                binding.root as ViewGroup,
                getString(R.string.text_failed_loading_data_from_internet),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.text_refresh)) {
                oneTImeError = true
                homeAdapter.refresh()
            }.setAnchorView(binding.tabLayout)
        } catch (_: Exception) {
        }


    }


    override fun onHomeSuccess(homeResponse: HomeResponse) {

        // newStateList.clear()
        // popularList.clear()
        // recomendedList.clear()
        //
        // for (a in homeResponse.data.indices) {
        //
        //     val items: List<String> = homeResponse.data[a].types?.split(", ") ?: ArrayList()
        //     for (x in items.indices) {
        //         when {
        //             items[x].equals(NEW_FOOD, true) -> {
        //                 newStateList.add(homeResponse.data[a])
        //                 // homeFoodTypesViewmodel.setFood(NEW_FOOD, newStateList.toList())
        //             }
        //
        //             items[x].equals(POPULAR, true) -> {
        //                 popularList.add(homeResponse.data[a])
        //                 // homeFoodTypesViewmodel.setFood(POPULAR, popularList.toList())
        //             }
        //
        //             items[x].equals(RECOMMENDED, true) -> {
        //                 recomendedList.add(homeResponse.data[a])
        //                 // homeFoodTypesViewmodel.setFood(RECOMMENDED, recomendedList.toList())
        //             }
        //         }
        //     }
        //
        // }
        //
        // binding.apply {
        //
        //     rcList.apply {
        //         // adapter = HomeAdapter(homeResponse.data) {
        //         //     foodDetailViewmodel.setDetailFood(it)
        //         //     val detail = HomeFragmentDirections.actionNavigationHomeToDetailActivity()
        //         //     detail.keyData = it
        //         //     requireView().findNavController().navigate(detail)
        //         // }
        //         // layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //     }
        //
        //     // sectionsPagerAdapter.setData(newStateList, popularList, recomendedList)
        //     viewPager.adapter = sectionsPagerAdapter
        //     TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        //         tab.text = resources.getString(TAB_TITLES[position])
        //     }.attach()
        //
        //
        // }
    }

    override fun onHomeFailed(message: String) {
        // Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        // progressDialog?.show()
    }

    override fun dismissLoading() {
        // progressDialog?.dismiss()
    }

    private fun loadingIsVisible(boolean: Boolean) {
        if (boolean) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun showSnackBarIsVisible(boolean: Boolean) {
        if (!oneTImeError) {
            initSnackBar()
            if (boolean) snackbar?.show() else snackbar?.dismiss()
        }
    }

    private fun rcHorizontalIsVisible() {
        lifecycleScope.launch(Dispatchers.Main) {
            val value = booleanStateFlow.value
            animateFade(binding.rcList, value)
        }
    }


    // override fun onDestroyView() {
    //     super.onDestroyView()
    //     _binding = null
    // }

    override fun onPause() {
        super.onPause()
        // homeFoodTypesViewmodel.deleteFoodAll()

        // new - refresh
        // listBoolean.clear()
        oneTimeAdapter = false
    }

    override fun onResume() {
        super.onResume()

        // new - refresh
        // homeAdapter.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        listBoolean.clear()
        oneTimeAdapter = false
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.text_new_taste,
            R.string.text_popular,
            R.string.text_recommended
        )

        const val NEW_FOOD = "new_food"
        const val POPULAR = "popular"
        const val RECOMMENDED = "recommended"
    }
}