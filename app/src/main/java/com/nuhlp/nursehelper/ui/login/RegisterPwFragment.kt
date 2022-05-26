package com.nuhlp.nursehelper.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.base.BaseViewBindingFragment
import com.nuhlp.nursehelper.databinding.FragmentRegisterPwBinding


class RegisterPwFragment : BaseViewBindingFragment<FragmentRegisterPwBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setValueToView(binding)
        binding.btnContinueLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerPwFragment_to_loginFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    private fun setValueToView(binding: FragmentRegisterPwBinding) = binding.apply {
        registProgressbar.setProgressCompat(90,true)
        stateTitle.text = resources.getString(R.string.regist_state_title_last)
        stateSubTitle.text = resources.getString(R.string.regist_state_sub_title_user_pw)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterPwBinding {
        return FragmentRegisterPwBinding.inflate(inflater,container,false)
    }
}