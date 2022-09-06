package com.nuhlp.nursehelper.utill.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.databinding.ItemHolderDocumentBinding
import com.nuhlp.nursehelper.datasource.room.app.Document



class QuickListAdapter (private val onTaskClicked: (Document) -> Unit) :
    ListAdapter<Document, QuickListAdapter.TaskViewHolder>(DiffCallback){

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(oldDoc: Document, newDoc: Document): Boolean {
                return oldDoc.docNo == newDoc.docNo
            }

            override fun areContentsTheSame(oldDoc: Document, newDoc: Document): Boolean {
                return (oldDoc.docNo == newDoc.docNo)
                        && (oldDoc.patNo == newDoc.patNo)
                        && (oldDoc.tmpNo == newDoc.tmpNo)
                        && (oldDoc.crtDate == newDoc.crtDate)
                        && (oldDoc.contentsJs == newDoc.contentsJs)
            }
        }
    }

    class TaskViewHolder(private var binding: ItemHolderDocumentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doc: Document) {
            binding.apply {
                title.text = doc.docNo.toString()
                content.text = "${doc.crtDate}\n환자번호: ${doc.patNo}\n문서종류: ${doc.tmpNo}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemHolderDocumentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onTaskClicked(current)
        }
        holder.bind(current)
    }
}