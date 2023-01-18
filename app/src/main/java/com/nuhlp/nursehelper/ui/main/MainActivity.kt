package com.nuhlp.nursehelper.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.ActivityMainBinding
import com.nuhlp.nursehelper.repository.TestRepository
import com.nuhlp.nursehelper.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _mainViewModel : MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application))
            .get(MainViewModel::class.java)
    }

    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val _isLogin: LiveData <Boolean> by lazy{_mainViewModel.isLogin}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setIsLoginObserve(isLogin = _isLogin)
        setBottomAppBar()
        setContentView(_binding.root)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option_menu_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout-> _mainViewModel.loginFail()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    // **** 초기화 메서드 *****

    private fun setIsLoginObserve(isLogin: LiveData<Boolean>){
        isLogin.observe(this) { _ ->
            choseActivity(_isLogin.value?:false)
        }
    }

    private fun setBottomAppBar() =_binding.apply {
        setSupportActionBar(appBarBottomDrawer.bottomAppBar)

        appBarBottomDrawer.appBarFloatingActionButton.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        navHostFragment = _binding.appBarBottomDrawer.contentDrawer.mainNavHostFragment.getFragment()
        //navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.boardFragment),
            mainLayout
        )
        /*  appBarConfiguration = AppBarConfiguration(
             navController.graph, mainLayout
        )*/

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // **** 헬퍼 메서드 *****

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
                /*val myToast = Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT)
                myToast.show()*/
            }
        }
    }

}