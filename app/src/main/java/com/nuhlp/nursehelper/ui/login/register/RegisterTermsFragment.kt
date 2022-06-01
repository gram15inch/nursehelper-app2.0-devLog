package com.nuhlp.nursehelper.ui.login.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.R

import com.nuhlp.nursehelper.databinding.FragmentRegisterTermsBinding
import com.nuhlp.nursehelper.viewmodel.LoginViewModel
import com.nuhlp.nursehelper.ui.login.Term


class RegisterTermsFragment : Fragment() {
    private var _binding: FragmentRegisterTermsBinding? = null
    private val binding get() = _binding!!
    private val _loginViewModel : LoginViewModel by activityViewModels()

    private lateinit var isAgreeTerm : LiveData<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAgreeTerm = _loginViewModel.isAgreeTerm
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterTermsBinding.inflate(layoutInflater,container,false)

        setValueToView(binding)
        setObserver(binding)
        setListener(binding)

        return binding.root
    }

    private fun setValueToView(binding: FragmentRegisterTermsBinding) = binding.apply {
        registProgressbar.setProgressCompat(30,true)
        stateTitle.text = resources.getString(R.string.welcome_regist)
        stateSubTitle.text = resources.getString(R.string.agree_terms)
    }

    private fun setObserver(binding: FragmentRegisterTermsBinding) = binding.apply{
        isAgreeTerm.observe(viewLifecycleOwner){ value->
            btnContinueLogin.isEnabled = value
            agreeTermsCheckboxAll.isChecked = value
        }
    }

    private fun setListener(binding: FragmentRegisterTermsBinding) = binding.apply{
        setCheckBoxes(allCheck = agreeTermsCheckboxAll,
            agreeTermsCheckboxEssential
            ,agreeTermsCheckboxUserInfo)

        btnContinueLogin.setOnClickListener{
            _loginViewModel.agreeTerms()
            findNavController().navigate(R.id.action_registerFragment_to_registerIdFragment)

        }
        setIcons(
            essentialTermIcon,
            userinfoTermIcon
        )

    }

    private fun setIcons(vararg termIcons : AppCompatImageButton) {
        val iconList =  termIcons.asList()

        iconList.map {icon->
            icon.setOnClickListener{v->
                val action =
                   RegisterTermsFragmentDirections.actionRegisterTermsFragmentToRegisterTermDetailFragment(
                        getTerm(v.id)
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun getTerm(id:Int) = when(id){
            binding.essentialTermIcon.id->{
                Term.ESSENTIAL
            }
            binding.userinfoTermIcon.id->{
                Term.USERINFO
            }
            else -> { throw IllegalArgumentException("unknown Term") }
    }

    private fun setCheckBoxes(allCheck: CheckBox ,vararg termCheck :CheckBox) {
        binding.apply {
            val termList =  termCheck.asList()
            allCheck.setOnCheckedChangeListener{_,isChecked->
                if(isChecked)
                    termList.map { it.isChecked = isChecked }
                btnContinueLogin.isEnabled = isChecked
            }
           termList.map {term->
               term.setOnCheckedChangeListener{_,_->
                   when{
                       termList.all { it.isChecked }-> allCheck.isChecked = true
                       termList.any { !it.isChecked }-> allCheck.isChecked = false
                   }
                }
           }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
