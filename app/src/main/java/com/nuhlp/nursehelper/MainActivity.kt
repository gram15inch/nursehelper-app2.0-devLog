package com.nuhlp.nursehelper


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.nuhlp.nursehelper.databinding.ActivityMainBinding
import com.nuhlp.nursehelper.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _mainViewModel : MainViewModel  by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application))
            .get(MainViewModel::class.java)
    }

   private val _isLogin: LiveData <Boolean> by lazy{ _mainViewModel.isLogin}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setIsLoginObserve(isLogin = _isLogin)
        setIsLoginClickTest()
        setContentView(_binding.root)
    }

    private fun setIsLoginClickTest() {
        _binding.textLogin.setOnClickListener{
            _mainViewModel.loginFail()
        }
    }


    private fun setIsLoginObserve(isLogin: LiveData<Boolean>){
        isLogin.observe(this) { _ ->
            choseActivity(_isLogin.value?:false)
        }
    }

    private fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun choseActivity(isLogin : Boolean){

        when(isLogin){
            false->{startLoginActivity()}
            true->{
                val text: String = getString(R.string.isLogin,isLogin)
                _binding.textLogin.text = text
                val myToast = Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT)
                myToast.show()
            }
        }
    }

}