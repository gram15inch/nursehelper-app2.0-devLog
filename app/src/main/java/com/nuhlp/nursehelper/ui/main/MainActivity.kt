package com.nuhlp.nursehelper.ui.main


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.ActivityMainBinding
import com.nuhlp.nursehelper.ui.login.LoginActivity
import com.nuhlp.nursehelper.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _mainViewModel : MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application))
            .get(MainViewModel::class.java)
    }

    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val _isLogin: LiveData <Boolean> by lazy{ _mainViewModel.isLogin}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setIsLoginObserve(isLogin = _isLogin)
        setBottomAppBar()

        setContentView(_binding.root)

        /* todo
         앱바 만들기
         앱바 액티비티와 연동
         홈 프래그먼트 만들기*/

    }

    private fun setBottomAppBar() =_binding.apply {
        setSupportActionBar(appBarBottomDrawer.bottomAppBar)

       appBarBottomDrawer.bottomAppBar.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
      /*  appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), mainLayout
        )*/
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController =  _binding.appBarBottomDrawer.contentDrawer.mainNavHostFragment.findNavController()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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