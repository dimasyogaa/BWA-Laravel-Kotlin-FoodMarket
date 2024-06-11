package com.yogadimas.yogadimas_foodmarketbwa.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.yogadimas.yogadimas_foodmarketbwa.BuildConfig
import com.yogadimas.yogadimas_foodmarketbwa.R
import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.UserPreference
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.User
import com.yogadimas.yogadimas_foodmarketbwa.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewPager.adapter = SectionsPagerAdapter(childFragmentManager, lifecycle)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

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
                .into(ivPicture)

            tvName.setText(userResponse.name)
            tvEmail.setText(userResponse.email)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.account,
            R.string.title_foodmarket
        )
    }
}