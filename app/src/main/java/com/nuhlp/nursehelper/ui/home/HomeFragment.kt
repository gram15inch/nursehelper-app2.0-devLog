package com.nuhlp.nursehelper.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.datasource.room.app.DataCount
import com.nuhlp.nursehelper.datasource.room.app.Document
import com.nuhlp.nursehelper.databinding.FragmentHomeBinding
import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.useapp.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

// ** 인덱스 어답터 베이스 프래그먼트로 빼기 **
// 최대한 xml에 livedata를 이용하게 바꾸기
// repository retrofit 사용 가능하게 바꾸기
//
//todo appDB에 place 저장할 테이블 생성 (place에서 사용하는 값만 파라미터로 가지고있는)
//todo 환자 더미 데이터 place 번호 사용하게 생성
//todo 지도 추가해서 내위치 근처 병원 출력
//todo 환자목록 리클라이어뷰 추가
//todo 가장 가까운 병원으로 환자검색후 환자목록 리클라이어뷰 어답터에 주입
//todo 가장 첫번째 환자 검색후 기존 뷰모델 환자 라이브에 주입

class HomeFragment : BaseDataBindingFragment<FragmentHomeBinding>() {

    override var layoutResourceId = R.layout.fragment_home
    private lateinit var _liveAdapter: DocListAdapter
    val ll = "HomeFragment"


    private val _homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModel.Factory(
                activity?.application ?: throw IllegalAccessException("no exist activity")
            )
        ).get(HomeViewModel::class.java)
    }


    @Override
    override fun onCreateViewAfterBinding() {
        binding.viewModel = _homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        testInit() // todo 완성시 삭제
        setRecyclerView()

        _homeViewModel.places.observe(this){
            Log.d(ll,it.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("test","homefragment")
        _homeViewModel.testCall()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setRecyclerView() = binding.indexRecyclerView.apply {
        // ** layoutManager **
        layoutManager = LinearLayoutManager(
            this@HomeFragment.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        // ** Adapter **
        _liveAdapter = DocListAdapter {}
        adapter = _liveAdapter

        // ** deco **
        val mid =  MarginItemDecoration(7)
        addItemDecoration(mid)
        itemAnimator = null

        //todo 장소 라이브 생성후 환자 옵저버달기
        //todo 월개수 라이브로 가져오기
        //todo 라이브월개수로 어답터로 돌릴지 메인에서 추가할지 결정
        // ** data **
        _homeViewModel.patientNoLive.observe(this@HomeFragment){ pNo->
            CoroutineScope(Dispatchers.IO).launch {
                _homeViewModel.getCountPerMonth(pNo).let { list ->
                    Log.d(ll, "$list \n first: ${_homeViewModel.patientNoLive.value}")
                    if (list.isNotEmpty()){
                        index_recyclerView.updateIndex(list, false)
                        if(_homeViewModel.STATE_FIRST) {
                            recyclerViewPick(list.last(), false)
                            _homeViewModel.STATE_FIRST = false
                        }
                    }
                }
            }
        }
        _homeViewModel.setPatientNo(0)


        /*
        _homeViewModel.dayOfMonthCountLive.observe(this@HomeFragment) { dc ->
            countToIndex(dc).let { list ->
                binding.indexRecyclerView.updateIndex(list, false)
                if(_homeViewModel.STATE_FIRST) {
                    if(list.isNotEmpty()) {
                        recyclerViewPick(list.last(), false)
                        _homeViewModel.STATE_FIRST = false
                    }
                }
            }
            Log.d(ll,"call count:$dc")
        }
*/

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
        _liveAdapter.submitList(this@updateAdapter)
    }

    private fun docToIndex(docList: List<Document>): List<Int> {
        val list = mutableListOf<Int>()
        val cal = Calendar.getInstance()
        docList.forEach { dl->
            dl.crtDate.apply {
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
    fun dummy(size:Int):List<Document>{
        val list = mutableListOf<Document>()
        repeat(size){
           list.add (Document(it,0,0,"$it",""))
        }
        return  list.toList()
    }

    private fun testInit() {
        // _homeViewModel.deletAllDoc()

    }


}