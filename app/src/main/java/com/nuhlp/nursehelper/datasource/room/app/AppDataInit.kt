package com.nuhlp.nursehelper.datasource.room.app

object DummyData{
    fun getPatients():List<Patient>{
        val list = mutableListOf<Patient>()
        val index= 0

        return list
    }
    fun getDocuments():List<Document>{
        val list = mutableListOf<Document>()

        return list
    }
    fun getProduct():List<Product>{
        val list = mutableListOf<Product>()

        return list
    }
    fun getStocks():List<Stock>{
        val list = mutableListOf<Stock>()

        return list
    }
}