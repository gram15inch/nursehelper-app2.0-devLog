package com.nuhlp.nursehelper.ui.main


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.ActivityMainBinding
import com.nuhlp.nursehelper.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _mainViewModel : MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application))
            .get(MainViewModel::class.java)
    }
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController: NavController

    private val _isLogin: LiveData <Boolean> by lazy{ _mainViewModel.isLogin}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setIsLoginObserve(isLogin = _isLogin)
        setNavigation()
        setContentView(_binding.root)

        /* todo
         로그인 data 테스트
         로그인 ui 테스트
         홈 프래그먼트 만들기*/
    }

    private fun setNavigation() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
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
                val myToast = Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT)
                myToast.show()
            }
        }
    }

}