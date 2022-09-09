package com.nuhlp.nursehelper.ui.quick

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.google.android.material.chip.Chip
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentQuickCreationBinding
import com.nuhlp.nursehelper.ui.home.HomeViewModel
import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment

class QuickCreationFragment : BaseDataBindingFragment<FragmentQuickCreationBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_quick_creation

    private val _quickCreationViewModel: QuickCreationViewModel by lazy {
        ViewModelProvider(
            this,
            QuickCreationViewModel.Factory()
        ).get(QuickCreationViewModel::class.java)
    }

    override fun onCreateViewAfterBinding() {
        binding.viewModel =_quickCreationViewModel
        binding.lifecycleOwner = viewLifecycleOwner



        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            registerFilterChanged(checkedIds)
        }
    }

    private fun registerFilterChanged(checkedIds:List<Int>){
        checkedIds.forEach {checkedId->
            getCardView(checkedId)
        }
    }
    private fun getCardView(chipId:Int):Int{
        return when(chipId){
            R.id.chip1->{ R.id.card_view_default_care }
            R.id.chip4->{ R.id.card_view_injection}
            else -> { R.id.card_view_default_care }

        }
    }
}

//todo 어답터에 필요한 데이터 설계
//todo 홀더 만들기 (컨테이너뷰, 칩스, 확장버튼)
//todo 작업별 프래그먼트 만들기
//todo 어답터 만들기