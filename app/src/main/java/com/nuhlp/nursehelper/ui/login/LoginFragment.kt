package com.nuhlp.nursehelper.ui.login


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData

import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.ui.main.MainActivity
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentLoginBinding
import com.nuhlp.nursehelper.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val _loginViewModel: LoginViewModel by activityViewModels()
    lateinit var isLogin: LiveData<Boolean>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        isLogin = _loginViewModel.isLogin
        setListener()

        return binding.root
    }

    private fun setListener() = binding.apply {

        textViewFindLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerTermsFragment)
        }
        btnContinueLogin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (isValidUser()) {
                    _loginViewModel.loginSuccess()
                    startMainActivity()
                } else{
                    _loginViewModel.loginFail()
                    showToast(resources.getString(R.string.wrongUser))
                }

            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private suspend fun isValidUser(): Boolean {
        binding.apply {
            val id = textViewIdLogin.text.toString()
            val pw = textViewPwLogin.text.toString()
            return _loginViewModel.validUser(id, pw)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(str: String) = CoroutineScope(Dispatchers.Main).launch {
        Toast.makeText(context, "$str", Toast.LENGTH_SHORT).show()
    }


}