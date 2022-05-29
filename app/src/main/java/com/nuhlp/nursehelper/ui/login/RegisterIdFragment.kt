package com.nuhlp.nursehelper.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.utill.base.BaseViewBindingFragment
import com.nuhlp.nursehelper.databinding.FragmentRegisterIdBinding

class RegisterIdFragment : BaseViewBindingFragment<FragmentRegisterIdBinding>() {

    private val _loginViewModel : LoginViewModel by activityViewModels()
  //   private val _IsAvailableId =

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValueToView(binding)
        binding.apply {
            checkIdBtn.setOnClickListener {
                _loginViewModel.checkId(textViewIdRegist.text.toString())
            }
            btnContinueLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerIdFragment_to_registerPwFragment)
            }
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