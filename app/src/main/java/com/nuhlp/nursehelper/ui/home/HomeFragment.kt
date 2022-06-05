package com.nuhlp.nursehelper.ui.home

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentHomeBinding
import com.nuhlp.nursehelper.ui.main.MainViewModel
import com.nuhlp.nursehelper.utill.base.BaseViewBindingFragment


class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding>() {
    private val _homeViewModel : HomeViewModel by lazy {
        ViewModelProvider(this, HomeViewModel.Factory(activity?.application?: throw IllegalAccessException("no exist activity")) )
            .get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater,container,false)
    }

}