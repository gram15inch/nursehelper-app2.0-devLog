package com.nuhlp.nursehelper.utill.test

import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.datasource.room.app.CareService
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

    val careServiceList : List<CareService> =
        listOf(
        CareService(1010000,"기본간호"),
             CareService(1010001,"건강상태파악 및 관찰"),
             CareService(1010002,"문제확인과 간호진단"),
             CareService(1010003,"활력증후 측정"),
             CareService(1010004,"섭취와 배설량 확인"),
             CareService(1010005,"체위변경"),
             CareService(1010006,"온/냉요법"),
             CareService(1010007,"구강간호"),
             CareService(1010008,"유방간호"),
             CareService(1010009,"복부 마사지"),
             CareService(1010010,"등마사지"),
             CareService(1010011,"기스모(Gismo) 관리"),
             CareService(1010011,"기스모(Gismo) 관리"),
         CareService(1020000,"치료적 간호"),
             CareService(1020001,"비위관 교환 및 관리"),
             CareService(1020002,"산소요법"),
             CareService(1020003,"인공호흡기 관리"),
             CareService(1020004,"상처치료"),
             CareService(1020005,"염증성 처치"),
             CareService(1020006,"봉합사 제거"),
             CareService(1020007,"욕창간호 및 치료"),
             CareService(1020008,"방광세척"),
             CareService(1020009,"도뇨관 삽입/단순도뇨"),
             CareService(1020010,"정체도뇨관 교환 및 간호"),
         CareService(1030000,"검사"),
             CareService(1030001,"검사물 수집"),
             CareService(1030002,"피부반응 검사"),
             CareService(1030003,"경피적산소분압검사"),
             CareService(1030004,"뇨당검사"),
             CareService(1030005,"반정량 혈당검사"),
         CareService(1040000,"투약 및 주사"),
             CareService(1040001,"내복약 투약관리"),
             CareService(1040002,"근육주사"),
             CareService(1040003,"혈관주사"),
             CareService(1040004,"피하주사"),
             CareService(1040005,"수액감시 및 관찰"),
             CareService(1040006,"기타 외용약 및 안약투여"),
             CareService(1050007,"식이요법"),
             CareService(1050008,"위관영양법"),
             CareService(1050009,"보행훈련"),
             CareService(1050010,"투약방법"),
             CareService(1050011,"특수처치 기구 및 장비 사용법"),
             CareService(1050012,"감염증상 판별법"),
             CareService(1050013,"반정량 혈당검사법"),
             CareService(1050014,"피하주사의 자가주사법"),
             CareService(1050015,"고/저 혈당시 응급처치"),
         CareService(1060000,"상담"),
             CareService(1060001,"환자상태상담"),
             CareService(1060002,"재입원 상담"),
             CareService(1060003,"주수발자 및 가족문제"),
             CareService(1060004,"환경관리"),
         CareService(1070000,"의뢰"),
             CareService(1070000,"타 가정간호실시기관 의뢰"),
             CareService(1070000,"보건소 의뢰"),
             CareService(1070000,"종합사회복지관 의뢰"),
             CareService(1070000,"주간보호시설 의뢰"),
             CareService(1070000,"이동목욕센터 의뢰"),
             CareService(1070000,"정신보건센터 의뢰"),
             CareService(1070000,"호스피스 의뢰"),
             CareService(1070000,"노인장기요양기관 의뢰"),
        )
    }


