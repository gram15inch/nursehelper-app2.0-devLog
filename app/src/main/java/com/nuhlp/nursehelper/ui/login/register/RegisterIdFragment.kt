package com.nuhlp.nursehelper.ui.login.register

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentRegisterIdBinding
import com.nuhlp.nursehelper.ui.login.LoginViewModel
import com.nuhlp.nursehelper.utill.base.BaseViewBindingFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class RegisterIdFragment : BaseViewBindingFragment<FragmentRegisterIdBinding>() {

    private val _loginViewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValueToView(binding)
        setListeners()
        /*
        * pw 작성시 유저생성
        * 최종 생성  데이터 저장
        * 로그인 구현 */
    }

    private fun setListeners()= binding.apply {

        checkIdBtn.setOnClickListener {
            val viewText = textViewIdRegist.text.toString()
            if (isValidId(viewText))
                lifecycleScope.launch {
                    setObserverToFlow(_loginViewModel.getAvailableId(viewText))
                }
        }

        btnContinueLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerIdFragment_to_registerPwFragment)
            _loginViewModel.ID = binding.textViewIdRegist.text.toString()
        }


    }

    private suspend fun setObserverToFlow(flow: Flow<Boolean>) = binding.apply {
        flow.collect { //flow
            val isAble = !it
            btnContinueLogin.isEnabled = isAble // 버튼
            when (isAble) {
                true -> {
                    textViewIdRegist.error = null // 에딧 텍스트
                    idTextField.helperText =
                        getString(R.string.edit_text_hint_verify)
                    hideKeyboard()
                }
                false -> {
                    textViewIdRegist.error =
                        getString(R.string.text_edit_disable_id)
                    idTextField.helperText =
                        getString(R.string.edit_text_hint_retry_anather_id)
                }
            }
        }
    }

    private fun isValidId(id: String): Boolean = id.trim() != ""


    private fun getEditTextString(bool: Boolean) = when (bool) {
        true -> ""
        false -> getString(R.string.text_edit_disable_id)
    }

    private fun setValueToView(binding: FragmentRegisterIdBinding) = binding.apply {
        registProgressbar.setProgressCompat(60, true)
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
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterIdBinding {
        return FragmentRegisterIdBinding.inflate(inflater, container, false)
    }

}