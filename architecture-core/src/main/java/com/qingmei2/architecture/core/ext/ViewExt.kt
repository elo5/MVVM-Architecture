package com.qingmei2.architecture.core.ext

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager

fun ViewPager.initFragments(
    manager: FragmentManager,
    fragments:MutableList<Fragment>
):ViewPager{
    adapter = object : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getCount() = fragments.size
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun saveState(): Parcelable? {
            return null
        }
    }
    return this
}