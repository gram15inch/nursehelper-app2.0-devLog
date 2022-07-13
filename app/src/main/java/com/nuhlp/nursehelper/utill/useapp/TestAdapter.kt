package com.nuhlp.nursehelper.utill.useapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.databinding.ItemHolderDocumentBinding


class TestAdapter :
    ListAdapter<Document, TestAdapter.ItemViewHolder>(DiffCallback){

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(oldDoc: Document, newDoc: Document): Boolean {
                return oldDoc.docNo == newDoc.docNo
            }

            override fun areContentsTheSame(oldDoc: Document, newDoc: Document): Boolean {
                /*   return (oldDoc.docNo == newDoc.docNo)
                           && (oldDoc.patNo == newDoc.patNo)
                           && (oldDoc.tmpNo == newDoc.tmpNo)
                           && (oldDoc.crtDate == newDoc.crtDate)
                           && (oldDoc.contentsJs == newDoc.contentsJs)*/
                return oldDoc == newDoc
            }
        }
    }

    class ItemViewHolder(private var binding: ItemHolderDocumentBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemHolderDocumentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)

    }
}