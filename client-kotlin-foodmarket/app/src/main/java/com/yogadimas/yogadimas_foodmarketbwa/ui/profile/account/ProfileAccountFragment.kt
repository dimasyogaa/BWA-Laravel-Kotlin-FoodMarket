package com.yogadimas.yogadimas_foodmarketbwa.ui.profile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentProfileAccountBinding
import com.yogadimas.yogadimas_foodmarketbwa.model.dummy.ProfileMenuModel
import com.yogadimas.yogadimas_foodmarketbwa.ui.profile.ProfileMenuAdapter


class ProfileAccountFragment : Fragment() {

    private var _binding: FragmentProfileAccountBinding? = null

    private val binding get() = _binding!!

    private var menuProfileList: MutableList<ProfileMenuModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileAccountBinding.inflate(inflater, container, false)
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
            ProfileMenuModel("Edit Profile"),
            ProfileMenuModel("Home Address"),
            ProfileMenuModel("Security"),
            ProfileMenuModel("Payments")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}