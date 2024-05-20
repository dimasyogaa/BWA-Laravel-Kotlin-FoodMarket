package com.yogadimas.yogadimas_foodmarketbwa.ui.home.popular

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentHomePopularBinding
import com.yogadimas.yogadimas_foodmarketbwa.model.dummy.HomeVerticalModel
import com.yogadimas.yogadimas_foodmarketbwa.ui.detail.DetailActivity
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.newtaste.HomeNewTasteAdapter

class HomePopularFragment : Fragment() {


    private var _binding: FragmentHomePopularBinding? = null

    private val binding get() = _binding!!


    private var foodList: MutableList<HomeVerticalModel> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomePopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataDummy()


        binding.rcList.apply {
            adapter = HomeNewTasteAdapter(foodList) {
                val detail = Intent(activity, DetailActivity::class.java).putExtra(DetailActivity.KEY_DATA, "it")
                startActivity(detail)
            }
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun initDataDummy() {
        foodList = mutableListOf()
        foodList.add(HomeVerticalModel("Cherry Healthy", "28000", "", 5f))
        foodList.add(HomeVerticalModel("Burger Tamayo", "50000", "", 4f))
        foodList.add(HomeVerticalModel("Bakhwan Cihuy", "70000", "", 4.5f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}