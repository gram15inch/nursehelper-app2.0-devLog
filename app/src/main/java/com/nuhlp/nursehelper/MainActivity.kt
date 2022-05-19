package com.nuhlp.nursehelper


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.data.DataStoreImpl
import com.nuhlp.nursehelper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
   /* private val _mainViewModel : MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application))
            .get(MainViewModel::class.java)
    }*/


    //private var _isLogin: LiveData <Boolean> = _mainViewModel.isLogin
    // todo 지우기
        private lateinit var _isLogin: LiveData <Boolean>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setIsLoginObserve(isLogin = _isLogin)

        //setIsLoginTestBtn()
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

       // _isLogin= DataStoreImpl(this).preferenceFlow.asLiveData()
        return super.onCreateView(parent, name, context, attrs)
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