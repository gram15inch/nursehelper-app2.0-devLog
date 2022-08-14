package com.nuhlp.nursehelper.datasource.room.app

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    //** 사용중 **
    @Query("SELECT strftime('%m',crtDate) data , count(*) as count from document where patNo =:patientNo and crtDate like :year group by data")
    fun getCountMFlow(year: String, patientNo: Int): Flow<List<DataCount>>

    @Query("select * from Document where patNo =:patientNo and crtDate like :yearMonth")
    fun getDoc(yearMonth: String, patientNo: Int):List<Document>

    @Query("SELECT strftime('%m',crtDate) data , count(*) as count from document where patNo =:patientNo and crtDate like :year group by data")
    fun getCountPerMonth(year: String, patientNo: Int): List<DataCount>

    @Query("SELECT *  from Patient where patNo like:patNo and bpNo =:bpNo and name like:name and rrn like:rrn ")
    fun getPatients(patNo: String = "%", bpNo: String ="%",name: String ="%",rrn: String="%" ): List<Patient>




    //** 사용 안함 **
    @Query("SELECT strftime('%m',crtDate) data , count(*) as count from document where patNo =:patientNo and crtDate like :year group by data")
    fun getCountM(year: String, patientNo: Int): List<DataCount>
    @Query("SELECT strftime(:expr,crtDate) data , count(*) as count from document where patNo =:patientNo and crtDate like :year group by data")
    fun getCountYM(expr: String, year: String, patientNo: Int): List<DataCount>
    @Query("SELECT strftime(:expr,crtDate) data , count(*) as count from document where patNo =:patientNo and crtDate like :year group by data")
    fun getCountYMFlow(expr: String, year: String, patientNo: Int): Flow<List<DataCount>>


    //** learning test **
    @Query("SELECT *  from document where patNo =:patientNo ")
    fun getAllFlow(patientNo: Int): Flow<List<Document>>


    // CRUD
    @Query("select * from Document where patNo =:patientNo and docNo =:documentNo")
    fun getDoc(documentNo: Int, patientNo: Int):Document

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
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun setBP(businessPlace: BusinessPlace)

    @Update fun updateDoc(document: Document)
    @Update fun updatePd(product: Product)
    @Update fun updatePt(patient: Patient)
    @Update fun updateStk(stock: Stock)
    @Update fun updateBP(businessPlace: BusinessPlace)
    
    @Delete fun deleteDoc(document: Document)
    @Delete fun deletePd(product: Product)
    @Delete fun deletePt(patient: Patient)
    @Delete fun deleteStk(stock: Stock)
    @Delete fun deleteBP(businessPlace: BusinessPlace)


    // test 전용

    @Query("select * from Document")
    fun getAll(): List<Document>

    @Query("DELETE FROM Document")
    fun deleteAll()


}
