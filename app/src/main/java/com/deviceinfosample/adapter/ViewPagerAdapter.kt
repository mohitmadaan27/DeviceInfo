package com.deviceinfosample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.deviceinfosample.fragment.MainFragment
import java.util.ArrayList

class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(
    manager!!
) {
    var titles: MutableList<String> = ArrayList()
    fun addTitle(title: String) {
        titles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return MainFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}