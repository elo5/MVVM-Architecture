package com.qingmei2.sample.ui.login

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.fragment.app.viewModels
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.databinding.FragmentLoginBinding
import com.qingmei2.sample.http.Errors
import com.qingmei2.sample.ui.MainActivity
import com.qingmei2.sample.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        binding.mBtnSignIn.setOnClickListener {
            mViewModel.login(binding.tvUsername.text.toString(), binding.tvPassword.text.toString())
        }

        observe(mViewModel.stateLiveData, this::onNewState)
        observe(mViewModel.autoLoginLiveData, this::onAutoLogin)
    }

    private fun onAutoLogin(autoLoginEvent: AutoLoginEvent) {
        if (autoLoginEvent.autoLogin) {
            binding.tvUsername.setText(autoLoginEvent.username, TextView.BufferType.EDITABLE)
            binding.tvPassword.setText(autoLoginEvent.password, TextView.BufferType.EDITABLE)
            mViewModel.login(autoLoginEvent.username, autoLoginEvent.password)
        }
    }

    private fun onNewState(state: LoginViewState) {
        if (state.throwable != null) {
            when (state.throwable) {
                is Errors.EmptyInputError -> "username or password can't be null."
                is HttpException ->
                    when (state.throwable.code()) {
                        401 -> "username or password failure."
                        else -> "network failure"
                    }
                else -> "网络异常，请检查你的网络环境（GitHubAPI访问需要梯子）"
            }.also { str ->
                toast(str)
            }
        }

        binding.mProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.loginInfo != null) {
            MainActivity.launch(requireActivity())
        }
    }
}
