package com.nuhlp.nursehelper.utill.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nuhlp.nursehelper.databinding.ViewPlacePanelBinding

class PlacePanel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    val binding : ViewPlacePanelBinding
    init {
        binding =ViewPlacePanelBinding .inflate(LayoutInflater.from(context),this)
    }
}