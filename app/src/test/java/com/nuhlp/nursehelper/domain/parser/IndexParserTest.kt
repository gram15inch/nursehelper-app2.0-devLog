package com.nuhlp.nursehelper.domain.parser

import com.nuhlp.nursehelper.data.room.app.DataCount
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IndexParserTest {

    val DataList = listOf(DataCount("01",31)
    ,DataCount("02",29)
    ,DataCount("03",27))

    @Test
    fun dataToIndexTest(){
        val DataToIndex = dataToIndex(DataList)
        val PureIndex = listOf(1,2,3)
        assertEquals(PureIndex == DataToIndex ,true)
    }

    @Test
    fun eqList(){
        val list1 = listOf(1,2,3)
        val list2 = listOf(1,2,4)
        val list3 = listOf(1,2,3,4)
        val list4 = listOf(1,2,3)

        assertEquals(false,list1==list2)
        assertEquals(false,list1==list3)
        assertEquals(true,list1==list4)
    }

}

private fun dataToIndex(dataList: List<DataCount>):List<Int> {
    val list = mutableListOf<Int>()
    dataList.forEach { dc ->
        dc.data.toInt().apply { if(this!=0) list.add(this) } }
    return list
}