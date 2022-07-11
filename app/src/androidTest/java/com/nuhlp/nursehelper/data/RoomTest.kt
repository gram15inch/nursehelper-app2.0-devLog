package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.data.room.user.UserAccount
import com.nuhlp.nursehelper.data.room.user.UserDatabase
import com.nuhlp.nursehelper.data.room.user.getUserDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {
    lateinit var dispatcher: TestDispatcher
    lateinit var room: UserDatabase
    lateinit var context: Context
    lateinit var users: List<UserAccount>

    @Before
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        room = getUserDatabase(context, "testUsers")
        users = listOf(
            UserAccount( "user1", "pw1", "20220101"),
            UserAccount( "user2", "pw2", "20220102"),
            UserAccount( "user3", "pw3", "20220103"),
            UserAccount( "user4", "pw4", "20220104"),
            UserAccount( "user5", "pw5", "20220104")
        )
        room.userDao.deleteAll()

    }

    @Test
    fun setAndGetUser() {
        room.userDao.apply {
            setUser(users[0])
            users[0].same(getUser(users[0].id))
            setUser(users[1])
            users[1].same(getUser(users[1].id))
            setUser(users[2])
            users[2].same(getUser(users[2].id))

            assertEquals(null, getUser(users[3].id))


        }
    }

    @Test
    fun checkId(){
        room.userDao.apply {
            setUser(users[0])
            runTest(dispatcher){
                assertEquals(1,getAvailableId(users[0].id).first())
                assertEquals(0,getAvailableId(users[1].id).first())
            }
        }
    }


    @Test
    fun delete() {
        room.userDao.apply {
            deleteAll()
            assertEquals(0, getAll().size)
        }
    }

    @Test
    fun verifyLoginUser(){
        room.userDao.apply {
            assertEquals(countExistedUser(users[0].id,users[0].pw),0)

            setUser(users[0])
            assertEquals(countExistedUser(users[0].id,users[0].pw),1)
            assertEquals(countExistedUser("noExistId",users[0].pw),0)
            assertEquals(countExistedUser(users[0].id,"noExistPw"),0)
        }
    }

    // 헬퍼메소드
    fun UserAccount.same(user: UserAccount) {
        assertEquals(user.id, this.id)
        assertEquals(user.pw, this.pw)
        assertEquals(user.registrationDate, this.registrationDate)
    }

}