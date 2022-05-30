package com.nuhlp.nursehelper.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.nuhlp.nursehelper.data.LoginDataStoreImpl
import com.nuhlp.nursehelper.data.room.UserAccount
import com.nuhlp.nursehelper.data.room.getUserDatabase
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

class LoginViewModel(application: Application) : AndroidViewModel(application){
    private val loginRepository = LoginRepository(LoginDataStoreImpl(application),getUserDatabase(application))
    val isLogin : LiveData<Boolean> = loginRepository.isLogin.asLiveData()
    val isAgreeTerm : LiveData<Boolean> = loginRepository.isAgreeTerms.asLiveData()

    var ID=""
    var PW=""

    fun loginSuccess(){
        viewModelScope.launch{
            loginRepository.setIsLoginToDataStore(true)
        }
    }

    fun agreeTerms(){
        viewModelScope.launch {
            loginRepository.setTermsToDataStore(true)
        }
    }

    fun getAvailableId(userId :String): Flow<Boolean> = loginRepository.getAvailableId(userId)

   fun setUser()= CoroutineScope(Dispatchers.IO).launch {
       val user = createUser()
       if(user.isValid())
           loginRepository.setUserToDatabase(user)
       else
           throw java.lang.IllegalArgumentException("user not Valid :${user.id}/${user.pw}/${user.registrationDate}")
    }

    private fun createUser(): UserAccount {
        val cal = Calendar.getInstance()

        val today = UserAccount.asFormattedDate(cal)
        return UserAccount(ID, PW, today)
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}