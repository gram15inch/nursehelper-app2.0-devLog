package com.nuhlp.nursehelper.ui.home

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.util.map.BaseMapFragment
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentHomeBinding
import com.nuhlp.nursehelper.datasource.room.app.*
import com.nuhlp.nursehelper.utill.test.DummyDataUtil
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.useapp.MarginItemDecoration
import com.nuhlp.nursehelper.utill.useapp.adapter.PatientsListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

// ** 인덱스 어답터 베이스 프래그먼트로 빼기 **
// 최대한 xml에 livedata를 이용하게 바꾸기
// repository retrofit 사용 가능하게 바꾸기
//todo appDB에 place 저장할 테이블 생성 (place에서 사용하는 값만 파라미터로 가지고있는)
//todo 테이블에 place 초기 데이터(카카오 place) 주입
//todo 환자 더미 데이터 place 번호 사용하게 생성
//todo 지도 추가해서 내위치 근처 병원 출력
//todo 환자목록 리클라이어뷰 추가
//todo 가장 가까운 병원으로 환자검색후 환자목록 리클라이어뷰 어답터에 주입
//todo 가장 첫번째 환자 검색후 기존 뷰모델 환자 라이브에 주입

class HomeFragment : BaseMapFragment<FragmentHomeBinding>() {

    override var layoutResourceId = R.layout.fragment_home
    override val markerResourceId= R.drawable.ic_hospital_marker

    private lateinit var _liveDocAdapter: DocListAdapter
    private lateinit var _livePatAdapter: PatientsListAdapter
    val ll = "HomeFragment"

    private val _homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModel.Factory(
                activity?.application ?: throw IllegalAccessException("no exist activity")
            )
        ).get(HomeViewModel::class.java)
    }

    override fun onUpdateMyLatLng(latLng: LatLng) {
       _homeViewModel.updatePlaces(latLng)
        //Log.d("HomeFragment", "latlng: ${latLng}")

    }


    override fun onCreateViewAfterMap() {
        binding.viewModel = _homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mapUtil = this
        testInit() // todo 완성시 삭제
        setPatientRecyclerView()
        setDocRecyclerView()

        _homeViewModel.run{
            places.observe(this@HomeFragment){ list ->
                Log.d("HomeFragment", "pNo: ${list.size}") //todo 3번씩 불리는 이유 찾기 
                list.minByOrNull { it .distance }?.toBusiness().let {
                    if(it!= null)
                        businessPlace.value = it
                }

            }
            businessPlace.observe(this@HomeFragment){
                updatePatients(it.bpNo)
            }
        }
    }


    private fun setDocRecyclerView() = binding.indexRecyclerView.apply {
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

        // 장소 라이브 생성후 환자 옵저버달기
        // 라이브월개수로 어답터로 돌릴지 메인에서 추가할지 결정 todo(보류)
        // todo 맵뷰로 장소 가져오기
        // ** data **
        _homeViewModel.patientNoLive.observe(this@HomeFragment){ pNo->
            CoroutineScope(Dispatchers.IO).launch {
                _homeViewModel.getCountPerMonth(pNo).let { list ->
                    Log.d(ll, "$list \n firstPatient: ${_homeViewModel.patientNoLive.value}")
                    if (list.isNotEmpty()){
                        index_recyclerView.updateIndex(list, false)
                        recyclerViewPick(list.last(), false)
                    }else
                        _liveDocAdapter.submitList(emptyList())
                }
            }
        }


        // todo 인덱스 클래스 생성시 인덱스 변경 콜백함수를 넣어서 초기화로 변경
        binding.indexRecyclerView.let{
            getPickIndexLive(true).observe(this@HomeFragment) { pickH ->
                recyclerViewPick(pickH,true)
            }
            getPickIndexLive(false).observe(this@HomeFragment){ pickV->
                recyclerViewPick(pickV,false)
            }
        }

    }
    private fun setPatientRecyclerView() = binding.patientsRecyclerView.apply {
        // ** layoutManager **
        layoutManager = LinearLayoutManager(
            this@HomeFragment.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        // ** Adapter **
        _livePatAdapter = PatientsListAdapter { patient ->
            _homeViewModel.patientNoLive.value = patient.patNo
        }
        adapter = _livePatAdapter

        // ** deco **
        val mid =  MarginItemDecoration(7)
        addItemDecoration(mid)
        itemAnimator = null

        _homeViewModel.patients.observe(this@HomeFragment){

            //todo 환자 리스트 넣을 리클라이어뷰 생성
            //todo 리클라이어뷰 인덱스 사용할지 말지 결정
            _livePatAdapter.submitList(it)
            val pl = _homeViewModel.patients.value?.first()?.patNo
            if(pl!=null)
                _homeViewModel.selectPatientNo(pl)
        }


    }

    private fun recyclerViewPick(pick: Int, isHorizontal:Boolean) {
        if(isHorizontal)
            (index_recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pick,0)
        else
            CoroutineScope(Dispatchers.IO).launch {
                _homeViewModel.getDocInMonth(pick).apply {
                    this.updateAdapter()
                }
            }
    }

    private fun List<Document>.updateAdapter() {
        binding.indexRecyclerView.updateIndex(docToIndex(this), true)
        _liveDocAdapter.submitList(this@updateAdapter)
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

    private fun testInit() {
       //createDocumentDummy()
    }


}