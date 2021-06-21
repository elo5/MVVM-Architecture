package com.qingmei2.architecture.core.base.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qingmei2.architecture.core.base.view.IView
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B:ViewBinding> : AppCompatActivity(), IView {

    private lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

//    private fun getViewModelClass(): Class<VM> {
//        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
//        return type as Class<VM>
//    }

    abstract fun getViewBinding(): B
}
