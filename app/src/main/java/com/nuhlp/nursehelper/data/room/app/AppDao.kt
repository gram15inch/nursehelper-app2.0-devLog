package com.nuhlp.nursehelper.data.room.app

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select

@Dao
interface AppDao {


    @Query("select * from Document")
    fun getAllDocFlowList(): Flow<List<Document>>

/*    @Query("select crtDate group by  from Document")
    fun getMonth():Flow<List<Int>>*/

    // CRUD
    @Query("select * from Document where docNo =:documentNo")
    fun getDoc(documentNo: Int):Document
    @Query ("select * from Product where prdNo =:productNo")
    fun getPd(productNo: Int):Product
    @Query("select * from Patient where patNo =:patientNo")
    fun getPt(patientNo: Int):Patient
    @Query("select * from Stock where stkNo =:stockNo")
    fun getStk(stockNo: Int):Stock


    @Insert(onConflict = OnConflictStrategy.REPLACE) fun setDoc(document: Document)
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun setPd(product: Product)
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun setPt(patient: Patient)
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun setStk(stock: Stock)
    
    @Update fun updateDoc(document: Document)
    @Update fun updatePd(product: Product)
    @Update fun updatePt(patient: Patient)
    @Update fun updateStk(stock: Stock)
    
    @Delete fun deleteDoc(document: Document)
    @Delete fun deletePd(product: Product)
    @Delete fun deletePt(patient: Patient)
    @Delete fun deleteStk(stock: Stock)


    // test 전용

    @Query("select * from Document")
    fun getAll(): List<Document>

    @Query("DELETE FROM Document")
    fun deleteAll()


}
