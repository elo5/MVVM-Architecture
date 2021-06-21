package com.qingmei2.sample.ui.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.qingmei2.architecture.core.adapter.ViewPagerAdapter
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentMainBinding
import com.qingmei2.sample.ui.main.home.HomeFragment
import com.qingmei2.sample.ui.main.profile.ProfileFragment
import com.qingmei2.sample.ui.main.repos.ReposFragment
import dagger.hilt.android.AndroidEntryPoint

@Suppress("PLUGIN_WARNING")
@SuppressLint("CheckResult")
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val mViewModel: MainViewModel by viewModels()     // not used

    private var isPortMode: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPortMode = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager,
                listOf(HomeFragment(), ReposFragment(), ProfileFragment()))
        binding.viewPager.offscreenPageLimit = 2

        when (isPortMode) {
            true -> bindsPortScreen()
            false -> bindsLandScreen()
        }
    }

    private fun bindsPortScreen() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) = onPageSelectChanged(position)


            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        binding.navigation?.setOnNavigationItemSelectedListener { menuItem ->
            onBottomNavigationSelectChanged(menuItem)
            true
        }
    }

    private fun bindsLandScreen() {
        binding.fabHome?.setOnClickListener { onPageSelectChanged(0) }
        binding.fabRepo?.setOnClickListener { onPageSelectChanged(1) }
        binding.fabProfile?.setOnClickListener { onPageSelectChanged(2) }
    }

    private fun onPageSelectChanged(index: Int) {
        // port-mode
        if (isPortMode) {
            for (position in 0..index) {
                if (binding.navigation?.visibility == View.VISIBLE)
                    binding.navigation?.menu?.getItem(position)?.isChecked = index == position
            }
        } else {
            // land-mode
            if (binding.viewPager.currentItem != index) {
                binding.viewPager.currentItem = index
                if (binding.fabMenu != null && binding.fabMenu.isExpanded)
                    binding.fabMenu.toggle()
            }
        }
    }

    // port-mode only
    private fun onBottomNavigationSelectChanged(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                binding.viewPager.currentItem = 0
            }
            R.id.nav_repos -> {
                binding.viewPager.currentItem = 1
            }
            R.id.nav_profile -> {
                binding.viewPager.currentItem = 2
            }
        }
    }
}
