package com.nuhlp.nursehelper.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.utill.base.BaseViewBindingFragment
import com.nuhlp.nursehelper.databinding.FragmentRegisterIdBinding

class RegisterIdFragment : BaseViewBindingFragment<FragmentRegisterIdBinding>() {




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValueToView(binding)
        binding.btnContinueLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerIdFragment_to_registerPwFragment)
        }
    }

    private fun setValueToView(binding : FragmentRegisterIdBinding) = binding.apply {
        registProgressbar.setProgressCompat(60,true)
        stateTitle.text = resources.getString(R.string.regist_state_title_almost)
        stateSubTitle.text = resources.getString(R.string.regist_state_sub_title_user_id)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterIdBinding {
        return FragmentRegisterIdBinding.inflate(inflater,container,false)
    }
}