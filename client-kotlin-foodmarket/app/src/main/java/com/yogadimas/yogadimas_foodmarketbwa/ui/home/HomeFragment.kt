package com.yogadimas.yogadimas_foodmarketbwa.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentHomeBinding
import com.yogadimas.yogadimas_foodmarketbwa.model.dummy.HomeModel
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var foodList: MutableList<HomeModel> = mutableListOf()

    private var newStateList: MutableList<HomeModel> = mutableListOf()
    private var popularList: MutableList<HomeModel> = mutableListOf()
    private var recomendedList: MutableList<HomeModel> = mutableListOf()

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

        initDataDummy()
        binding.apply {
            rcList.apply {
                adapter = HomeAdapter(foodList) {
                    val detail = Intent(activity, DetailActivity::class.java).putExtra(
                        DetailActivity.KEY_DATA, "it")
                    startActivity(detail)
                }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }


            viewPager.adapter = SectionsPagerAdapter(childFragmentManager, lifecycle)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

    }


    private fun initDataDummy() {
        foodList = mutableListOf(
            HomeModel("Cherry Healthy", "", 5f),
            HomeModel("Burger Tamayo", "", 4f),
            HomeModel("Bakhwan Cihuy", "", 4.5f)
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.text_new_taste,
            R.string.text_popular,
            R.string.text_recommended
        )
    }
}