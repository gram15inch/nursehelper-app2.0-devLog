package com.nuhlp.nursehelper.utill.useapp


import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import org.w3c.dom.Document

object BindingAdapters {

    /**
     * View의 visibility를 변경
     * @param view 속성을 사용하는 view
     * @param isVisible visibility를 변경시키는 기준이 되는 값
     */

    @JvmStatic
    @BindingAdapter("textAny")
    fun setTextAny(view: View, text: Any?) {
        if (view is TextView)
            view.text = "${text ?: "?"}"
    }

    @JvmStatic
    @BindingAdapter("textLive")
    fun setTextLive(view: View, live: LiveData<*>) {

        if (view is TextView)
            (live as LiveData<List<Document>>).apply {
                view.text  ="${this.value}"
            }



    }


    }


