package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.data.room.user.UserAccount
import com.nuhlp.nursehelper.data.room.user.UserDatabase
import com.nuhlp.nursehelper.data.room.user.getUserDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {
    lateinit var dispatcher: TestDispatcher
    lateinit var AppDB: AppDatabase
    lateinit var context: Context
    lateinit var docs: List<Document>

    @Before
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        AppDB = getAppDatabase(context, "testApp")
        docs = listOf(
            Document( 0, 0, 0," 0s contents"),
            Document( 1, 0, 1," 0s contents"),
            Document( 2, 1, 0," 1s contents"),
            Document( 3, 1, 1," 1s contents"),
            Document( 4, 2, 0," 2s contents"),
            Document( 5, 2, 1," 2s contents"),
        )
        AppDB.appDao.deleteAll()

    }

    @Test
    fun setAndGetDocument() {
        AppDB.appDao.apply {
            setDoc(docs[0])
            docs[0].same(getDoc(docs[0].docNo))
            setDoc(docs[1])
            docs[1].same(getDoc(docs[1].docNo))
            setDoc(docs[2])
            docs[2].same(getDoc(docs[2].docNo))
          Assert.assertEquals(null, getDoc(docs[3].patNo))
        }
    }

    @Test
    fun updateAndDelete(){
        AppDB.appDao.apply {
            setDoc(docs[0])
            setDoc(docs[1])
            setDoc(docs[2])
            docs[0].same(getDoc(docs[0].docNo))
            docs[1].same(getDoc(docs[1].docNo))

            val upDoc1 = Document(1,0,3,"0s contents")
            updateDoc(upDoc1)
            upDoc1.same(getDoc(upDoc1.docNo))
            deleteDoc(upDoc1)
            Assert.assertEquals(null, getDoc(upDoc1.docNo))

            docs[0].same(getDoc(0))
            docs[2].same(getDoc(2))

        }

    }

    /*@Test
    fun checkId(){
        room.userDao.apply {
            setUser(users[0])
            runTest(dispatcher){
                Assert.assertEquals(1,getAvailableId(users[0].id).first())
                Assert.assertEquals(0,getAvailableId(users[1].id).first())
            }
        }
    }*/


    // 헬퍼 메소드
    fun Document.same(doc: Document) {
        Assert.assertEquals(doc.docNo, this.docNo)
        Assert.assertEquals(doc.patNo, this.patNo)
        Assert.assertEquals(doc.tmpNo, this.tmpNo)
        Assert.assertEquals(doc.contentsJs, this.contentsJs)
    }
}