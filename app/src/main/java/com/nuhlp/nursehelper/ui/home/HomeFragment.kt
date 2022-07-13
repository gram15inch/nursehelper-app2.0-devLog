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

        setRecyclerView()
        //setRecyclerView2()
    }

    private fun setRecyclerView2() {

        binding.recycleTest.layoutManager =    LinearLayoutManager(this@HomeFragment.context,
            LinearLayoutManager.HORIZONTAL,false)
        val a = TestAdapter()
        a.submitList(listOf(Document(0,0,0,"00","")))
        binding.recycleTest.adapter = a
    }

    private fun setRecyclerView() =binding.indexRecyclerView.apply {
        // ** index recycler **
        layoutManager = LinearLayoutManager(this@HomeFragment.context,
            LinearLayoutManager.HORIZONTAL,false)

        _liveAdapter = DocListAdapter{}
       _liveAdapter.submitList(dummy(8))
      /*  _homeViewModel.docLive.observe(this@HomeFragment){
            if(it.isNotEmpty())
                _liveAdapter.submitList(it)
        }*/
        adapter = _liveAdapter
        val mid =  MarginItemDecoration(5)
        addItemDecoration(mid)
        itemAnimator = null
    }
    fun dummy(size:Int):List<Document>{
        val list = mutableListOf<Document>()
        repeat(size){
           list.add (Document(it,0,0,"$it",""))
        }
        return  list.toList()
    }


}