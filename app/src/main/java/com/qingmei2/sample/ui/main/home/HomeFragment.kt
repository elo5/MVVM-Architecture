package com.qingmei2.sample.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.jumpBrowser
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentHomeBinding
import com.qingmei2.sample.ui.search.SearchActivity
import com.qingmei2.sample.utils.removeAllAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mViewModel: HomeViewModel by viewModels()


    private val mAdapter: HomePagedAdapter = HomePagedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_home_search)
        binds()

        binding.mRecyclerView.adapter = mAdapter
        binding.mRecyclerView.removeAllAnimation()
    }

    private fun binds() {
        // when button was clicked, scrolling list to top.
        binding.fabTop.setOnClickListener {
            binding.mRecyclerView.scrollToPosition(0)
        }

        // swipe refresh event.
        binding.mSwipeRefreshLayout.setOnRefreshListener(mAdapter::refresh)

        // search menu item clicked event.
        binding.toolbar.setOnMenuItemClickListener {
            SearchActivity.launch(requireActivity())
            true
        }

        // list item clicked event.
        observe(mAdapter.observeItemEvent(), requireActivity()::jumpBrowser)

        observe(mAdapter.loadStateFlow.asLiveData()) { loadStates ->
            binding.mSwipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        observe(mViewModel.eventListLiveData) {
            mAdapter.submitData(lifecycle, it)
            binding.mRecyclerView.scrollToPosition(0)
        }
    }
}
