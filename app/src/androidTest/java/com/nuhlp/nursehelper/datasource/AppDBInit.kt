package com.nuhlp.nursehelper.datasource

import android.content.Context
import android.icu.util.Calendar
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.datasource.room.app.AppDatabase
import com.nuhlp.nursehelper.datasource.room.app.Document
import com.nuhlp.nursehelper.datasource.room.app.getAppDatabase
import com.nuhlp.nursehelper.utill.useapp.AppTime
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppDBInit {
    lateinit var dispatcher: TestDispatcher
    lateinit var AppDB: AppDatabase
    lateinit var context: Context
    lateinit var docs: List<Document>
    val divider = "================================================"
    @Before
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        AppDB = getAppDatabase(context, "nuhlpDB")
    }

    @Test
    fun Test1(){
        AppDB.appDao.deleteAll()
        roomDummy.forEach {
            AppDB.appDao.setDoc(it)
        }

    }

    @Test fun zero(){
        AppDB.appDao.deleteAll()
    }




    /* 헬퍼 메소드 */
    fun log(str:Any) {
        println("$divider $str")
    }

    private val roomDummy :List<Document> by lazy{
        val list = mutableListOf<Document>()
        val time = Calendar.getInstance()
        time.set(Calendar.YEAR,2022)
        time.set(Calendar.MONTH,0)
        time.set(Calendar.DAY_OF_MONTH,1)

        repeat(700){
            val t= AppTime.SDF.format(time.time)
            list.add(Document(0,if(it<360) 0 else 1 ,0,t,t))
            time.add(Calendar.DAY_OF_MONTH,1)
        }
        list.toList()
    }
}
