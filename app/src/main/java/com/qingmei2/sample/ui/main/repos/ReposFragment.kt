package com.qingmei2.sample.ui.main.repos

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.jumpBrowser
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentReposBinding
import com.qingmei2.sample.utils.removeAllAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReposFragment : BaseFragment<FragmentReposBinding>() {

    private val mViewModel: ReposViewModel by viewModels()

    private val mAdapter = ReposPagedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_repos_filter_type)

        binding.mRecyclerView.adapter = mAdapter
        binding.mRecyclerView.removeAllAnimation()

        binds()
    }

    private fun binds() {
        // swipe refresh event.
        binding.mSwipeRefreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }

        // when button was clicked, scrolling list to top.
        binding.fabTop.setOnClickListener {
            binding.mRecyclerView.scrollToPosition(0)
        }

        // menu item clicked event.
        binding.toolbar.setOnMenuItemClickListener {
            onMenuSelected(it)
            true
        }

        // list item clicked event.
        observe(mAdapter.getItemClickEvent(), requireActivity()::jumpBrowser)

        observe(mAdapter.loadStateFlow.asLiveData()) { loadStates ->
            binding.mSwipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        observe(mViewModel.pagedListLiveData) {
            mAdapter.submitData(lifecycle, it)
            binding.mRecyclerView.scrollToPosition(0)
        }
    }

    private fun onMenuSelected(menuItem: MenuItem) {
        val isKeyUpdated = mViewModel.setSortKey(
                when (menuItem.itemId) {
                    R.id.menu_repos_letter -> ReposViewModel.sortByLetter
                    R.id.menu_repos_update -> ReposViewModel.sortByUpdate
                    R.id.menu_repos_created -> ReposViewModel.sortByCreated
                    else -> throw IllegalArgumentException("failure menuItem id.")
                }
        )
        if (isKeyUpdated)
            mAdapter.refresh()
    }
}
