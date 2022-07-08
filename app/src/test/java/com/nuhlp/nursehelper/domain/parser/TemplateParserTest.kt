package com.nuhlp.nursehelper.domain.parser

import com.nuhlp.nursehelper.domain.DATA_HOME_NURSING_PROGRESS_REPORT
import com.nuhlp.nursehelper.domain.Template
import com.nuhlp.nursehelper.learningtest.Person
import com.nuhlp.nursehelper.learningtest.TypeTest1
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TemplateParserTest {

    object Template5 {
        val template = DATA_HOME_NURSING_PROGRESS_REPORT("sangmun5")
        fun json() = """ {"progressContents":"${template.progressContents}"} """.trim()
    }

    @Before
    fun initData(){


    }

    @Test // json -> data class (Factory 멤버변수 사용)
    fun jsonToClassWithProperty(){
        val json = Template5.json()
        val dataClass = Template5.template
        val jsonToDataClass = TemplateParser.fromJson(json,dataClass::class.java)
        Assert.assertEquals(dataClass, jsonToDataClass.progressContents)
    }

    @Test // data class -> json (Factory 멤버변수 사용)
    fun classToJsonWithProperty(){
        val json = Template5.json()
        val dataClass = Template5.template
        val dataClassToJson = TemplateParser.toJson(dataClass)
        Assert.assertEquals(json, dataClassToJson)
    }
}