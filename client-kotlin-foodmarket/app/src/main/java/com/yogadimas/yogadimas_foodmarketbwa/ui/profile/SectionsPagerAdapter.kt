package com.yogadimas.yogadimas_foodmarketbwa.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yogadimas.yogadimas_foodmarketbwa.ui.profile.account.ProfileAccountFragment
import com.yogadimas.yogadimas_foodmarketbwa.ui.profile.foodmarket.ProfileFoodmarketFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {



    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = ProfileAccountFragment()
                // fragment.arguments = Bundle().apply {
                //     putParcelableArrayList("data", newTasteList)
                // }
            }

            1 -> fragment = ProfileFoodmarketFragment()
        }
        return fragment as Fragment
    }


}