package com.nuhlp.nursehelper.ui.login.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.utill.base.binding.BaseViewBindingFragment
import com.nuhlp.nursehelper.databinding.FragmentRegisterPwBinding
import com.nuhlp.nursehelper.ui.login.LoginViewModel


class RegisterPwFragment : BaseViewBindingFragment<FragmentRegisterPwBinding>() {

    private val _loginViewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValueToView()
        setListeners()
    }

    private fun setListeners() = binding.apply {
       /* textLayoutPw.editText!!.addTextChangedListener { text ->
            if(isValidPw(text.toString()))
            {
                if(isMatchingPw()){
                    textLayoutPw.error = null
                }else
                    textLayoutPw.error = "PW가 일치하지 않습니다"
            }
            else
                textLayoutPw.error = "공백없이 4자이상 입력하세요"
        }*/

        listOf(textLayoutPw, textLayoutPwCheck).map { layout ->
            layout.editText!!.addTextChangedListener {text->
                if(isValidPw(text.toString()))
                {
                    if(isMatchingPw()){
                        layout.error = null
                        _loginViewModel.PW.value = text.toString()
                        btnContinueLogin.isEnabled =true
                        Log.d("LoginViewModel","model ID = ${_loginViewModel.ID} PW: ${_loginViewModel.PW}")
                    }else
                        textLayoutPwCheck.error = "PW가 일치하지 않습니다"
                }
                else
                    layout.error = "공백없이 4자이상 입력하세요"
            }
        }

        btnContinueLogin.setOnClickListener {
            _loginViewModel.apply {
                setUser()
            }

            findNavController().navigate(R.id.action_registerPwFragment_to_loginFragment)
        }
    }

    private fun isValidPw(str:String) =
        str.isNotBlank()
                && !(str.contains(" "))
                && str.length >= 4
    private fun isMatchingPw()= binding.run {
            textEditPw.text.toString() == textEditPwCheck.text.toString() == textEditPwCheck.text.toString().isNotBlank()
    }


    private fun setValueToView() = binding.apply {
        registProgressbar.setProgressCompat(90, true)
        stateTitle.text = resources.getString(R.string.regist_state_title_last)
        stateSubTitle.text = resources.getString(R.string.regist_state_sub_title_user_pw)
    }

    fun setTextViewError(view1: TextInputEditText, view2: TextInputEditText) {

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterPwBinding {
        return FragmentRegisterPwBinding.inflate(inflater, container, false)
    }

    fun createUser() {

    }
}