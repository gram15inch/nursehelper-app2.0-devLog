package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import com.nuhlp.nursehelper.data.room.UserAccount
import com.nuhlp.nursehelper.data.room.UserDatabase
import com.nuhlp.nursehelper.data.room.getUserDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {
    lateinit var dispatcher: TestDispatcher
    lateinit var room: UserDatabase
    lateinit var context : Context
    lateinit var users :List<UserAccount>

    @Before
    fun setUp(){
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        room = getUserDatabase(context,"testUsers")
        users = listOf(
            UserAccount(-1,"user1","pw1","20220101")
            ,UserAccount(-1,"user2","pw2","20220102")
            ,UserAccount(-1,"user3","pw3","20220103")
            ,UserAccount(-1,"user4","pw4","20220104")
            ,UserAccount(-1,"user5","pw5","20220104"))

            room.userDao.deleteAll()

    }

    @Test
    fun checkAccountNoAutoGenerate(){
        room.userDao.apply {
            assertEquals(-1,users[0].accountNo)
            setUser(users[0])
            assertEquals(1,getUser(users[0].id).accountNo)

            assertEquals(-1,users[1].accountNo)
            setUser(users[1])
            assertEquals(2,getUser(users[1].id).accountNo)

            assertEquals(-1,users[2].accountNo)
            setUser(users[2])
            assertEquals(3,getUser(users[2].id).accountNo)
        }
    }


    @Test
    fun setUser(){
        room.userDao.apply {
            deleteAll()
            setUser(users[0])
            users[0].same(getUser(users[0].id))
            setUser(users[1])
            users[1].same(getUser(users[1].id))
            setUser(users[2])
            users[2].same(getUser(users[2].id))
            assertEquals(0,checkId(users[3].id))
        }
    }
    @Test
    fun delete(){
        room.userDao.apply {
            deleteAll()
           assertEquals(0,getAll().size)
        }

    }
    // 헬퍼메소드
    fun UserAccount.same(user:UserAccount){
        assertEquals(user.id,this.id)
        assertEquals(user.pw,this.pw)
        assertEquals(user.registrationDate,this.registrationDate)
    }

}