package com.nuhlp.nursehelper.utill.test

import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.datasource.room.app.Patient

object DummyDataUtil {

    val placeList : List<BusinessPlace>  =
        listOf(
            BusinessPlace( // 동백 공간샘 제일 가까운 병원
                7842591,
                "동백내과의원",
                "경기 용인시 기흥구 중동 832-1",
                "경기 용인시 기흥구 동백5로 21-11",
                "031-274-7111"
            ),
            BusinessPlace(
                8921995,
                "동백미즈산부인과",
                "경기 용인시 기흥구 중동 829",
                "경기 용인시 기흥구 동백중앙로 283",
                "031-8006-2030"
            ),
            BusinessPlace(
                14877938,
                "동백쥬니어치과",
                "경기 용인시 기흥구 중동 832-2",
                "경기 용인시 기흥구 동백5로 21-11",
                "031-285-7522"
            ),
            BusinessPlace(
                16172420,
                "늘편한내과의원",
                "경기 용인시 기흥구 중동 832-1",
                "경기 용인시 기흥구 동백죽전대로 444",
                "031-679-0207"
            ),
            BusinessPlace(437574539, "동백동물병원", "경기 용인시 기흥구 중동 829", "경기 용인시 기흥구 동백5로 21-11", ""),
            BusinessPlace(
                7842591,
                "고려외과의원",
                "경기 용인시 기흥구 중동 833",
                "경기 용인시 기흥구 동백중앙로 283",
                "031-693-5501"
            )
        )
    }


