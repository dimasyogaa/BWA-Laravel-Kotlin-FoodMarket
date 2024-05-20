package com.yogadimas.yogadimas_foodmarketbwa.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.newtaste.HomeNewTasteFragment
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.popular.HomePopularFragment
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.recommended.HomeRecommendedFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    // var newTasteList:MutableList<HomeModel>? = mutableListOf()
    // var popularList:MutableList<HomeModel>? = mutableListOf()
    // var recommendedList:MutableList<HomeModel>? = mutableListOf()

    // fun setData(newTasteListParms : MutableList<HomeModel>?, popularListParms : MutableList<HomeModel>?, recomendedListParms : MutableList<HomeModel>?) {
    //     newTasteList = newTasteListParms
    //     popularList = popularListParms
    //     recommendedList = recomendedListParms
    //
    // }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = HomeNewTasteFragment()
                // fragment.arguments = Bundle().apply {
                //     putParcelableArrayList("data", newTasteList)
                // }
            }

            1 -> fragment = HomePopularFragment()
            2 -> fragment = HomeRecommendedFragment()
        }
        return fragment as Fragment
    }


}