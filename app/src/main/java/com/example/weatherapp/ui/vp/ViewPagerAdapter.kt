package com.example.weatherapp.ui.vp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.ui.fragment.CurLocationFragment
import com.example.weatherapp.ui.fragment.SearchCityFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return CurLocationFragment()
            1 -> return SearchCityFragment()
        }
        return CurLocationFragment()
    }
}
