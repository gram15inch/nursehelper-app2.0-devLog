package com.nuhlp.nursehelper.ui.login

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.NurseHelperApplication
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentRegisterIdBinding
import com.nuhlp.nursehelper.utill.base.BaseViewBindingFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RegisterIdFragment : BaseViewBindingFragment<FragmentRegisterIdBinding>() {

    private val _loginViewModel : LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValueToView(binding)

        binding.apply {
            checkIdBtn.setOnClickListener {
                lifecycleScope.launch{
                    if(textViewIdRegist.text.toString().trim() != "")
                        _loginViewModel.getAvailableId(textViewIdRegist.text.toString()).collect {
                        val isAble = !it
                        binding.btnContinueLogin.isEnabled = isAble
                        when(isAble){
                            true->{  textViewIdRegist.error = null
                                    idTextField.helperText = getString(R.string.edit_text_hint_verify)
                                    hideKeyboard()
                                 }
                            false ->{ textViewIdRegist.error = getString(R.string.text_edit_disable_id)
                                idTextField.helperText = getString(R.string.edit_text_hint_retry_anather_id)
                            }
                            /*위 코드 정리
                            * pw 작성시 유저생성
                            * 최종 생성  데이터 저장
                            * 로그인 구현*/

                        }

                    }
                }
            }

            btnContinueLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerIdFragment_to_registerPwFragment)
                _loginViewModel.ID = binding.textViewIdRegist.text.toString()
            }
        }

    }

    private fun getEditTextString(bool:Boolean) = when(bool){
        true-> ""
        false-> getString(R.string.text_edit_disable_id)
    }
    private fun setValueToView(binding : FragmentRegisterIdBinding) = binding.apply {
        registProgressbar.setProgressCompat(60,true)
        stateTitle.text = resources.getString(R.string.regist_state_title_almost)
        stateSubTitle.text = resources.getString(R.string.regist_state_sub_title_user_id)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterIdBinding {
        return FragmentRegisterIdBinding.inflate(inflater,container,false)
    }

}