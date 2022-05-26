package com.nuhlp.nursehelper.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.base.BaseViewBindingFragment
import com.nuhlp.nursehelper.databinding.FragmentRegisterTermDetailBinding
import java.lang.IllegalArgumentException


class RegisterTermDetailFragment : BaseViewBindingFragment<FragmentRegisterTermDetailBinding>() {
    private val _args: RegisterTermDetailFragmentArgs by navArgs()
    private val term get() = _args.term


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.termTitleText.text = term.getTermTitle()
        binding.termDetailText.text = term.getTermDetail()
        binding.btnBackRegist.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterTermDetailBinding {


        return FragmentRegisterTermDetailBinding.inflate(inflater,container,false)
    }

    private fun Term.getTermTitle() = when(this.CODE){
        1-> resources.getString(R.string.agree_terms_checkbox_label_essential)
        2-> resources.getString(R.string.agree_terms_checkbox_label_userinfo)
        else -> throw IllegalArgumentException("unknown Code")
    }
    private fun Term.getTermDetail() = when(this.CODE){
        1-> resources.getString(R.string.agree_terms_checkbox_label_essential_detail)
        2-> resources.getString(R.string.agree_terms_checkbox_label_essential_detail)
        else -> throw IllegalArgumentException("unknown Code")
    }

}