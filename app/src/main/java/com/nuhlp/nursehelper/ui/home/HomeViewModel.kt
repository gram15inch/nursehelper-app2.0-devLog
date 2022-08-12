package com.nuhlp.nursehelper.ui.home

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.*
import com.nuhlp.nursehelper.datasource.room.app.Document
import com.nuhlp.nursehelper.utill.base.recyclerview.BaseRecyclerViewModel
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.Constants

class HomeViewModel (application: Application) : BaseRecyclerViewModel(application) {



    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")

                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

   /* **** test **** */
    private var countT = 0
    fun testCall(){
     //  updatePlaces(Constants.LATLNG_DONGBAEK)
    }
    fun btnClick(){
        test()
    }

    private val roomDummy :List<Document> by lazy{
        val list = mutableListOf<Document>()
        val time = Calendar.getInstance()
        time.set(Calendar.YEAR,2022)
        time.set(Calendar.MONTH,0)
        time.set(Calendar.DAY_OF_MONTH,1)

        repeat(365){
            val t= AppTime.SDF.format(time.time)
            list.add(Document(it,0,0,t,t))
            time.add(Calendar.DAY_OF_MONTH,1)
        }
       list.toList()
    }
}


