package com.nuhlp.nursehelper.ui.login

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.ActivityLoginBinding
import com.nuhlp.nursehelper.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding
    private lateinit var navController: NavController
    private val _loginViewModel : LoginViewModel by lazy {
        ViewModelProvider(this, LoginViewModel.Factory(this.application))
            .get(LoginViewModel::class.java)
    }
    lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setContentView(_binding.root)
    }





   /* fun setClickListenerToBtn(){ // 임시 메인이동버튼
        _binding.successBtn.setOnClickListener { _loginViewModel.loginSuccess()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }*/
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
}