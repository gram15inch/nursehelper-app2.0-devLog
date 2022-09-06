package com.nuhlp.nursehelper.ui.quick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nuhlp.nursehelper.ui.home.HomeViewModel

class QuickCreationViewModel : ViewModel() {
    // TODO: Implement the ViewModel



    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuickCreationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return QuickCreationViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}