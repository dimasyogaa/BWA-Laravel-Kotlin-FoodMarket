package com.yogadimas.yogadimas_foodmarketbwa.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data
import com.yogadimas.yogadimas_foodmarketbwa.ui.order.inprogress.InprogressFragment
import com.yogadimas.yogadimas_foodmarketbwa.ui.order.pastorders.PastordersFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var inprogressList: ArrayList<Data>? = ArrayList()
    private var pastordersList: ArrayList<Data>? = ArrayList()

    fun setData(inprogressListParms: ArrayList<Data>?, pastordersListParms: ArrayList<Data>?) {
        inprogressList?.clear()
        pastordersList?.clear()
        inprogressList = inprogressListParms
        pastordersList = pastordersListParms
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment?
        when (position) {
            0 -> {
                // fragment = InprogressFragment()
                // val bundle = Bundle()
                // bundle.putParcelableArrayList(OrderFragment.KEY_DATA, inprogressList)
                // Log.e("TAG", "createFragment: $inprogressList", )
                // fragment.arguments = bundle
                // return fragment
                return InprogressFragment.newInstance(position)
            }
            1 -> {
                fragment = PastordersFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList(OrderFragment.KEY_DATA, pastordersList)
                fragment.arguments = bundle
                return fragment
            }
            else -> {
                fragment = InprogressFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList(OrderFragment.KEY_DATA, inprogressList)
                fragment.arguments = bundle
                return fragment
            }
        }
    }


}