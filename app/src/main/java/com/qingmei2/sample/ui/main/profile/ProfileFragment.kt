package com.qingmei2.sample.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.architecture.core.image.GlideApp
import com.qingmei2.sample.databinding.FragmentProfileBinding
import com.qingmei2.sample.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val mViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        observe(mViewModel.viewStateLiveData, this::onNewState)

        binding.mBtnEdit.setOnClickListener { toast("coming soon...") }
    }

    private fun onNewState(state: ProfileViewState) {
        if (state.throwable != null) {
            // handle throwable.
        }

        if (state.userInfo != null) {
            GlideApp.with(requireContext())
                    .load(state.userInfo.avatarUrl)
                    .apply(RequestOptions().circleCrop())
                    .into(binding.mIvAvatar)

            binding.mTvNickname.text = state.userInfo.name
            binding.mTvBio.text = state.userInfo.bio
            binding.mTvLocation.text = state.userInfo.location
        }
    }
}
