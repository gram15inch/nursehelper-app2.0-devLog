package com.nuhlp.nursehelper.utill.useapp

import android.util.Log
import com.nuhlp.nursehelper.data.room.app.DataCount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.w3c.dom.Document

class AppProxy {
    companion object{
        fun coInList(co: Flow<List<DataCount>>){
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("HomeViewModel", co.first().toString())
            }
        }
        fun coInLambda(ld:()->Unit){
            CoroutineScope(Dispatchers.IO).launch {
                ld.invoke()
            }
        }
    }
}