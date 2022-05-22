package com.nuhlp.nursehelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.nuhlp.nursehelper.MainActivity
import com.nuhlp.nursehelper.MainViewModel
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding
    private val _loginViewModel : LoginViewModel by lazy {
        ViewModelProvider(this, LoginViewModel.Factory(this.application))
            .get(LoginViewModel::class.java)
    }
    private val _isLogin: LiveData<Boolean> by lazy{ _loginViewModel.isLogin}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        

    }

    fun setClickListenerToBtn(){ // 임시 메인이동버튼
        _binding.successBtn.setOnClickListener { _loginViewModel.loginSuccess()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}