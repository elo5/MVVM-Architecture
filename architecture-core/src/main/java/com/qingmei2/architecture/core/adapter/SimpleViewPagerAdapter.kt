package com.qingmei2.architecture.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       private val fragments: List<Fragment>
) : FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(index: Int): Fragment = fragments.get(index)

    override fun getCount(): Int = fragments.size
}