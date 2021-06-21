package com.qingmei2.architecture.core.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.qingmei2.architecture.core.base.view.IView
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<B:ViewBinding> : Fragment(), IView {

    private var _binding:B? = null
//    protected abstract val viewModel: ViewModel
    protected val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val superclass: Type = javaClass.genericSuperclass!!
        val aClass = (superclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        try {
            val method: Method = aClass.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            _binding = method.invoke(null, layoutInflater, container, false) as B

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initialize()
//        setupListeners()
//        observe()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    abstract fun initialize()
//
//    abstract fun setupListeners()
//
//    abstract fun observe()

}
