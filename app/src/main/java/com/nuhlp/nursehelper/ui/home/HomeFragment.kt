package com.nuhlp.nursehelper.ui.home

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.nuhlp.nursehelper.utill.base.map.BaseMapFragment
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentHomeBinding
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.datasource.room.app.*
import com.nuhlp.nursehelper.utill.test.DummyDataUtil
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.Constants
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.useapp.MarginItemDecoration
import com.nuhlp.nursehelper.utill.useapp.adapter.PatientsListAdapter
import java.util.*

// ** 인덱스 어답터 베이스 프래그먼트로 빼기 **
// 최대한 xml에 livedata를 이용하게 바꾸기
// repository retrofit 사용 가능하게 바꾸기
// appDB에 place 저장할 테이블 생성 (place에서 사용하는 값만 파라미터로 가지고있는)
// 테이블에 place 초기 데이터(카카오 place) 주입
// 환자 더미 데이터 place 번호 사용하게 생성
// 지도 추가해서 내위치 근처 병원 출력
// 환자목록 리클라이어뷰 추가
// 가장 가까운 병원으로 환자검색후 환자목록 리클라이어뷰 어답터에 주입
// 가장 첫번째 환자 검색후 기존 뷰모델 환자 라이브에 주입

class HomeFragment : BaseMapFragment<FragmentHomeBinding>(),HomeUtil {

    override val layoutResourceId = R.layout.fragment_home
    override val markerResourceId= R.drawable.ic_hospital_marker

    private lateinit var _liveDocAdapter: DocListAdapter
    private lateinit var _livePatAdapter: PatientsListAdapter
    val ll = "HomeFragment"

    private val _homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModel.Factory()
        ).get(HomeViewModel::class.java)
    }

    override fun onCreateViewAfterMap() {
        binding.viewModel = _homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mapUtil = this
        binding.homeUtil = this
    }


    override fun onUpdateMyLatLng(latLng: LatLng) {
        _homeViewModel.updatePlaces(latLng)
    }

    override fun setPatientRecyclerView(view:RecyclerView) {
        view.apply {
            // ** layoutManager **
            layoutManager = LinearLayoutManager(
                this@HomeFragment.context,
                LinearLayoutManager.HORIZONTAL, false
            )

            // ** Adapter **
            _livePatAdapter = PatientsListAdapter { patient ->
                _homeViewModel.updatePatient(patient)
            }
            adapter = _livePatAdapter

            // ** deco **
            val mid =  MarginItemDecoration(7)
            addItemDecoration(mid)
            itemAnimator = null
        }
    }
    override fun setDocumentRecyclerView(view:RecyclerView) {
        view.apply {

        // ** layoutManager **
        layoutManager = LinearLayoutManager(
            this@HomeFragment.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        // ** Adapter **
        _liveDocAdapter = DocListAdapter {}
        adapter = _liveDocAdapter

        // ** deco **
        val mid =  MarginItemDecoration(7)
        addItemDecoration(mid)
        itemAnimator = null

        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tagPlace = marker.tag as Place
        _homeViewModel.updateBusinessPlace(tagPlace.toBusiness())
        return true
    }

    override fun onResume() {
        super.onResume()
        if(isMapReady()){
            updateLocation()
            Log.d("HomeFragment","onResume!")
        }
    }




     /* **** Test **** */

    private val roomDummy :List<Document> by lazy{
        val list = mutableListOf<Document>()
        val time = android.icu.util.Calendar.getInstance()
        time.set(android.icu.util.Calendar.YEAR,2022)
        time.set(android.icu.util.Calendar.MONTH,0)
        time.set(android.icu.util.Calendar.DAY_OF_MONTH,1)

        repeat(365){
            val t= AppTime.SDF.format(time.time)
            list.add(Document(it,0,0,t,t))
            time.add(android.icu.util.Calendar.DAY_OF_MONTH,1)
        }
        list.toList()
    }
    fun createDocumentDummy(){
        val list = mutableListOf<Document>()
        val time = android.icu.util.Calendar.getInstance()
        time.set(android.icu.util.Calendar.YEAR,2022)
        time.set(android.icu.util.Calendar.MONTH,0)
        time.set(android.icu.util.Calendar.DAY_OF_MONTH,1)

        val pNo = 6
        val dNo = 1 + 364
            for(day in dNo..dNo+364) {
                val t = AppTime.SDF.format(time.time)
                val doc = Document(day, pNo, 0, t, "$pNo's document$day")
                list.add(doc)
                time.add(android.icu.util.Calendar.DAY_OF_MONTH, 1)
                _homeViewModel.setDoc(doc)
            }
    }
    fun createPatientDummy() {
        val list = mutableListOf<Patient>()
        repeat(10){
            val no =  if (it <= 5) 0 else 1
            val bp = DummyDataUtil.placeList[no].bpNo
            list.add(Patient(it,bp,"name1$it","19010101","M"))
        }
        list.forEach {
            _homeViewModel.setPatient(it)
        }
    }
    fun createPlaceDummy(){
        val list = mutableListOf<BusinessPlace>()
        DummyDataUtil.placeList.forEachIndexed(){no,item->
            list.add(item)
        }
        list.forEach {
            _homeViewModel.setBusinessPlace(it)
        }
    }


    private fun docToIndex(docList: List<Document>): List<Int> {
        val list = mutableListOf<Int>()
        val cal = Calendar.getInstance()
        docList.forEach { doc->
            doc.crtDate.apply {
                cal.time = AppTime.SDF.parse(this)
                list.add(cal.get(Calendar.DAY_OF_MONTH))
            }
        }
        return list
    }
    private fun countToIndex(dataList: List<DataCount>):List<Int> {
        val list = mutableListOf<Int>()
        dataList.forEach { dc ->
            dc.data.toInt().apply { if(this!=0) list.add(this) } }
        return list
    }

}