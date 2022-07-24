package com.nuhlp.nursehelper.data.room.app

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class Document constructor(
    @PrimaryKey(autoGenerate = true)
    val docNo: Int, // * document no
    val patNo: Int,  // * patient no
    val tmpNo: Int, // * template no 문서 종류 ex) 경과기록지, 가정간호 동의서... domain 에 정의
    @Nullable
    val crtDate: String, // * create Date : AppTime.SDF
    val contentsJs: String // json
) {

}
@Entity
data class Patient constructor(
    @PrimaryKey(autoGenerate = true)
    val patNo: Int,  // * patient no
    val adrJs : String, // * address (요양원) (json)
    val name: String,
    val rrn: String, // * Resident Registration Number (주민등록번호)
    val gender: String
) {
    fun isValid():Boolean = ( name !="" && rrn !=""&& gender !="")
}

@Entity
data class Stock constructor(
    @PrimaryKey(autoGenerate = true)
    val stkNo: Int,  // * stock no
    val prdNo: String, // * product no
    val count: Int  // * 재고
) {

}

@Entity
data class Product constructor(
    @PrimaryKey(autoGenerate = true)
    val prdNo: Int,  // * stock no
    val typeNo: String, // * 물품종류 ex) 주사기, 거즈, 약품...
    val name: String,// * stock name
    val sizeMain: String,  //* 물품 용량1
    val sizeSub: String,  //* 물품 용량2
) {

}

@Entity
data class DataCount(
    val data :String,
    val count :Int
)

fun List<DataCount>.toInt():List<Int>{
    val list = mutableListOf<Int>()
    this.forEach { dc ->
        dc.data.toInt().apply { if(this!=0) list.add(this) } }
    return list
}

/*@Entity
data class Address(
)*/

//todo today
//todo 요양원 정보로 어떻게 지도에 표시할지
//todo 요양원 정보를 어디까지 저장할지 (어떤정보가 필요한지)
//todo 요양원 정보를 어디서 어떤화면에서 어떤 api로 입력받을지
//todo 요양원 정보를 환자에 json / 테이블을 따로 만들어 저장할지
