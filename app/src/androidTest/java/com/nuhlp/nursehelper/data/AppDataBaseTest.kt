package com.nuhlp.nursehelper.data

import android.content.Context
import android.icu.util.Calendar
import androidx.lifecycle.liveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.utill.useapp.AppTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {
    lateinit var dispatcher: TestDispatcher
    lateinit var AppDB: AppDatabase
    lateinit var context: Context
    lateinit var docs: List<Document>
//nuhlpDB
    @Before
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        AppDB = getAppDatabase(context, "testApp")
        docs = listOf(
            Document( 0, 0, 0,"20220101"," 0s contents"),
            Document( 1, 0, 1,"20220101"," 0s contents"),
            Document( 2, 1, 0,"20220101"," 1s contents"),
            Document( 3, 1, 1,"20220101"," 1s contents"),
            Document( 4, 2, 0,"20220101"," 2s contents"),
            Document( 5, 2, 1,"20220101"," 2s contents"),
        )
        AppDB.appDao.deleteAll()
    }

    @Test
    fun setAndGetDocument() {
        AppDB.appDao.apply {
            setDoc(docs[0])
            docs[0].same(getDoc(docs[0].docNo,0))
            setDoc(docs[1])
            docs[1].same(getDoc(docs[1].docNo,0))
            setDoc(docs[2])
            docs[2].same(getDoc(docs[2].docNo,0))
          Assert.assertEquals(null, getDoc(docs[3].docNo,0))
        }
    }

    @Test
    fun updateAndDelete(){
        AppDB.appDao.apply {
            setDoc(docs[0])
            setDoc(docs[1])
            setDoc(docs[2])
            docs[0].same(getDoc(docs[0].docNo,0))
            docs[1].same(getDoc(docs[1].docNo,0))

            val upDoc1 = Document(1,0,3,"20220101","0s contents")
            updateDoc(upDoc1)
            upDoc1.same(getDoc(upDoc1.docNo,0))
            deleteDoc(upDoc1)
            Assert.assertEquals(null, getDoc(upDoc1.docNo,0))

            docs[0].same(getDoc(docs[0].docNo,0))
            docs[1].same(getDoc(docs[1].docNo,2))
        }
    }

    @Test
    fun countYM(){
        AppDB.appDao.apply {
            roomDummy.forEach {
                setDoc(it)
            }
        }
        AppDB.appDao.getCountM("2022%",0).apply { println("ym: ${this[0]}")
            Assert.assertEquals("01", this[0].data)
            Assert.assertEquals(31, this[0].count)
        }
    }
    @Test
    fun countYM2(){
        AppDB.appDao.apply {
            roomDummy.forEach {
                setDoc(it)
            }
        }
        val year=2022
        AppDB.appDao.getCountYM("$year-%m", "$year%",0).apply { println("ym: ${this[0]}")
            Assert.assertEquals("2022-01", this[0].data)
            Assert.assertEquals(31, this[0].count)
        }
    }

    @Test
    fun flowParameterRuntimeChange(){
        var pNo = 0
        var live = AppDB.appDao.getAllFlow(pNo)
        runTest(StandardTestDispatcher()) {
            live.collect(){
               // assertEquals(pNo,it?.get(0).patNo)
                println("=============== ${live.first()}")
            }
        }
        runTest(StandardTestDispatcher()) {
            pNo =0
            Thread.sleep(100)
            pNo =1
            Thread.sleep(100)
        }
    }
    @Test
    fun flowTest(){
        var count = 0
        fun flow1() = flow(){
            repeat(3){
                delay(1000)
                emit(count)
            }
        }
         runBlocking {
            flow1().collect(){
                assertEquals(count++,it)
            }
        }

    }



    // 헬퍼 메소드
    fun Document.same(doc: Document) {
        Assert.assertEquals(doc.docNo, this.docNo)
        Assert.assertEquals(doc.patNo, this.patNo)
        Assert.assertEquals(doc.tmpNo, this.tmpNo)
        Assert.assertEquals(doc.contentsJs, this.contentsJs)
    }

    private val roomDummy :List<Document> by lazy{
        val list = mutableListOf<Document>()
        val time = Calendar.getInstance()
        time.set(Calendar.YEAR,2021)
        time.set(Calendar.MONTH,11)
        time.set(Calendar.DAY_OF_MONTH,25)

        repeat(90){
            val t= AppTime.SDF.format(time.time)
            list.add(Document(it,0,0,t,t))
            time.add(Calendar.DAY_OF_MONTH,1)
        }
        list.toList()
    }
}