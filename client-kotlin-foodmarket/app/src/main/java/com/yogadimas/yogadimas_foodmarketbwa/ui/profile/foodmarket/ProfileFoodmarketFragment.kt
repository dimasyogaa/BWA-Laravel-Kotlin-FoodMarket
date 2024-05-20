package com.yogadimas.yogadimas_foodmarketbwa.ui.profile.foodmarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentProfileFoodmarketBinding
import com.yogadimas.yogadimas_foodmarketbwa.model.dummy.ProfileMenuModel
import com.yogadimas.yogadimas_foodmarketbwa.ui.profile.ProfileMenuAdapter


class ProfileFoodmarketFragment : Fragment() {
    private var _binding: FragmentProfileFoodmarketBinding? = null

    private val binding get() = _binding!!

    private var menuProfileList: MutableList<ProfileMenuModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileFoodmarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataDummy()

        binding.apply {
            rcList.apply {
                adapter = ProfileMenuAdapter(menuProfileList) {
                    Toast.makeText(activity, it.title, Toast.LENGTH_SHORT).show()
                    // val detail = Intent(activity, DetailActivity::class.java).putExtra("data", data)
                    // startActivity(detail)
                }
                layoutManager = LinearLayoutManager(activity)
            }
        }

    }

    private fun initDataDummy() {
        menuProfileList = mutableListOf(
            ProfileMenuModel("Rate Apps"),
            ProfileMenuModel("Help Center"),
            ProfileMenuModel("Privacy & Policy"),
            ProfileMenuModel("Term & Conditions")
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}