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

    // private var newTasteList:MutableList<Data>? = mutableListOf()
    // private var popularList:MutableList<Data>? = mutableListOf()
    // private var recommendedList:MutableList<Data>? = mutableListOf()
    //
    // fun setData(newTasteListParms : MutableList<Data>?, popularListParms : MutableList<Data>?, recomendedListParms : MutableList<Data>?) {
    //     newTasteList?.clear()
    //     popularList?.clear()
    //     recommendedList?.clear()
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
                //     putParcelableArrayList(MainActivity.KEY_DATA, newTasteList?.let { ArrayList(it) })
                // }
            }

            1 -> {
                fragment = HomePopularFragment()
                // fragment.arguments = Bundle().apply {
                //     putParcelableArrayList(MainActivity.KEY_DATA, popularList?.let { ArrayList(it) })
                // }
            }
            2 -> {
                fragment = HomeRecommendedFragment()
                // fragment.arguments = Bundle().apply {
                //     putParcelableArrayList(
                //         MainActivity.KEY_DATA,
                //         recommendedList?.let { ArrayList(it) })
                // }
            }
        }
        return fragment as Fragment
    }


}