package com.nuhlp.nursehelper.ui.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentLoginBinding
import com.nuhlp.nursehelper.databinding.FragmentRegistBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegistBinding? = null
    private val binding get() = _binding!!
    private lateinit var isAgreeTerm : LiveData<Boolean>
    private val _loginViewModel : LoginViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAgreeTerm = _loginViewModel.isAgreeTerm
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegistBinding.inflate(layoutInflater,container,false)
        setBinding(binding)
        setListener(binding)
        setObserver(binding)
        return binding.root
    }

    private fun setBinding(binding: FragmentRegistBinding) {
        binding.apply {
            registProgressbar.setProgressCompat(30,true)
        }
    }

    private fun setObserver(binding: FragmentRegistBinding) = binding.apply{
        isAgreeTerm.observe(viewLifecycleOwner){ value->
            btnContinueLogin.isEnabled = value
            agreeTermsCheckboxAll.isChecked = value
        }

    }

    private fun setListener(binding: FragmentRegistBinding) = binding.apply{
        setCheckBoxes(allCheck = agreeTermsCheckboxAll,
            agreeTermsCheckboxEssential
            ,agreeTermsCheckboxUserInfo)
        btnContinueLogin.setOnClickListener{
            // todo datastore(true)
            findNavController().navigate(R.id.register2Fragment)
            /*해야할것
            * datastore update
            * viewModel update
            * repository update
            * 각 테스트
            * */
        }
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
               term.setOnCheckedChangeListener{_,isChecked->
                   when{
                       termList.all { it.isChecked == true}->{allCheck.isChecked = true}
                       termList.any { it.isChecked == false}->{allCheck.isChecked = false }
                   }
                }
           }

        }
    }



    private fun isCheckedTerm(checkBoxes :List<CheckBox>):Boolean{
        return when{
            (checkBoxes[0].isChecked)->{
                checkBoxes[0].isChecked
            }
            else->{
                checkBoxes[0].isChecked.apply { checkBoxes[0].isChecked  = checkBoxes[1] == checkBoxes[2] }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
