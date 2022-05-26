package com.nuhlp.nursehelper.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.base.BaseViewBindingFragment
import com.nuhlp.nursehelper.databinding.FragmentRegisterIdBinding

class RegisterIdFragment : BaseViewBindingFragment<FragmentRegisterIdBinding>() {




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterIdBinding {
        return FragmentRegisterIdBinding.inflate(inflater,container,false)
    }
}