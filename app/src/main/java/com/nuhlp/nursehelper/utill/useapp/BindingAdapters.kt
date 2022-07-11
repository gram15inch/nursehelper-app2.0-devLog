package com.nuhlp.nursehelper.utill.useapp


import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.w3c.dom.Document

object BindingAdapters {

    /**
     * View의 visibility를 변경
     * @param view 속성을 사용하는 view
     * @param isVisible visibility를 변경시키는 기준이 되는 값
     */

    @JvmStatic
    @BindingAdapter("textAny")
    fun setTextAny(view: View, text: Any) {
        if(view is TextView)
            view.text  ="$text"
    } // todo flow 로 바꾼 list 튕기지 않고 출력
    // todo room 에서 말고 mutable로 생성하면 튕기지않음


}