package com.nuhlp.nursehelper.ui.home

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.data.room.app.DataCount
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.databinding.FragmentHomeBinding
import com.nuhlp.nursehelper.utill.base.BaseDataBindingFragment
import com.nuhlp.nursehelper.utill.useapp.AppProxy
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.MarginItemDecoration
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


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

        // ** data **
        _homeViewModel.patientNoLive.observe(this@HomeFragment){ pNo->
            _homeViewModel.getCountPerMonth(pNo).let{ list->
               Log.d(ll,list.toString())
                if(list.isNotEmpty())
                    index_recyclerView.updateIndex(list, false) }
        }
        _homeViewModel.setPatientNo(0)
        // todo 메인에서 접근 백에서 접근 둘다오류 잡기

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