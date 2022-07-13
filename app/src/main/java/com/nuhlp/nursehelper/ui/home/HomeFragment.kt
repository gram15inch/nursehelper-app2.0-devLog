package com.nuhlp.nursehelper.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.databinding.FragmentHomeBinding
import com.nuhlp.nursehelper.utill.base.BaseDataBindingFragment
import com.nuhlp.nursehelper.utill.useapp.MarginItemDecoration
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.useapp.TestAdapter
import kotlinx.coroutines.launch


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
        _homeViewModel.docLive.observe(this@HomeFragment){ _liveAdapter.submitList(it) }

        // ** deco **
        val mid =  MarginItemDecoration(5)
        addItemDecoration(mid)
        itemAnimator = null
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