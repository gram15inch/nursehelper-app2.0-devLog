package com.nuhlp.nursehelper.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.data.room.app.DataCount
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.databinding.FragmentHomeBinding
import com.nuhlp.nursehelper.utill.base.BaseDataBindingFragment
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.MarginItemDecoration
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class HomeFragment : BaseDataBindingFragment<FragmentHomeBinding>() {

    override var layoutResourceId = R.layout.fragment_home
    private lateinit var _liveAdapter :DocListAdapter
    private val _homeViewModel : HomeViewModel by lazy {
        ViewModelProvider(this, HomeViewModel.Factory(activity?.application?: throw IllegalAccessException("no exist activity")) )
            .get(HomeViewModel::class.java)
    }


    @Override
    override fun onCreateViewAfterBinding() {
        binding.viewModel = _homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        testInit() // todo 완성시 삭제
        setRecyclerView()
    }



    private fun setRecyclerView() =binding.indexRecyclerView.apply {
        // ** layoutManager **
        layoutManager = LinearLayoutManager(this@HomeFragment.context,
            LinearLayoutManager.HORIZONTAL,false)

        // ** Adapter **
        _liveAdapter = DocListAdapter{}
        adapter = _liveAdapter

        // ** data **
        _homeViewModel.apply {
            monthLive.observe(this@HomeFragment){
                binding.indexRecyclerView.updateIndex(dataToIndex(it),false) } }
        binding.indexRecyclerView.apply{
            getPickIndexLive(false).observe(this@HomeFragment){pick->
                CoroutineScope(Dispatchers.IO).launch{
                    _homeViewModel.getDocWithM(String.format("%02d",pick)).apply {
                        _liveAdapter.submitList(this)
                        binding.indexRecyclerView.updateIndex(docToIndex(this),true) } }
            }
            getPickIndexLive(true).observe(this@HomeFragment){pick->
                (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pick,0)
            }
        }


        // ** deco **
        val mid =  MarginItemDecoration(7)
        addItemDecoration(mid)
        itemAnimator = null

        // todo test
        CoroutineScope(Dispatchers.IO).launch {
           _liveAdapter.submitList(_homeViewModel.getDocWithM(String.format("%02d", 1)))
        }
        //todo 초기 업데이트시 인덱스 가려지는 문제 해결
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

    private fun dataToIndex(dataList: List<DataCount>):List<Int> {
        val list = mutableListOf<Int>()
        dataList.forEach { dc ->
            dc.data.toInt().apply { if(this!=0) list.add(this) } }
        return list
    }


    // todo **** Test ****
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