package com.qingmei2.sample.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qingmei2.architecture.core.base.view.activity.BaseActivity
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.apply {
            findFragmentByTag(TAG) ?: beginTransaction()
                .add(R.id.flContainer, LoginFragment(), TAG)
                .commitAllowingStateLoss()
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)
}

//class LoginActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//        initFragment()
//    }
//
//    private fun initFragment() {
//        supportFragmentManager.apply {
//            findFragmentByTag(TAG) ?: beginTransaction()
//                    .add(R.id.flContainer, LoginFragment(), TAG)
//                    .commitAllowingStateLoss()
//        }
//    }
//
//    companion object {
//        private const val TAG = "LoginFragment"
//    }
//}
