package com.nuhlp.nursehelper.utill.base.binding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseDataBindingFragment<T : ViewDataBinding> : Fragment() {

    abstract val layoutResourceId: Int
    private var _binding : T? = null
    protected val binding get() = _binding!!

    abstract fun onCreateViewAfterBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        onCreateViewAfterBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //Log.d("HomeFragment","destroyView")
    }
}